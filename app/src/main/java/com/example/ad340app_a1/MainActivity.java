package com.example.ad340app_a1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView dateDisplay;
    private EditText name_entry;
    private EditText occupation_entry;
    private EditText email_entry;
    private EditText description_entry;
    private Button loginBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email_entry = findViewById(R.id.user_email);
        name_entry = findViewById(R.id.user_name);
        EditText username_entry = findViewById(R.id.user_username);
        occupation_entry = findViewById(R.id.user_occupation);
        description_entry = findViewById(R.id.user_description);

        // Adding Date dialog picker
        dateDisplay = findViewById(R.id.user_dob);
        dateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(),
                        datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });
        loginBtn = findViewById(R.id.login_btn);

        Log.i(TAG, "onCreate()");
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            dateDisplay.setText(format);
//            ageDisplay.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            Log.d(TAG, "onDateSet() date: " + month + dayOfMonth + year);
        }
    };

//        int calculateAge(long date){
//            Calendar dob = Calendar.getInstance();
//            dob.setTimeInMillis(date);
//            Calendar today = Calendar.getInstance();
//            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//            if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
//                age--;
//            }
//            return age;
//        }


    public void goToSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_NAME, name_entry.getText().toString());
        bundle.putString(Constants.KEY_OCCUPATION, occupation_entry.getText().toString());
        bundle.putString(Constants.KEY_EMAIL, email_entry.getText().toString());
        bundle.putInt(Constants.KEY_AGE, 50);
        bundle.putString(Constants.KEY_DESCRIPTION, description_entry.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(TAG, "onRestoreInstanceState()");
        if (savedInstanceState.containsKey(Constants.KEY_NAME)) {
            name_entry.setText((String)savedInstanceState.get(Constants.KEY_NAME));
        }

        if (savedInstanceState.containsKey(Constants.KEY_BUTTON_TXT)) {
            loginBtn.setText((String) savedInstanceState.get(Constants.KEY_BUTTON_TXT));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSaveInstanceState()");
        outState.putString(Constants.KEY_NAME, name_entry.getText().toString());
        outState.putString(Constants.KEY_BUTTON_TXT, loginBtn.getText().toString());
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
