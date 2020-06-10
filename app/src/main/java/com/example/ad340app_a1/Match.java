package com.example.ad340app_a1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Match implements Parcelable {
    public String uid;
    public String name;
    public String imageUrl;
    public String lat;
    public String longitude;
    public boolean liked;

    public Match() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Match(Parcel in) {
        uid = in.readString();
        name = in.readString();
        liked = in.readByte() != 0;
        imageUrl = in.readString();
        lat = in.readString();
        longitude = in.readString();
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

//    // ???
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        //        result.put("title", title);
//        result.put("uid", uid);
//        result.put("name", name);
//        result.put("true", liked);
//        return result;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Getters
    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public boolean getLike() {
        return liked;
    }

    public String getLat() {
        return lat;
    }

    public String getLongitude() {
        return longitude;
    }



    // Setters
    public void setLike(boolean liked) {
        this.liked = liked;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // tells parcel class how to write object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (liked ? 1 : 0));
    }
}
