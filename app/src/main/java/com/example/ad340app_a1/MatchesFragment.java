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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.ad340app_a1.Constants.BELLEVUE_LAT;
import static com.example.ad340app_a1.Constants.BELLEVUE_LONG;
import static com.example.ad340app_a1.Constants.METERS_TO_MILES;

/**
 * A fragment representing a list of Matches. Activities containing this fragment MUST implement
 * {@link OnMatchesFragmentInteractionListener} interface.
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = MatchesFragment.class.getSimpleName();

    private Context context;
    public MatchesFragmentViewModel viewModel;
    public RecyclerView recyclerView;
    private ArrayList<Match> matchDataSet;
    private OnMatchesFragmentInteractionListener mListener;
    private LocationManager locationManager;
    double latitudeGPS  = BELLEVUE_LAT;
    double longitudeGPS = BELLEVUE_LONG;
    int maxMatchDistance = 10;

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

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        context = getActivity();
        setGPSUpdates();

        // adapter
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePicture;
        public TextView name;
        public TextView description;
        public ImageButton favoriteBtn;
        public String likedToastMsg;
        public String unLikedToastMsg;

        public String matchUid;
        public String matchName;
        public boolean isLiked;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.frag_matches, parent, false));
            profilePicture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            favoriteBtn = (ImageButton) itemView.findViewById(R.id.favorite_button);

            viewModel = new MatchesFragmentViewModel();

            //toast display
            favoriteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Match item = new Match();
                    // if isLike is true at onClick, it's getting un-liked
                    if (isLiked) {
                        favoriteBtn.setImageResource(R.drawable.button_normal);
                        isLiked = !isLiked;
                        item.setLike(isLiked);
                        item.setUid(matchUid);
                        onMatchesFragmentInteraction(item);
                        Log.i(TAG, String.valueOf(item.getLike()));
                    // if isLike is false at onClick, it's getting liked
                    } else {
                        favoriteBtn.setImageResource(R.drawable.button_pressed);
                        isLiked = !isLiked;
                        item.setLike(isLiked);
                        item.setUid(matchUid);
                        onMatchesFragmentInteraction(item);
                        Log.i(TAG, String.valueOf(item.getLike()));
                        Toast toast = Toast.makeText(v.getContext(), likedToastMsg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }

// previous assignment
//        public ContentAdapter(Context context) {
//            Resources resources = context.getResources();
//            userNames = resources.getStringArray(R.array.user_names);
//            userDesc = resources.getStringArray(R.array.user_desc);
//            liked = resources.getStringArray(R.array.liked);
//            TypedArray a = resources.obtainTypedArray(R.array.user_profile_pictures);
//            profilePhotos = new Drawable[a.length()];
//            for (int i = 0; i < profilePhotos.length; i++) {
//                profilePhotos[i] = a.getDrawable(i);
//            }
//            a.recycle();

    // Adapter to display recycler view
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> userNames;
        private ArrayList<String> uid;
        private ArrayList<String> profileImageUrls;
//        private final Drawable[] profilePhotos;  // previous assignment
        private ArrayList<Boolean> liked;

        private MatchesFragmentViewModel viewModel;

        public int MatchesInsideMaxDistanceCount;

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

                    //TODO retrieve desired max distance // HARDCODED TO 10 /////
                    if(matchDistance < maxMatchDistance){
                        userNames.add(matchDataSet.get(i).getName());
                        profileImageUrls.add(matchDataSet.get(i).getImageUrl());
                        liked.add(matchDataSet.get(i).getLike());
                        uid.add(matchDataSet.get(i).getUid());
                        MatchesInsideMaxDistanceCount++;
                    }
                }
                notifyDataSetChanged();
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

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.picture.setImageDrawable(profilePhotos[position % profilePhotos.length]);
            Picasso.get().load(profileImageUrls.get(position)).into(holder.profilePicture);
            holder.name.setText(userNames.get(position));
            holder.matchUid = uid.get(position);
            holder.isLiked = liked.get(position);

            //set if isLiked
            if(String.valueOf(holder.isLiked) == "true"){
                holder.favoriteBtn.setImageResource(R.drawable.button_pressed);
            }

            //TODO add message for unliking a match
            holder.matchName = holder.name.getText().toString();//set name for each card
            StringBuilder likeBtnMsg = new StringBuilder(getString(R.string.like_button_message));
            likeBtnMsg.append(holder.matchName);
            holder.likedToastMsg = likeBtnMsg.toString();

            Log.i(TAG, "onBindViewHolder()" + position);
        }

        @Override
        public int getItemCount() {
            return MatchesInsideMaxDistanceCount;
        }
    }


    private boolean checkLocation(){
        if(!isLocationEnabled()){
            showAlert();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled(){
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
                .setNegativeButton(R.string.location_cancel, (paramDialogInterface, paramInt) -> {});
        dialog.show();
    }

    public void setGPSUpdates() {
        if(!checkLocation()) {
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
            Log.e("db","LOCATION PERMISSIONS ERROR");
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
        public void onProviderEnabled(String s) {}

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

    public void onMatchesFragmentInteraction(Match item) {
        item.liked = true;
        // updateMatchLikeById located in FirebaseMatchModel
        viewModel.updateMatchLikeById(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.clear();
        Log.i(TAG, "onPause()");
    }
}
