package be.jeroendeprest.popular_movies_stage1.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import be.jeroendeprest.popular_movies_stage1.domain.Movie;

/**
 * Created by jeroe on 07/02/2017.
 */

public final class MovieJsonUtils {

    public static ArrayList<Movie> getSimpleMovieStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String RESULTS = "results";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";

        final String ORIGINAL_TITLE = "original_title";
        final String POPULARITY = "popularity";

        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        ArrayList<Movie> parsedMovieData = new ArrayList<>();

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        JSONArray movieResultsArray = forecastJson.getJSONArray(RESULTS);

        for (int i = 0; i < movieResultsArray.length(); i++) {
            String overview;
            String title;
            String posterPath;
            double voteAverage;
            String releaseDate;

            JSONObject movie = movieResultsArray.getJSONObject(i);

            overview = movie.getString(OVERVIEW);
            title = movie.getString(ORIGINAL_TITLE);
            posterPath = movie.getString(POSTER_PATH);
            voteAverage = movie.getDouble(VOTE_AVERAGE);
            releaseDate = movie.getString(RELEASE_DATE);

            parsedMovieData.add(new Movie(overview,title,posterPath,voteAverage,releaseDate));
        }

        return parsedMovieData;
    }
}
