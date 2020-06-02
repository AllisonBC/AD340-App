//package com.example.ad340app_a1;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import java.util.ArrayList;
//
//import static com.example.ad340app_a1.MatchesActivityFragment.ARG_DATA_SET;
//
//public class FirebaseMatchesActivity extends AppCompatActivity
//        implements MatchesActivityFragment.OnMatchesFragmentInteractionListener {
//
//    private FirebaseMatchViewModel viewModel;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_firebase_matches);
//
//        viewModel = new FirebaseMatchViewModel();
//
//        viewModel.getMatches(
//                (ArrayList<Match> matches) -> {
//                    FragmentManager manager = getSupportFragmentManager();
//                    MatchesActivityFragment fragment = (MatchesActivityFragment) manager.findFragmentByTag("todoItemFragment");
//
//                    if (fragment != null) {
//                        // Remove fragment to re-add it
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        transaction.remove(fragment);
//                        transaction.commit();
//                    }
//
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList(ARG_DATA_SET, matches);
//
//                    MatchesActivityFragment matchesActivityFragment = new MatchesActivityFragment();
//                    matchesActivityFragment.setArguments(bundle);
//
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.add(R.id.matchesListFragmentContainer, matchesActivityFragment, "matchesActivityFragment");
//                    transaction.commit();
//                }
//        );
//    }
//
////    public void addMatch(View view) {
////        String title = newMatchText.getText().toString();
////        Match item = new Match(title, false);
////        viewModel.addMatch(item);
////    }
//
//    @Override
//    public void onMatchesFragmentInteraction(Match item) {
//        item.liked = true;
//        // updateMatchLikeById located in FirebaseMatchModel
//        viewModel.updateMatchLikeById(item);
//    }
//
//    @Override
//    protected void onPause() {
//        viewModel.clear();
//        super.onPause();
//    }
//
//    public FirebaseMatchViewModel getViewModel() {
//        return viewModel;
//    }
//
//    public void setViewModel(FirebaseMatchViewModel vm) {
//        viewModel = vm;
//    }
//
//}
