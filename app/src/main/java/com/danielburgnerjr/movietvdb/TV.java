package com.danielburgnerjr.movietvdb;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TV implements Parcelable {
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    @SerializedName("id")
    private String strId;

    @SerializedName("name")
    private String strTitle;

    @SerializedName("poster_path")
    private String strPoster;

    @SerializedName("overview")
    private String strDescription;

    @SerializedName("backdrop_path")
    private String strBackdrop;

    @SerializedName("first_air_date")
    private String strReleaseDate;

    @SerializedName("vote_average")
    private double dUserRating;

    public TV() {}

    protected TV(Parcel in) {
        strId = in.readString();
        strTitle = in.readString();
        strPoster = in.readString();
        strDescription = in.readString();
        strBackdrop = in.readString();
        strReleaseDate = in.readString();
        dUserRating = in.readDouble();
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) { return new TV(in); }

        @Override
        public TV[] newArray(int nSize) { return new TV[nSize]; }
    };

    public String getId() {
        return strId;
    }

    public void setId(String strI) {
        this.strId = strI;
    }

    public String getTitle() {
        return strTitle;
    }

    public void setTitle(String strT) {
        this.strTitle = strT;
    }

    public String getPoster() {
        return TMDB_IMAGE_PATH + strPoster;
    }

    public void setPoster(String strP) {
        this.strPoster = strP;
    }

    public String getDescription() {
        return strDescription;
    }

    public void setDescription(String strD) {
        this.strDescription = strD;
    }

    public String getBackdrop() {
        return TMDB_IMAGE_PATH + strBackdrop;
    }

    public void setBackdrop(String strB) {
        this.strBackdrop = strB;
    }

    public void setReleaseDate(String strRD) {
        this.strReleaseDate = strRD;
    }

    public String getReleaseDate() {
        return strReleaseDate;
    }

    public void setUserRating(double dUR) {
        this.dUserRating = dUR;
    }

    public double getUserRating() {
        return dUserRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parP, int nI) {
        parP.writeString(strId);
        parP.writeString(strTitle);
        parP.writeString(strPoster);
        parP.writeString(strDescription);
        parP.writeString(strBackdrop);
        parP.writeString(strReleaseDate);
        parP.writeDouble(dUserRating);
    }

    public static class TVResult {
        @SerializedName("results")
        private List<TV> mResults;

        public List<TV> getResults() {
            return mResults;
        }

        public int getSize() {
            return mResults.size();
        }
    }
}
