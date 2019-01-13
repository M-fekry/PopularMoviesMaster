package com.example.mfekr.popularmoviesmaster.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mfekr.popularmoviesmaster.Model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favoritesMovies")
    LiveData<List<Movie>>loadAllMovies();

    @Insert
    void insertMovie(Movie movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movieEntry);

    @Delete
    void deleteMovie(Movie movieEntry);

    @Query("SELECT * FROM favoritesMovies WHERE movie_id = :id")
    LiveData<Movie> loadMovieById(int id);
}
