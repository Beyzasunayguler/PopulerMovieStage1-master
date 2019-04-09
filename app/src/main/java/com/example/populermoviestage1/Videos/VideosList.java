package com.example.populermoviestage1.Videos;




import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosList {
    @SerializedName("results")
    public List<Videos> videos;

    public List<Videos> getVideos(){
        return videos;
    }
}
