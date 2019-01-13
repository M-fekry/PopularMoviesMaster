package com.example.mfekr.popularmoviesmaster.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "favoritesMovies")
public class Movie implements Parcelable{

    private Long voteCount;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private int id;
    @ColumnInfo(name = "vote_avg")
    private String voteAverage;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @ColumnInfo (name = "poster_path")
    private String posterPath;

    public Movie(Long voteCount, @NonNull int id, String voteAverage, String title, String posterPath, String overview, String releaseDate) {
        this.voteCount = voteCount;
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;

    }

    public Movie() {

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.voteAverage);
        parcel.writeString(this.title);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.overview);
        parcel.writeString(this.releaseDate);

    }

    protected Movie(Parcel in) {
        id = in.readInt();
        voteAverage = in.readString();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();


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


    public Long getVoteCount() {

        return voteCount;
    }
    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }


}
