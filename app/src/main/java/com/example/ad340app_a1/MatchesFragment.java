package com.example.ad340app_a1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.ad340app_a1.Constants.BELLEVUE_LAT;
import static com.example.ad340app_a1.Constants.BELLEVUE_LONG;
import static com.example.ad340app_a1.Constants.METERS_TO_MILES;

/**
 * A fragment representing a list of Matches. Activities containing this fragment MUST implement
 * {@link OnMatchesFragmentInteractionListener} interface.
 */
public class MatchesFragment extends Fragment {
    private static final String TAG = MatchesFragment.class.getSimpleName();

    public MatchesFragmentViewModel viewModel;
    public RecyclerView recyclerView;

//    private ArrayList<Match> matchDataSet;
    private OnMatchesFragmentInteractionListener mListener;
    boolean hasMatches = false;
    public Context context;

    private LocationManager locationManager;
    double latitudeGPS = BELLEVUE_LAT;
    double longitudeGPS = BELLEVUE_LONG;
    int maxMatchDistance = 10;

    String email;

    // Mandatory empty constructor to instantiate frag (eg: screen orientation change)
    public MatchesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,
                container, false);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        context = getActivity();
        setGPSUpdates();

        // New up Settings vm
        SettingsViewModel settingsViewModel = new ViewModelProvider(this)
                .get(SettingsViewModel.class);
        Bundle bundle = getArguments();

        if (bundle != null) {
            email = bundle.getString(Constants.KEY_EMAIL);
        }

        final Observer<List<com.example.ad340app_a1.Settings>>
                getSettingsObserver = newSettings -> {

            if (newSettings == null || newSettings.size() <= 0) {
                return;
            }

            com.example.ad340app_a1.Settings settings = newSettings.get(0);
            if (settings == null) {
                return;
            }
            maxMatchDistance = settings.getDistance();
        };

        String[] emails = {email};
        settingsViewModel.loadSettingsById(recyclerView.getContext(),
                emails).observe((LifecycleOwner) recyclerView.getContext(), getSettingsObserver);

        // adapter
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // create dialog to go to show intent to go to Settings application
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.enable_location)
                .setMessage(getString(R.string.location_message))
                .setPositiveButton(R.string.location_settings, (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton(R.string.location_cancel, (paramDialogInterface, paramInt) -> {
                });
        dialog.show();
    }

    public void setGPSUpdates() {
        if (!checkLocation()) {
            return;
        }
        // do self permission checks
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // request scheduled location updates
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    60 * 1000,
                    10,
                    locationListenerGPS);
        } else {
            Log.e("db", "LOCATION PERMISSIONS ERROR");
        }
    }

    private final LocationListener locationListenerGPS = new LocationListener() {

        public void onLocationChanged(Location location) {
            latitudeGPS = location.getLatitude();
            longitudeGPS = location.getLongitude();

            // adapt content when location changed
            ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {}
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMatchesFragmentInteractionListener) {
            mListener = (OnMatchesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMatchesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.i(TAG, "onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.clear();
        Log.i(TAG, "onPause()");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePicture;
        public TextView name;
        public TextView description;
        public ImageButton favoriteBtn;
        public String likedToastMsg;
        public String unlikedToastMsg;

        public String matchUid;
        public String matchName;
        public boolean isLiked;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.frag_matches, parent, false));
            profilePicture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);
            favoriteBtn = itemView.findViewById(R.id.like_button);


            favoriteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Match item = new Match();
                    // if isLike is true at onClick, it's getting un-liked
                    if (isLiked) {
                        favoriteBtn.setImageResource(R.drawable.button_normal);
                        isLiked = false;
                        item.setLike(isLiked);
                        item.setUid(matchUid);
                        mListener.onMatchesFragmentInteraction(item);

                        Toast toast = Toast.makeText(v.getContext(), unlikedToastMsg, Toast.LENGTH_SHORT);
                        toast.show();
                        Log.i(TAG, String.valueOf(item.getLike()));
                    // if isLike is false at onClick, it's getting liked
                    } else {
                        favoriteBtn.setImageResource(R.drawable.button_pressed);
                        isLiked = true;
                        item.setLike(isLiked);
                        item.setUid(matchUid);
                        mListener.onMatchesFragmentInteraction(item);

                        Toast toast = Toast.makeText(v.getContext(), likedToastMsg, Toast.LENGTH_SHORT);
                        toast.show();
                        Log.i(TAG, String.valueOf(item.getLike()));
                    }
                }
            });
        }
    }

    // Adapter connects data to RecyclerView and determines which ViewHolder is needed to display it
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        boolean hasMatches = false;
        private ArrayList<String> userNames;
        private ArrayList<String> uid;
        private ArrayList<String> profileImageUrls;
        private ArrayList<Boolean> liked;

        private MatchesFragmentViewModel viewModel;

        public int MatchesInsideMaxDistanceCount;
        double latitudeGPS = BELLEVUE_LAT;
        double longitudeGPS = BELLEVUE_LONG;
        int maxMatchDistance = 10;

        Location matchLocation = new Location("");
        Location userLocation = new Location("");

        public ContentAdapter(Context context) {

            userNames = new ArrayList<>();
            profileImageUrls = new ArrayList<>();
            liked = new ArrayList<>();
            uid = new ArrayList<>();

            userLocation.setLatitude(latitudeGPS);
            userLocation.setLongitude(longitudeGPS);

            viewModel = new MatchesFragmentViewModel();
            viewModel.getMatches((ArrayList<Match> matchDataSet) -> {
                for(int i = 0; i < matchDataSet.size(); i++){
                    double matchLatitude = Double.parseDouble(matchDataSet.get(i).getLat());
                    Log.i(TAG, "matchLatitude: " + matchLatitude);
                    double matchLongitude = Double.parseDouble(matchDataSet.get(i).getLongitude());
                    matchLocation.setLatitude(matchLatitude);
                    matchLocation.setLongitude(matchLongitude);
                    double matchDistance = matchLocation.distanceTo(userLocation);
                    matchDistance = metersToMiles(matchDistance);

                    if(matchDistance < maxMatchDistance){
                        userNames.add(matchDataSet.get(i).getName());
                        profileImageUrls.add(matchDataSet.get(i).getImageUrl());
                        liked.add(matchDataSet.get(i).getLike());
                        uid.add(matchDataSet.get(i).getUid());
                        MatchesInsideMaxDistanceCount++;
                    }
                }
                notifyDataSetChanged();
                hasMatches = true;
                MatchesInsideMaxDistanceCount = userNames.size();
            });
        }

        private double metersToMiles(double meters) {
            return meters / METERS_TO_MILES;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        // onBindViewHolder is called for each ViewHolder to bind it to the adapter; this is where you pass data to ViewHolder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.get().load(profileImageUrls.get(position)).into(holder.profilePicture);
            holder.name.setText(userNames.get(position));
            holder.matchUid = uid.get(position);
            holder.isLiked = liked.get(position);
            holder.matchName = holder.name.getText().toString();

            //set if isLiked
            if(String.valueOf(holder.isLiked) == "true") {
                holder.favoriteBtn.setImageResource(R.drawable.button_pressed);
            }

            holder.likedToastMsg = getString(R.string.like_button_message) + " " + holder.matchName;
            holder.unlikedToastMsg = getString(R.string.unlike_button_message) + " " + holder.matchName;

            Log.i(TAG, "onBindViewHolder()" + position);
        }

        @Override
        public int getItemCount() {
            return MatchesInsideMaxDistanceCount;
        }
        public void onMatchesFragmentInteraction(Match item) {
            item.liked = !item.liked;
            // updateMatchLikeById located in FirebaseMatchModel
            viewModel.updateMatchLikeById(item);
        }

    }
}