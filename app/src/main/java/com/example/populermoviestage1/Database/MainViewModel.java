package com.example.populermoviestage1.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> favoriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database=AppDatabase.getDatabase(this.getApplication());
       // favoriteMovies=database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {

        return favoriteMovies;
    }
}
