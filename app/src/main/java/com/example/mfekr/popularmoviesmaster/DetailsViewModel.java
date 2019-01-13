package com.example.mfekr.popularmoviesmaster;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mfekr.popularmoviesmaster.Database.AppDatabase;
import com.example.mfekr.popularmoviesmaster.Model.Movie;



public class DetailsViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;

    public DetailsViewModel(AppDatabase appDatabase, int movieId) {

        movieLiveData = appDatabase.movieDao().loadMovieById(movieId);

    }

    public LiveData<Movie> getMovie() {
        return movieLiveData;
    }
}
