package com.example.mfekr.popularmoviesmaster;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.mfekr.popularmoviesmaster.Database.AppDatabase;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase mAppDatabase;
    private int movieID;

    public DetailsViewModelFactory(AppDatabase appDatabase, int movieId) {
        this.mAppDatabase = appDatabase;
        this.movieID = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(mAppDatabase, movieID);
    }
}
