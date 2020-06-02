package com.example.ad340app_a1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A fragment representing a list of Matches.
 * Activities containing this fragment MUST implement the
 * {@link OnMatchesFragmentInteractionListener} interface.
 */
public class MatchesActivityFragment extends Fragment {

    private static final String TAG = MatchesActivityFragment.class.getSimpleName();
    public FirebaseMatchViewModel viewModel;
    private ArrayList<Match> matchDataSet;
    private OnMatchesFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchesActivityFragment() {
    }

//    @SuppressWarnings("unused")
//    public static MatchesActivityFragment newInstance(int columnCount) {
//        MatchesActivityFragment fragment = new MatchesActivityFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//            mDataSet = getArguments().getParcelableArrayList(ARG_DATA_SET);
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

            viewModel = new FirebaseMatchViewModel();

            //toast display
            favoriteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Match item = new Match();

                    if (isLiked) {
                        favoriteBtn.setImageResource(R.drawable.button_pressed);
                        isLiked = !isLiked;
                        item.setLike(isLiked);
                        item.setUid(matchUid);
                        onMatchesFragmentInteraction(item);
                        Log.i(TAG, String.valueOf(item.getLike()));
                    }else{
                        favoriteBtn.setImageResource(R.drawable.button_normal);
                        isLiked = !isLiked;
                        item.setLike(isLiked);
                        item.setUid(matchUid);
                        onMatchesFragmentInteraction(item);
                        Log.i(TAG, String.valueOf(item.getLike()));
                    }
                    Toast toast = Toast.makeText(v.getContext(), likedToastMsg, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 18;
        private final String[] userNames;
        private final String[] uid;
        private String[] profileImageUrls;
//        private final Drawable[] profilePhotos;
        private final boolean[] liked;
        private FirebaseMatchViewModel viewModel;

        public ContentAdapter(Context context) {
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

            // initialized to size of given firebase db
            //TODO: initialize to length
            userNames = new String[6];
            profileImageUrls = new String[6];
            liked = new boolean[6];
            uid   = new String[6];

            viewModel = new FirebaseMatchViewModel();
            viewModel.getMatches(
                    (ArrayList<Match> matchDataSet) -> {
                        for(int i = 0; i < matchDataSet.size(); i++){
                            userNames[i] = matchDataSet.get(i).getName();
                            profileImageUrls[i] = matchDataSet.get(i).getImageUrl();
                            liked[i] = matchDataSet.get(i).getLike();
                            uid[i]   = matchDataSet.get(i).getUid();
                        }
                        notifyDataSetChanged();
                    }
            );
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
            holder.matchUid = uid[position % uid.length]; //store uid for each card
            holder.isLiked = liked[position % liked.length];


            //set like button for liked matches from db.
            if(String.valueOf(holder.isLiked) == "true"){
                holder.favoriteBtn.setImageResource(R.drawable.button_pressed);
            }

            holder.matchName = holder.name.getText().toString();//set name for each card
            StringBuilder likeBtnMsg = new StringBuilder(R.string.like_button_message);
            likeBtnMsg.append(holder.matchName);
            holder.likedToastMsg = likeBtnMsg.toString();

            Log.i(TAG, "onBindViewHolder()" + position);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
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
