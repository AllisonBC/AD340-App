//package com.example.ad340app_a1;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
///**
// * {@link RecyclerView.Adapter} that can display a {@link Match} and makes a call to the
// * specified {@link OnMatchesFragmentInteractionListener}.
// */
//public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {
//
//    private final List<Match> mValues;
//    private final OnMatchesFragmentInteractionListener mListener;
//
//    public MatchRecyclerViewAdapter(List<Match> items, OnMatchesFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.frag_matches, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
////        holder.mIdView.setText(String.format("%s", mValues.get(position).done));
//        holder.mNameView.setText(mValues.get(position).name);
//        // TODO: mButtonView needs
////        holder.mButtonView.setText(String.format("%s", mValues.get(position).liked));
//
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onMatchesFragmentInteraction(holder.mItem);
//                    Toast.makeText(v.getContext(),
//                   "You liked " + mValues.get(position).name, Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
////        public final TextView mIdView;
//        public final TextView mNameView;
//        public final ImageView mImageView;
//        public final ImageButton mButtonView;
////        public final TextView mContentView;
//        public Match mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mNameView = view.findViewById(R.id.card_title);
//            mImageView = view.findViewById(R.id.card_image);
//            mButtonView = view.findViewById(R.id.favorite_button);
////            mIdView = (TextView) view.findViewById(R.id.item_number);
////            mContentView = (TextView) view.findViewById(R.id.card_text);
//        }
//
////        @Override
////        public String toString() {
////            return super.toString() + " '" + mContentView.getText() + "'";
////        }
//    }
//
//
////    RecyclerView recyclerView = (RecyclerView) inflater.inflate(
////            R.layout.recycler_view, container, false);
////    // Set the adapter
////    ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
////        recyclerView.setAdapter(adapter);
////        recyclerView.setHasFixedSize(true);
////        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////        return recyclerView;
//
//
//}
