package com.example.populermoviestage1.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "movie_table")
public class Movie implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int mId;
    @ColumnInfo(name = "movie_title")
    public String mMovieTitle;
    @ColumnInfo(name = "release_date")
    public String mReleaseDay;
    @ColumnInfo(name = "vote_average")
    public double mVoteAverage;
    @ColumnInfo(name = "poster_path")
    public String mPosterPath;
    @ColumnInfo(name = "movie_overview")
    public String mOverview;


    protected Movie(Parcel in) {
        mId = in.readInt();
        mMovieTitle = in.readString();
        mReleaseDay = in.readString();
        mVoteAverage = in.readDouble();
        mOverview = in.readString();
        mPosterPath = in.readString();
    }

    public Movie(int mId, String mMovieTitle, String mReleaseDay, double mVoteAverage, String mPosterPath, String mOverview) {
        this.mId = mId;
        this.mMovieTitle = mMovieTitle;
        this.mReleaseDay = mReleaseDay;
        this.mVoteAverage = mVoteAverage;
        this.mPosterPath = mPosterPath;
        this.mOverview = mOverview;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public String getmReleaseDay() {
        return mReleaseDay;
    }

    public void setmReleaseDay(String mReleaseDay) {
        this.mReleaseDay = mReleaseDay;
    }

    public void setmVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public Double getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(mVoteAverage);
        parcel.writeInt(mId);
        parcel.writeString(mOverview);
        parcel.writeString(mPosterPath);
        parcel.writeString(mMovieTitle);
        parcel.writeString(mReleaseDay);

    }
}
