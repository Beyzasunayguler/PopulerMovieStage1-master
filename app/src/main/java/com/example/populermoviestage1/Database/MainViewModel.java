package com.example.populermoviestage1.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final List<Movie> favoriteMovies;
    private static final String TAG=MainViewModel.class.getSimpleName();
    private AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getDatabase(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorites from the DataBase");
        favoriteMovies= database.movieDao().loadAllMovies();
    }

    public List<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }
}
