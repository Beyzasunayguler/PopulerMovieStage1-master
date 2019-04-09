package com.example.populermoviestage1;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.populermoviestage1.Database.AppDatabase;
import com.example.populermoviestage1.Database.Movie;
import com.example.populermoviestage1.Videos.VideosApiClient;
import com.example.populermoviestage1.Videos.VideosList;
import com.example.populermoviestage1.review.ReviewList;
import com.example.populermoviestage1.review.ReviewApiClient;
import com.squareup.picasso.Picasso;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {

    private TextView movieNameText;
    private TextView movieReleaseDateText;
    private TextView movieOverviewText;
    private TextView movieVoteAverageText;
    private ImageView posterImage;
    private LinearLayout reviewHolder;

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database =Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_name").allowMainThreadQueries().build();

        movieNameText = (TextView) findViewById(R.id.movieNameText);
        movieReleaseDateText = (TextView) findViewById(R.id.movieReleaseDateText);
        movieOverviewText = (TextView) findViewById(R.id.movieOverviewText);
        movieVoteAverageText = (TextView) findViewById(R.id.movieVoteAverageText);
        posterImage = (ImageView) findViewById(R.id.posterImage);
        reviewHolder = (LinearLayout) findViewById(R.id.reviewList);

        final FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.favorite_button);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String movieName = getIntent().getExtras().getString(IntentConstants.MOVIE_TITLE_KEY);
                final String movieReleaseDate = getIntent().getExtras().getString(IntentConstants.MOVIE_RELEASE_DAY_KEY);
                final String movieOverview = getIntent().getExtras().getString(IntentConstants.MOVIE_OVERVIEW_KEY);
                final float movieVoteAverage = getIntent().getExtras().getFloat(IntentConstants.MOVIE_VOTE_AVERAGE);
                final String imagePath = getIntent().getExtras().getString(IntentConstants.MOVIE_IMAGE);
                int id = getIntent().getExtras().getInt(IntentConstants.MOVIE_ID);
                Movie currentMovie =  new Movie(id, movieName, movieReleaseDate, movieVoteAverage, imagePath, movieOverview);
                final List<Movie> movies=database.movieDao().loadAllMovies();
                boolean isFavorıted = false;
                    for(Movie temp: movies) {
                        if (temp.mId == id) {
                            isFavorıted = true;
                            break;
                        }
                    }
                if (isFavorıted) {
                    database.movieDao().deleteMovie(currentMovie);
                    Toast.makeText(DetailActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    database.movieDao().insertMovie(currentMovie);
                    Toast.makeText(DetailActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton trailerButton = (FloatingActionButton) findViewById(R.id.trailerButton);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int videoID = getIntent().getExtras().getInt(IntentConstants.MOVIE_ID);
                Call<VideosList> call = VideosApiClient.getVideosInterface().getVideos(videoID, "c9a15d538bdf660ff7bfc99552c95c0b");
                call.enqueue(new Callback<VideosList>() {
                    @Override
                    public void onResponse(Call<VideosList> call, Response<VideosList> response) {
                        VideosList videosList = response.body();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(videosList.getVideos().get(0).getVideoUrl()));
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<VideosList> call, Throwable t) {
                        Toast.makeText(DetailActivity.this, "Something went wrong, check the logs", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });


        if (getIntent().getExtras() == null) {
            Toast.makeText(this, "Something went wrong, check the logs", Toast.LENGTH_LONG).show();
        } else {
            final String movieName = getIntent().getExtras().getString(IntentConstants.MOVIE_TITLE_KEY);
            final String movieReleaseDate = getIntent().getExtras().getString(IntentConstants.MOVIE_RELEASE_DAY_KEY);
            final String movieOverview = getIntent().getExtras().getString(IntentConstants.MOVIE_OVERVIEW_KEY);
            final float movieVoteAverage = getIntent().getExtras().getFloat(IntentConstants.MOVIE_VOTE_AVERAGE);
            final String imagePath = getIntent().getExtras().getString(IntentConstants.MOVIE_IMAGE);
            int id = getIntent().getExtras().getInt(IntentConstants.MOVIE_ID);
            Call<ReviewList> call = ReviewApiClient.getReviewInterface().getReviews(id, "c9a15d538bdf660ff7bfc99552c95c0b");
            call.enqueue(new Callback<ReviewList>() {
                @Override
                public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                    movieOverviewText.setText(movieOverview);
                    movieNameText.setText(movieName);
                    movieReleaseDateText.setText(String.format("%s %s", getString(R.string.release_date2), movieReleaseDate));
                    movieVoteAverageText.setText(String.valueOf(String.format("%s %s", getString(R.string.vote), movieVoteAverage)));

                    Picasso.get().load("https://image.tmdb.org/t/p/original" + imagePath).centerCrop().fit().into(posterImage);

                    ReviewList reviewList = response.body();
                    for (int i = 0; i < reviewList.getReviews().size(); i++) {
                        View reviewLayout = getLayoutInflater().inflate(R.layout.review_layout, reviewHolder, true);
                        TextView content = (TextView) reviewLayout.findViewById(R.id.contentView);
                        content.setText(reviewList.getReviews().get(i).getContent());
                        TextView author = (TextView) reviewLayout.findViewById(R.id.authorView);
                        author.setText(reviewList.getReviews().get(i).getAuthor());
                    }

                }

                @Override
                public void onFailure(Call<ReviewList> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Something went wrong, check the logs", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }

    }


    public final Intent createMovieShareIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("plain/text")
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.add_menu);
        menuItem.setIntent(createMovieShareIntent());
        return true;
    }
}
