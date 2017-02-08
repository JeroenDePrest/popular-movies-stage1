package be.jeroendeprest.popular_movies_stage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import be.jeroendeprest.popular_movies_stage1.domain.Movie;

/**
 * Created by jeroe on 06/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private ArrayList<Movie> mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;

    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie posterIndex = mMovieData.get(adapterPosition);
            mClickHandler.onClick(posterIndex);
        }
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = mMovieData.get(position);
        Picasso.with(movieAdapterViewHolder.mMovieImageView.getContext()).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(movieAdapterViewHolder.mMovieImageView);
    }


    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }


    public void setMovieData(ArrayList<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
