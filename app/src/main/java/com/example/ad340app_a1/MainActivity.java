package com.example.ad340app_a1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView dateDisplay;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateDisplay = findViewById(R.id.user_dob);
        dateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Widget_Holo_ActionBar_Solid,
                        dateSetListener, year, month, day);
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet() date: " + month + day + year);

                String date = month + "/" + day + "/" + "year";
                dateDisplay.setText(date);
            }
        };

        Log.i(TAG, "onCreate()");
    }


    public void goToSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_NAME, "Joe Exotic");
        bundle.putString(Constants.KEY_OCCUPATION, "The Tiger King");
        bundle.putString(Constants.KEY_EMAIL, "kingojungle@tigerlvr.com");
        bundle.putInt(Constants.KEY_AGE, 50);
        bundle.putString(Constants.KEY_DESCRIPTION, "Love tigers and guns; did not kill Carole Baskins");
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

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        Log.i(TAG, "onRestoreInstanceState()");
//        if (savedInstanceState.containsKey(Constants.KEY_NAME)) {
//            textView.setText((String)savedInstanceState.get(Constants.KEY_NAME));
//        }
//
//        if (savedInstanceState.containsKey(Constants.KEY_BUTTON_TXT)) {
//            submitButton.setText((String) savedInstanceState.get(Constants.KEY_BUTTON_TXT));
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        Log.i(TAG, "onSaveInstanceState()");
//        outState.putString(Constants.KEY_NAME, textView.getText().toString());
//        outState.putString(Constants.KEY_BUTTON_TXT, submitButton.getText().toString());
//    }

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
