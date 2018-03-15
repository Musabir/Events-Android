package az.events.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import az.events.R;
import az.events.others.CustomVolleyRequestQueue;
import az.events.others.Utils;

public class ActivityLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    public TextInputEditText email, password;
    TextView register;
    public CardView submit;
    LoginButton facebook_btn;
    SignInButton google_btn;
    CallbackManager callbackManager;
    private GoogleApiClient apiClient;
    private static final int REQ_CODE=9001;
    private static final int REQ_CODE_FB=9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext(),REQ_CODE_FB);

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "az.events",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        email = (TextInputEditText) findViewById(R.id.loginEmail);
        password = (TextInputEditText) findViewById(R.id.loginPassword);
        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this,ActivityRegister.class);
                startActivity(intent);
            }
        });
        submit = (CardView) findViewById(R.id.loginSubmit);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(getApplicationContext())){
                   loginAPI();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No internet",Toast.LENGTH_SHORT).show();
                }
            }
        });
        callbackManager = CallbackManager.Factory.create();
        facebook_btn = (LoginButton) findViewById(R.id.facebook_login);
        google_btn = (SignInButton) findViewById(R.id.google_login);
        facebook_btn.setReadPermissions("email");
        GoogleSignInOptions sign = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,sign).build();

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(signInIntent, REQ_CODE);
            }
        });
        // If using in a fragment
        // Other app specific specialization
        // Callback registration

        facebook_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("---->","Login Successful \n "+ loginResult.getAccessToken().getUserId()+" : "+ loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.d("---->","Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }


        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else         callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("----->", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("---->", acct.getDisplayName());
        }
    }

    public void loginAPI(){
      //  String url = "http://events.moko.az/api/login?language=az";
        RequestQueue queue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        Intent i = new Intent(ActivityLogin.this, MainActivity.class);
       // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        /*
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject convert = new JSONObject(response);
                            if (convert.getString("code").equals("200")) {
                                JSONObject data = convert.getJSONObject("data");
                                String _id = data.getString("_id");
                                String login = data.getString("login");
                                String img = data.getString("img");
                                String name = data.getString("name");
                                String surName = data.getString("surName");
                                Toast.makeText(getApplicationContext(), "Welcome "+ name +" "+ surName+"!", Toast.LENGTH_SHORT).show();

                                SharedPreferences sp = getApplicationContext().getSharedPreferences("user", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("_id", _id);
                                editor.putString("login",login);
                                editor.putString("img",img);
                                editor.putString("name",name);
                                editor.putString("surName",surName);
                                if (editor.commit()){

                                }
                            }
                            else
                            if (convert.getString("code").equals("401")){
                                Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("LoginForm[username]", email.getText().toString());
                params.put("LoginForm[password]", password.getText().toString());
                return params;
            }
        };

        queue.add(request);
        */
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
//264095770128-t41ekqavvo42k81tho7sgadt54mbmaak.apps.googleusercontent.com
