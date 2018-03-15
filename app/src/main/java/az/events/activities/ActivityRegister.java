package az.events.activities;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import az.events.R;

public class ActivityRegister extends AppCompatActivity {
    Spinner gender_spinner;
    Button signUpBtn;
    int year,month,day;
    EditText name,surname,email,password,confirmPassword;
    TextView bura,birthday;
    RelativeLayout gender_lyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gender_spinner = (Spinner) findViewById(R.id.gender);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        email = (EditText) findViewById(R.id.email);
        birthday = (TextView) findViewById(R.id.birthday);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        bura = (TextView) findViewById(R.id.already_user1);
        gender_lyt = (RelativeLayout)findViewById(R.id.gender_lyt);
        final Calendar c = Calendar.getInstance();
        ArrayAdapter adapter = ArrayAdapter.createFromResource(ActivityRegister.this,
                R.array.gender, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        gender_spinner.setAdapter(adapter);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Log.d("--->",year+" : "+month+" : "+day);
        bura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityRegister.this,ActivityLogin.class);
                startActivity(intent);

            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });


        gender_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_spinner.performClick();
            }});
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityRegister.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            birthday.setText(arg3+"/"+arg2+"/"+arg1);
        }
    };
    public final static boolean isValidEmail(CharSequence target) {
        if(!TextUtils.isEmpty(target))
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        return  true;
    }

    public final static boolean isValidPassword(CharSequence target) {
        if(target.length()>4)
        return  true;
        return false;
    }
}
