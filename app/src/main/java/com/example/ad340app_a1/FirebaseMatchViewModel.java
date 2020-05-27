package com.example.ad340app_a1;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.ad340app_a1.FirebaseMatchesDataModel;
import com.example.ad340app_a1.Match;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseMatchViewModel {
    private FirebaseMatchesDataModel matchModel;

    public FirebaseMatchViewModel() {
        matchModel = new FirebaseMatchesDataModel();
    }

//    public void addMatch(Match item) {
//        matchModel.addMatch(item);
//    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        matchModel.getMatches(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot != null) {
                        ArrayList<Match> matchList = new ArrayList<>();
                        for (DocumentSnapshot todoSnapshot : querySnapshot.getDocuments()) {
                            Match item = todoSnapshot.toObject(Match.class);
                            assert item != null;
                            item.uid = todoSnapshot.getId();
                            matchList.add(item);
                        }
                        responseCallback.accept(matchList);
                    }
                },
                (databaseError -> System.out.println("Error reading matches: " + databaseError))
        );
    }

    public void updateMatchLikeById(Match item) {
        matchModel.updateMatchLikeById(item);
    }

    public void clear() {
        matchModel.clear();
    }
}
