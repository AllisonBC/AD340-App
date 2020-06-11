package com.example.ad340app_a1;

import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.List;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static com.example.ad340app_a1.Constants.MAX_AGE;
import static com.example.ad340app_a1.Constants.MIN_AGE;

// Provides UI for view with Settings
public class SettingsFragment extends Fragment {
    public static final String TAG = SettingsFragment.class.getSimpleName();

    private SettingsViewModel settingsViewModel;
    private String email;
    private NumberPicker editAgeInterestHi;
    private NumberPicker editAgeInterestLo;
    private EditText editReminderTime;
    private EditText editDistance;
    private EditText editGender;
    private RadioGroup radio_privacy_group;
    private RadioButton editPrivacy;
    private Button saveButton;
    private Button btnTimePicker;
    private int mHour, mMinute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set view
        View view = inflater.inflate(R.layout.frag_settings, container, false);

        // set vm
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // set bundle
        Bundle b = getArguments();

        btnTimePicker= view.findViewById(R.id.btn_time);
        editReminderTime= view.findViewById(R.id.match_reminder);

        // add form to view
        editReminderTime = view.findViewById(R.id.match_reminder);
        editDistance = view.findViewById(R.id.distance);
        editGender = view.findViewById(R.id.gender);
        radio_privacy_group = view.findViewById(R.id.radio_privacy_group);
        editAgeInterestHi = view.findViewById(R.id.age_interest_hi);
        editAgeInterestLo = view.findViewById(R.id.age_interest_lo);
        editAgeInterestHi.setMaxValue(MAX_AGE);
        editAgeInterestLo.setMinValue(MIN_AGE);

        //TODO max cant be lower than min
        editAgeInterestLo.setMaxValue(MAX_AGE);
        editAgeInterestHi.setMinValue(MIN_AGE);
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
            editAgeInterestHi.setValue(settings.getAgeHi());
            editAgeInterestLo.setValue(settings.getAgeLo());

            if(settings.getPrivacy() == "Private") {
                radio_privacy_group.check(R.id.radio_private);

            } else {
                // if settings.getPrivacy == Public
                radio_privacy_group.check(R.id.radio_public);
            }
        };

        // get selected radio button from radioGroup
        int selectedId = radio_privacy_group.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        editPrivacy = (RadioButton) view.findViewById(selectedId);

        // updates Settings from form entry and sends data to db
        saveButton.setOnClickListener(v -> {
            Settings settings = new Settings();
            settings.setEmail(email);
            settings.setReminderTime(editReminderTime.getText().toString());
            //TODO null
            settings.setDistance(Integer.parseInt(editDistance.getText().toString()));
            settings.setGender(editGender.getText().toString());
            settings.setAgeHi(editAgeInterestHi.getValue());
            settings.setAgeLo(editAgeInterestLo.getValue());
            settings.setPrivacy(editPrivacy.getText().toString());

            settingsViewModel.updateSettings(view.getContext(), settings);

            editReminderTime.setText(settings.getReminderTime());
            editDistance.setText(String.valueOf(settings.getDistance()));
            editGender.setText(settings.getGender());
            editPrivacy.setText(settings.getPrivacy());
            editAgeInterestHi.setValue(settings.getAgeHi());
            editAgeInterestLo.setValue(settings.getAgeLo());
        });

        btnTimePicker.setOnClickListener(v -> {
            if (v == btnTimePicker) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        (view1, hourOfDay, minute) ->
                                editReminderTime.setText(hourOfDay + ":" + minute),
                        mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        String[] emails = { email };
        settingsViewModel.loadSettingsById(view.getContext(),
                emails).observe((LifecycleOwner) view.getContext(), getSettingsObserver);
        return view;
    }
}
