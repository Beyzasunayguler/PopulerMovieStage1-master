package com.example.populermoviestage1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.populermoviestage1.Database.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter  extends RecyclerView.Adapter<FavouriteAdapter.MovieHolder> {

    public List<Movie> data;

    public void setMovie(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new FavouriteAdapter.MovieHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.MovieHolder movieHolder, final int i) {
        movieHolder.bind(data.get(i).mPosterPath);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class MovieHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public MovieHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(itemView.getContext(), DetailActivity.class);
                    Movie movie = data.get(getAdapterPosition());
                    shareIntent.putExtra(IntentConstants.MOVIE_TITLE_KEY,movie.mMovieTitle);
                    shareIntent.putExtra(IntentConstants.MOVIE_RELEASE_DAY_KEY,movie.mReleaseDay);
                    shareIntent.putExtra(IntentConstants.MOVIE_OVERVIEW_KEY,movie.mOverview);
                    shareIntent.putExtra(IntentConstants.MOVIE_VOTE_AVERAGE, movie.mVoteAverage);
                    shareIntent.putExtra(IntentConstants.MOVIE_IMAGE, movie.mPosterPath);
                    shareIntent.putExtra(IntentConstants.MOVIE_ID,movie.mId);
                    itemView.getContext().startActivity(shareIntent);
                }
            });
        }

        public void bind(final String backdropPath) {
            poster = itemView.findViewById(R.id.posterImage);
            Picasso.get().load("https://image.tmdb.org/t/p/original" + backdropPath).fit().centerCrop().into(poster);
        }
    }

}
