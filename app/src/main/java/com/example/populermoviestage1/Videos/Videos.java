package com.example.populermoviestage1.Videos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.ArrayList;

public class Videos implements Parcelable {

    public static final String LOG_TAG = Videos.class.getSimpleName();

    @SerializedName("id")
    private String strID;
    @SerializedName("key")
    private String strKey;
    @SerializedName("name")
    private String strName;
    @SerializedName("site")
    private String strSite;
    @SerializedName("size")
    private String strSize;

    private Videos() {}

    public String getName() {
        return strName;
    }

    public String getKey() {
        return strKey;
    }

    public String getVideoUrl() {
        return "http://www.youtube.com/watch?v=" + strKey;
    }

    public static final Parcelable.Creator<Videos> CREATOR = new Creator<Videos>() {
        public Videos createFromParcel(Parcel source) {
            Videos video = new Videos();
            video.strID = source.readString();
            video.strKey = source.readString();
            video.strName = source.readString();
            video.strSite = source.readString();
            video.strSize = source.readString();
            return video;
        }

        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(strID);
        parcel.writeString(strKey);
        parcel.writeString(strName);
        parcel.writeString(strSite);
        parcel.writeString(strSize);
    }

    public class VideoResult {

        @SerializedName("results")
        private List<Videos> videoList = new ArrayList<>();

        public List<Videos> getVideoList() {
            return videoList;
        }
    }

}