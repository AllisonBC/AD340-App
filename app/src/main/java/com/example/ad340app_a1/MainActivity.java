package com.example.ad340app_a1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button submitButton;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        submitButton = findViewById(R.id.submitButton);
        editText = findViewById(R.id.nameEditText);
        textView = findViewById(R.id.textView);
        TextView userName = findViewById(R.id.userName);

        userName.setText(getString(R.string.example_user_name));

        User user = new User();
        user.setEmail(Objects.requireNonNull(getString(R.string.example_user_email)));
        user.setFirstName(Objects.requireNonNull(getString(R.string.example_user_first_name)));
        user.setLastName(Objects.requireNonNull(getString(R.string.example_user_last_name)));
        user.setUserName(Objects.requireNonNull(getString(R.string.example_user_name)));
        user.setDOB(Objects.requireNonNull(getString(R.string.example_user_dob)));


        new SetUserTask(this, user).execute();*/


        Log.i(TAG, "onCreate()");
    }

    public void goToSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(Constants.KEY_NAME, editText.getText().toString());
        intent.putExtra(Constants.KEY_DOB, 1989);
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
            textView.setText((String)savedInstanceState.get(Constants.KEY_NAME));
        }

        if (savedInstanceState.containsKey(Constants.KEY_BUTTON_TXT)) {
            submitButton.setText((String) savedInstanceState.get(Constants.KEY_BUTTON_TXT));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSaveInstanceState()");
        outState.putString(Constants.KEY_NAME, textView.getText().toString());
        outState.putString(Constants.KEY_BUTTON_TXT, submitButton.getText().toString());
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
