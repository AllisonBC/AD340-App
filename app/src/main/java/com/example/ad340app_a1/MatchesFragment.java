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
import android.widget.Button;
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

import static com.example.ad340app_a1.Constants.METERS_TO_MILES;

/**
 * A fragment representing a list of Matches. Activities containing this fragment MUST implement
 * {@link OnMatchesFragmentInteractionListener} interface.
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = MatchesFragment.class.getSimpleName();
    public MatchesFragmentViewModel viewModel;
    private ArrayList<Match> matchDataSet;
    private OnMatchesFragmentInteractionListener mListener;
    private LocationManager locationManager;

//    double longitudeNetwork, latitudeNetwork;//for testing
//    TextView longitudeValueNetwork, latitudeValueNetwork; //for testing


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
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,
                container, false);
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
        private static final int LENGTH = 6; // from given firebase db
        private final String[] userNames;
        private final String[] uid;
        private String[] profileImageUrls;
//        private final Drawable[] profilePhotos;
        private final boolean[] liked;
        private MatchesFragmentViewModel viewModel;



        Location matchLocation = new Location("");

        public ContentAdapter(Context context) {

            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

            userNames = new String[LENGTH];
            profileImageUrls = new String[LENGTH];
            liked = new boolean[LENGTH];
            uid   = new String[LENGTH];

            viewModel = new MatchesFragmentViewModel();
            viewModel.getMatches((ArrayList<Match> matchDataSet) -> {
                for(int i = 0; i < matchDataSet.size(); i++){
                    double matchLatitude = Double.parseDouble(matchDataSet.get(i).getLat());
                    double matchLongitude = Double.parseDouble(matchDataSet.get(i).getLongitude());
                    setMatchLocation(matchLatitude, matchLongitude);
                    double matchDistance = matchLocation.distanceTo(matchLocation);
                    matchDistance = metersToMiles(matchDistance);

                    //TODO retrieve desired max distance // HARDCODED TO 10 /////
                    if(matchDistance < 10){
                        userNames[i] = matchDataSet.get(i).getName();
                        profileImageUrls[i] = matchDataSet.get(i).getImageUrl();
                        liked[i] = matchDataSet.get(i).getLike();
                        uid[i] = matchDataSet.get(i).getUid();
                    }
                }
                notifyDataSetChanged();
            });
        }

        private double metersToMiles(double meters) {
            return meters / METERS_TO_MILES;
        }


        public void setMatchLocation(double matchLatitude, double matchLongitude){
            matchLocation.setLatitude(matchLatitude);
            matchLocation.setLongitude(matchLongitude);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.picture.setImageDrawable(profilePhotos[position % profilePhotos.length]);
            Picasso.get().load(profileImageUrls[position % profileImageUrls.length]).into(holder.profilePicture);
            holder.name.setText(userNames[position % userNames.length]);
            holder.matchUid = uid[position % uid.length];
            holder.isLiked = liked[position % liked.length];

            //set if isLiked
            if(String.valueOf(holder.isLiked) == "true"){
                holder.favoriteBtn.setImageResource(R.drawable.button_pressed);
            }

            holder.matchName = holder.name.getText().toString();//set name for each card
            StringBuilder likeBtnMsg = new StringBuilder(getString(R.string.like_button_message));
            likeBtnMsg.append(holder.matchName);
            holder.likedToastMsg = likeBtnMsg.toString();

            Log.i(TAG, "onBindViewHolder()" + position);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

        private boolean checkLocation(){
            if(!isLocationEnabled()){
                showAlert();
            }
            return isLocationEnabled();
        }

        private boolean isLocationEnabled(){
            return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
//TODO: add toggle button
        // at click of a button
        public void toggleNetworkUpdates(View view) {
            if(!checkLocation())
                return;

            // button toggles distance filter on/filter off
            Button button = (Button) view;
            // button reads pause, we're already getting updates, so
            if(button.getText().equals(getResources().getString(R.string.filter_on))) {
                // remove updates
                locationManager.removeUpdates(locationListenerNetwork);
                // set button to resume
                button.setText(R.string.filter_off);
            }
            // if distance filter is off
            else {
                // do self permission checks
                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(
                                getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                    // request scheduled location updates
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            60 * 1000,
                            10,
                            locationListenerNetwork);
                } else {
                    Log.e("db","LOCATION PERMISSIONS ERROR");
                }
            }
        }

        private final LocationListener locationListenerNetwork = new LocationListener() {
            public void onLocationChanged(Location location) {
//                //TESTING*************************
//                longitudeNetwork = location.getLongitude();
//                latitudeNetwork = location.getLatitude();

                //check text views for long and lat
                location.getLongitude();
                location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
    }


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
