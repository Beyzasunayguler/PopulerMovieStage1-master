package com.example.populermoviestage1.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY mId")
    List<Movie> loadAllMovies();

    @Query("SELECT * FROM movie_table WHERE mId = :id")
    Movie loadMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);


}
