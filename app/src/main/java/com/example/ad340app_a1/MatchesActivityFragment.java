package com.example.ad340app_a1;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
// import com.example.ad340app_a1.Match;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMatchesFragmentInteractionListener}
 * interface.
 */
public class MatchesActivityFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    static final String ARG_DATA_SET = "data-set";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private List<Match> mDataSet;
    private OnMatchesFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchesActivityFragment() {
    }

    @SuppressWarnings("unused")
    public static MatchesActivityFragment newInstance(int columnCount) {
        MatchesActivityFragment fragment = new MatchesActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mDataSet = getArguments().getParcelableArrayList(ARG_DATA_SET);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        // Set the adapter
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ImageButton favoriteBtn;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.frag_matches, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            favoriteBtn = (ImageButton) itemView.findViewById(R.id.favorite_button);
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 18;
        private final String[] userNames;
        private final String[] userDesc;
        private final Drawable[] profilePhotos;
        private final String[] liked;
        public ImageButton favoriteBtn;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            userNames = resources.getStringArray(R.array.user_names);
            userDesc = resources.getStringArray(R.array.user_desc);
            liked = resources.getStringArray(R.array.liked);
            TypedArray a = resources.obtainTypedArray(R.array.user_profile_pictures);
            profilePhotos = new Drawable[a.length()];
            for (int i = 0; i < profilePhotos.length; i++) {
                profilePhotos[i] = a.getDrawable(i);
            }
//            resources.findViewById(R.id.favorite_button).setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    Toast.makeText(getContent(),
////                    "You liked " + name.getText().toString(), Toast.LENGTH_LONG).show();
//                }
//            });

            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.picture.setImageDrawable(profilePhotos[position % profilePhotos.length]);
            holder.name.setText(userNames[position % userNames.length]);
            holder.description.setText(userDesc[position % userDesc.length]);
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
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMatchesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMatchesFragmentInteraction(Match item);
    }
}
