package com.example.ad340app_a1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


// Provides UI for view with Profile
public class ProfileFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfileFragment() {
    }

    private static final String TAG = MainActivity.class.getSimpleName();
//    private TextView ageDisplay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);
        super.onCreate(savedInstanceState);
        TextView profileText = view.findViewById(R.id.profile_text);

        StringBuilder msg = new StringBuilder("");
        Intent intent = getActivity().getIntent();
        Bundle b = intent.getExtras();

        // Initialize variables and give default values
        String name = "Example name";
        int age = 30;
        String occupation = "Occupation";
        String description = "Description";




        if (b != null) {
            if (b.containsKey(Constants.KEY_NAME)) {
                name = b.getString(Constants.KEY_NAME);
            }
            if (b.containsKey(Constants.KEY_AGE)) {
                age = b.getInt(Constants.KEY_AGE);
            }
            if (b.containsKey(Constants.KEY_OCCUPATION)) {
                occupation = b.getString(Constants.KEY_OCCUPATION);
            }
            if (b.containsKey(Constants.KEY_DESCRIPTION)) {
                description = b.getString(Constants.KEY_DESCRIPTION);
            }
        }
        ImageView userPhoto = view.findViewById(R.id.profile_photo);
        userPhoto.setImageResource(R.drawable.profile);

        TextView profileName = view.findViewById(R.id.profile_name);
        profileName.setText(name);

        msg.append(age).append(" years old\n");
        Log.i(TAG, new StringBuilder("Age: ").append(age).toString());
        msg.append(occupation).append("\n");
        msg.append(description).append("\n");
        profileText.setText(msg);

        Log.i(TAG, "onCreate()");
        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString(Constants.KEY_NAME, name);
//        outState.putString(Constants.KEY_TEXTVIEW_TEXT, textView.getText().toString());
//
//    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
    }
}