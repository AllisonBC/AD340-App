package com.example.ad340app_a1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Match implements Parcelable {
    public String uid;
    public String title;
    public boolean liked;

    public Match() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Match(String title, boolean liked) {
//        this.title = title;
        this.liked = liked;
    }

    public Match(Parcel in) {
//        title = in.readString();
        liked = in.readByte() != 0;
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
//        result.put("title", title);
        result.put("true", liked);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // tells parcel class how to write object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (liked ? 1 : 0));
    }
}
