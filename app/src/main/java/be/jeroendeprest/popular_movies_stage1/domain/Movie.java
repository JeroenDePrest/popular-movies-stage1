package be.jeroendeprest.popular_movies_stage1.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jeroe on 07/02/2017.
 */

public class Movie implements Parcelable {
    private String overview;
    private String title;
    private String posterPath;
    private double voteAverage;
    private String releaseDate;

    public Movie(String overview, String title, String posterPath, double voteAverage,String releaseDate) {
        this.overview = overview;
        this.title = title;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public Movie(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readDouble();
        this.releaseDate = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
