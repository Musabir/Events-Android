package az.events.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import az.events.R;
import az.events.activities.ActivityLogin;
import az.events.activities.MainActivity;
import az.events.others.Fonts;

public class FragmentFourth extends Fragment {

    //How it works
    /*
    In this fragment button shows "cixis" if you are logged in onclick redirects you to ActivityLogin with clearing SharedPreferences values
    else "giris" onclick redirects you to ActivityLogin
     */
    public FragmentFourth() {}
    private View view;
    private TextView t1,t2;
    private Button signin;
    private boolean loggedIn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String _id = sp.getString("_id", "null");
        if (!_id.equals("null")){
            loggedIn = true;
        }

        t1 = (TextView) view.findViewById(R.id.text1);
        t2 = (TextView) view.findViewById(R.id.text2);
        t1.setTypeface(Fonts.getTypeface(getActivity(), Fonts.helvetiacNeueLight));
        t2.setTypeface(Fonts.getTypeface(getActivity(), Fonts.helvetiacNeueLight));

        signin = (Button) view.findViewById(R.id.signin);
        signin.setTypeface(Fonts.getTypeface(getActivity(), Fonts.helveticaNeueMedium));
        if (loggedIn){
            signin.setText("Çıxış");
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loggedIn){
                    SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("user", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("_id", "null");
                    editor.putString("login","null");
                    editor.putString("img","null");
                    editor.putString("name","null");
                    editor.putString("surName","null");
                    if (editor.commit()){
                        Intent i = new Intent(getActivity(), ActivityLogin.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }
                else
                {
                    Intent i = new Intent(getActivity(), ActivityLogin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        });

        return view;
    }

}
