package com.example.populermoviestage1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    public MovieData data;

    public void setMovieData(MovieData data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MovieHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, final int i) {
        movieHolder.bind(data.results.get(i).posterPath);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.results.size();
    }


    public class MovieHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public MovieHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(itemView.getContext(), DetailActivity.class);
                    Result result = data.results.get(getAdapterPosition());
                    shareIntent.putExtra(IntentConstants.MOVIE_TITLE_KEY, result.originalTitle);
                    shareIntent.putExtra(IntentConstants.MOVIE_RELEASE_DAY_KEY,result.releaseDate);
                    shareIntent.putExtra(IntentConstants.MOVIE_OVERVIEW_KEY,result.overview);
                    shareIntent.putExtra(IntentConstants.MOVIE_VOTE_AVERAGE, result.voteAverage);
                    shareIntent.putExtra(IntentConstants.MOVIE_IMAGE, result.backdropPath);
                    shareIntent.putExtra(IntentConstants.MOVIE_ID,result.id);
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