package be.jeroendeprest.popular_movies_stage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.jeroendeprest.popular_movies_stage1.domain.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    private Movie movie;

    private ImageView mPoster;

    private TextView mTitle;
    private TextView mOverview;
    private TextView mVote;
    private TextView mRelease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie = getIntent().getParcelableExtra("movie");
        setTitle(movie.getTitle());

        mPoster = (ImageView) findViewById(R.id.iv_movie_poster_detail);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(mPoster);

        mTitle = (TextView) findViewById(R.id.tv_movie_title_text);
        mTitle.setText(movie.getTitle());

        mOverview = (TextView) findViewById(R.id.tv_movie_overview_text);
        mOverview.setText(movie.getOverview());

        mRelease = (TextView) findViewById(R.id.tv_movie_release_text);
        mRelease.setText(movie.getReleaseDate());

        mVote = (TextView) findViewById(R.id.tv_movie_vote_text);
        mVote.setText(movie.getVoteAverage()+" /10");

    }
}
