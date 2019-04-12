package com.example.populermoviestage1.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final LiveData<List<Movie>> favoriteMovies;
    private static final String TAG=MainViewModel.class.getSimpleName();
    AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getDatabase(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorites from the DataBase");
        favoriteMovies= (LiveData<List<Movie>>) database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void deleteItem(Movie movie){
        new deleteAsyncTask(database).execute(movie);
    }

    private static class deleteAsyncTask extends AsyncTask<Movie,Void,Void>{
        private AppDatabase db;
        deleteAsyncTask(AppDatabase appDatabase){
            db=appDatabase;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            db.movieDao().deleteMovie(movies[0]);
            return null;
        }
    }
}
