package com.example.mfekr.popularmoviesmaster;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mfekr.popularmoviesmaster.Database.AppDatabase;
import com.example.mfekr.popularmoviesmaster.Model.Movie;

import java.util.List;


public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movieList;


    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        movieList = appDatabase.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovieList(){
        return movieList;
    }
}
