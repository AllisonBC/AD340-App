package com.example.ad340app_a1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DatePicker dateDisplay;
    private EditText name_entry;
    private EditText occupation_entry;
    private EditText username_entry;
    private EditText email_entry;
    private EditText description_entry;
    private Button loginBtn;

    private Calendar today;
    private int age;
    private int dobYear;
    private int dobMonth;
    private int dobDay;
    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_entry = findViewById(R.id.user_email);
        name_entry = findViewById(R.id.user_name);
        username_entry = findViewById(R.id.user_username);
        occupation_entry = findViewById(R.id.user_occupation);
        description_entry = findViewById(R.id.user_description);
        dateDisplay = findViewById(R.id.user_dob_picker);
        loginBtn = findViewById(R.id.login_btn);

        //store current date
        today   = Calendar.getInstance();
        year  = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day   = today.get(Calendar.DAY_OF_MONTH);

        //dob of user
        dobYear  = dateDisplay.getYear();
        dobMonth = dateDisplay.getMonth();
        dobDay   = dateDisplay.getDayOfMonth();

        today = Calendar.getInstance();
        dateDisplay.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                age = calculateAge(c.getTimeInMillis());
                Log.d(TAG, "onDateSet() date: " + month + dayOfMonth + year);
            }

            int calculateAge(long date){
                Calendar dob = Calendar.getInstance();
                dob.setTimeInMillis(date);
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
                    age--;
                }
                return age;
            }
        });

        Log.i(TAG, "onCreate()");
    }


    public void goToSecondActivity(View view) {
        if(age >= 18){
            Intent intent = new Intent(MainActivity.this, TabsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_NAME, name_entry.getText().toString());
            bundle.putString(Constants.KEY_OCCUPATION, occupation_entry.getText().toString());
            bundle.putString(Constants.KEY_EMAIL, email_entry.getText().toString());
            bundle.putString(Constants.KEY_DESCRIPTION, description_entry.getText().toString());

            bundle.putInt(Constants.KEY_AGE, age);
            intent.putExtras(bundle);
            startActivity(intent);

        }else{
            Toast toast = Toast.makeText(this, R.string.age_invalid, Toast.LENGTH_SHORT);
            toast.show();
            Log.i(TAG, String.valueOf(age) + ": age entry invalid");
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        name_entry.setText("");
        email_entry.setText("");
        username_entry.setText("");
        description_entry.setText("");
        occupation_entry.setText("");
        dateDisplay.updateDate(year, month, day);
        Log.i(TAG, "onRestart()");
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(TAG, "onRestoreInstanceState()");
        if (savedInstanceState.containsKey(Constants.KEY_NAME)) {
            name_entry.setText((String)savedInstanceState.get(Constants.KEY_NAME));
        }
        if (savedInstanceState.containsKey(Constants.KEY_EMAIL)) {
            email_entry.setText((String) savedInstanceState.get(Constants.KEY_EMAIL));
        }
        if (savedInstanceState.containsKey(Constants.KEY_USERNAME)) {
            username_entry.setText((String) savedInstanceState.get(Constants.KEY_USERNAME));
        }
        if (savedInstanceState.containsKey(Constants.KEY_DESCRIPTION)) {
            description_entry.setText((String) savedInstanceState.get(Constants.KEY_DESCRIPTION));
        }
        if (savedInstanceState.containsKey(Constants.KEY_OCCUPATION)) {
            occupation_entry.setText((String) savedInstanceState.get(Constants.KEY_OCCUPATION));
        }

        // Date picker restore
        int mDay = Integer.parseInt((String) savedInstanceState.get(Constants.KEY_STR_DAY));
        int mMonth = Integer.parseInt((String) savedInstanceState.get(Constants.KEY_STR_MONTH));
        int mYear = Integer.parseInt((String) savedInstanceState.get(Constants.KEY_STR_YEAR));

        if (savedInstanceState.containsKey(String.valueOf((Constants.KEY_STR_YEAR)))) {
            dateDisplay.updateDate(mYear,mMonth,mDay);
        }

        Log.i(TAG, "onRestoreInstanceState()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.KEY_NAME, name_entry.getText().toString());
        outState.putString(Constants.KEY_EMAIL, email_entry.getText().toString());
        outState.putString(Constants.KEY_USERNAME, username_entry.getText().toString());
        outState.putString(Constants.KEY_OCCUPATION, occupation_entry.getText().toString());
        outState.putString(Constants.KEY_DESCRIPTION, description_entry.getText().toString());


        // Date picker save instance
        outState.putString(Constants.KEY_STR_DAY, String.valueOf(dateDisplay.getDayOfMonth()));
        outState.putString(Constants.KEY_STR_MONTH, String.valueOf(dateDisplay.getMonth()));
        outState.putString(Constants.KEY_STR_YEAR, String.valueOf(dateDisplay.getYear()));


        Log.i(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
