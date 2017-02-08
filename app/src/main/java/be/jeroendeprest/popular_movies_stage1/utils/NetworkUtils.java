package be.jeroendeprest.popular_movies_stage1.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by jeroe on 06/02/2017.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org";

    final static String API_KEY_PARAM = "api_key";

    public static URL buildUrl(String API_KEY,String sort) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sort)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d("URL",url.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
