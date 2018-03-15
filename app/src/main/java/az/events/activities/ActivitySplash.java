package az.events.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import az.events.R;

public class ActivitySplash extends AppCompatActivity {

    //How it works
    /*
    Every time user launches app
    check if sharedPreferences hold value related to key "_id"
    if yes  redirect to MainActivity
    else
    to LoginActivity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
                String _id = sp.getString("_id", "null");
                if (!_id.equals("null")){
                    Intent i = new Intent(ActivitySplash.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(ActivitySplash.this, ActivityLogin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        },
        400);
    }

}
