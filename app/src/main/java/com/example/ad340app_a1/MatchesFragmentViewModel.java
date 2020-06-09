package com.example.ad340app_a1;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;


public class MatchesFragmentViewModel {

    private static final String TAG = MatchesFragmentViewModel.class.getSimpleName();
    private FirebaseMatchDataModel matchModel;

    public MatchesFragmentViewModel() {
        matchModel = new FirebaseMatchDataModel();
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        matchModel.getMatches((QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<Match> matchList = new ArrayList<>();
                        for (DocumentSnapshot matchSnapshot : querySnapshot.getDocuments()) {
                            Match item = matchSnapshot.toObject(Match.class);
                            assert item != null;
                            item.uid = matchSnapshot.getId();
                            matchList.add(item);
                        }
                        responseCallback.accept(matchList);
                    }
                },
                (databaseError -> Log.i(TAG, "Error reading matches"))
//                (databaseError -> System.out.println("Error reading matches: " + databaseError))
        );
    }

    public void updateMatchLikeById(Match item) {
        matchModel.updateMatchLikeById(item);
    }

    public void clear(){
    matchModel.clear();
    }
}
