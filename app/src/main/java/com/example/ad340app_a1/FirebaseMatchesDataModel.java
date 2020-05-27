package com.example.ad340app_a1;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseMatchesDataModel {
    private FirebaseFirestore db;
    private List<ListenerRegistration> listeners;

    public FirebaseMatchesDataModel() {
        db = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
    }

    public void addMatch(Match item) {
        CollectionReference matchesRef = db.collection("matches");
        matchesRef.add(item);
    }

    public void getMatches(EventListener<DocumentSnapshot> viewModelCallback) {
        // This is where we can construct our path
        DocumentReference matchesRef = db.collection("matches").document("Document ID");
        ListenerRegistration registration = matchesRef.addSnapshotListener(viewModelCallback);
        listeners.add(registration);
    }

    // Update match liked field when button clicked
    public void updateMatchLikeById(Match item) {
        DocumentReference matchItemRef = db.collection("matches").document(item.uid);
        Map<String, Object> data = new HashMap<>();
        data.put("true", item.liked);
        matchItemRef.update(data);
    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(ListenerRegistration::remove);
    }
}
