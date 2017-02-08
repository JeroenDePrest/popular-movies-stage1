package be.jeroendeprest.popular_movies_stage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import be.jeroendeprest.popular_movies_stage1.data.MoviePreferences;
import be.jeroendeprest.popular_movies_stage1.domain.Movie;
import be.jeroendeprest.popular_movies_stage1.utils.MovieJsonUtils;
import be.jeroendeprest.popular_movies_stage1.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alternateTitle();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData();
    }

    private void loadMovieData() {
        showWeatherDataView();

        String API_KEY = MoviePreferences.API_KEY;
        String sort = sortType();
        if(isOnline()) {
            new FetchMovieTask().execute(API_KEY,sort);
        }else{
            showErrorMessage();
        }
    }


    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieDetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", movie);
        startActivity(intentToStartDetailActivity);
    }


    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String API_KEY = params[0];
            String sort = params[1];
            URL weatherRequestUrl = NetworkUtils.buildUrl(API_KEY,sort);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                ArrayList<Movie> simpleJsonMovieData = MovieJsonUtils
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);
                Log.v("api_data",jsonMovieResponse);
                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            showWeatherDataView();
            if (movieData != null) {
                showWeatherDataView();
                mMovieAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }

    public String sortType(){
        if(MoviePreferences.isSortByPopularity()){
            return MoviePreferences.sort_popular;
        }else{

            return MoviePreferences.sort_rated;
        }
    }

    public void alternateTitle(){
        if(MoviePreferences.isSortByPopularity()){
            setTitle(R.string.popularity);
        }else{
            setTitle(R.string.rating);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_sort) {
            String API_KEY = MoviePreferences.API_KEY;
            MoviePreferences.sortByPopularity = !MoviePreferences.sortByPopularity;
            alternateTitle();
            String sort = sortType();
            new FetchMovieTask().execute(API_KEY,sort);
            mMovieAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
