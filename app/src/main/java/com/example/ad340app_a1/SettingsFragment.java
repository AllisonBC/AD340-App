package com.example.ad340app_a1;

import android.os.Bundle;

import java.util.List;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

// Provides UI for view with Settings
public class SettingsFragment extends Fragment {
    public static final String TAG = SettingsFragment.class.getSimpleName();

    private SettingsViewModel settingsViewModel;
    private String email;
    private EditText editAgeInterestHi;
    private EditText editAgeInterestLo;
    private EditText editReminderTime;
    private EditText editDistance;
    private EditText editGender;
    private EditText editPrivacy;
    private Button saveButton;

//    private int lowAge;
//    private int hiAge;
//    private Spinner spinner1, spinner2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    // Improve UI
//    private void addAgeSpinner1() {
//
//        ages = new ArrayList<Integer>();
//        for (int i = 18; i <= 109; i++) {
//            ages.add(Integer.toString(i));
//        }
//        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, ages);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner1 = (Spinner)findViewById(R.id.spinner1);
//        spinner1.setAdapter(spinnerArrayAdapter);
//    }
//
//    private void addAgeSpinner2() {
//        ages = new ArrayList<Integer>();
//        for (int i = 18; i <= 109; i++) {
//            ages.add(Integer.toString(i));
//        }
//        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, ages);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner2 = (Spinner)findViewById(R.id.spinner2);
//        spinner2.setAdapter(spinnerArrayAdapter);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set view
        View view = inflater.inflate(R.layout.frag_settings, container, false);

        // set vm
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // set bundle
        Bundle b = getArguments();

        // add form to view
        editReminderTime = view.findViewById(R.id.match_reminder);
        editDistance = view.findViewById(R.id.distance);
        editGender = view.findViewById(R.id.gender);
        editPrivacy = view.findViewById(R.id.privacy);
        editAgeInterestHi = view.findViewById(R.id.age_interest_hi);
        editAgeInterestLo = view.findViewById(R.id.age_interest_lo);

        saveButton = view.findViewById(R.id.save_button);

        if(b!=null) {
            // get email
            email = b.getString(Constants.KEY_EMAIL);
        } else {
            // if null
            email =  getResources().getString(R.string.test_email);
        }
        // Create observer to update UI
        final Observer<List<Settings>> getSettingsObserver = newSettings -> {

            if(newSettings == null || newSettings.size() <= 0) {
                return;
            }

            // get email (0th)
            Settings settings = newSettings.get(0);

            if (settings == null) {
                return;
            }

            editReminderTime.setText(settings.getReminderTime());
            editDistance.setText(String.valueOf(settings.getDistance()));
            editGender.setText(settings.getGender());
            editPrivacy.setText(settings.getPrivacy());
            editAgeInterestHi.setText(String.valueOf(settings.getAgeHi()));
            editAgeInterestLo.setText(String.valueOf(settings.getAgeLo()));
        };

        // updates Settings from form entry and sends data to db
        saveButton.setOnClickListener(v -> {
            Settings settings = new Settings();
            settings.setEmail(email);
            settings.setReminderTime(editReminderTime.getText().toString());
            settings.setDistance(Integer.parseInt(editDistance.getText().toString()));
            settings.setGender(editGender.getText().toString());
            settings.setGender(editAgeInterestHi.getText().toString());
            settings.setGender(editAgeInterestLo.getText().toString());
            settings.setPrivacy(editPrivacy.getText().toString());

            settingsViewModel.updateSettings(view.getContext(), settings);

            editReminderTime.setText(settings.getReminderTime());
            editDistance.setText(String.valueOf(settings.getDistance()));
            editGender.setText(settings.getGender());
            editPrivacy.setText(settings.getPrivacy());
            editAgeInterestHi.setText(String.valueOf(settings.getAgeHi()));
            editAgeInterestLo.setText(String.valueOf(settings.getAgeLo()));
        });

        String[] emails = { email };
        settingsViewModel.loadSettingsById(view.getContext(),
                emails).observe((LifecycleOwner) view.getContext(), getSettingsObserver);
        return view;
    }
}
