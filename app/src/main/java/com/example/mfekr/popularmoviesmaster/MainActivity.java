package com.example.mfekr.popularmoviesmaster;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.mfekr.popularmoviesmaster.Adapter.RecyclerViewAdapter;
import com.example.mfekr.popularmoviesmaster.Model.Movie;
import com.example.mfekr.popularmoviesmaster.Utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    public static final String Popular_URL = "https://api.themoviedb.org/3/movie/popular?api_key=fab79d61f8bf7a29a59e741c6b2053b6";
    public static final String TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=fab79d61f8bf7a29a59e741c6b2053b6";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<Movie> movieList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView =   findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(this, movieList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2,1,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);
        MovieAsyncTask task = new MovieAsyncTask();
        task.execute(Popular_URL);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MovieAsyncTask task = new MovieAsyncTask();
        switch (item.getItemId())
        {
            case R.id.popular:
                task.execute(Popular_URL);
                return true;
            case R.id.top_rated:
                task.execute(TOP_RATED_URL);
                return true;
            case R.id.fav_movies:
                setupViewModel();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieEntries) {
                Log.d(LOG_TAG, "Updating list of Movies from LiveData in ViewModel");
                mAdapter.setMovie(movieEntries);
            }
        });
    }
    private class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... urls) {
            if (urls.length <1 || urls[0] == null){
                return null;
            }
            List<Movie>  result = QueryUtils.fetchMovieData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> data) {
            if (data == null) {
                Toast.makeText(MainActivity.this, "Something went wrong, please check your internet connection and try again! ", Toast.LENGTH_SHORT).show();
            } else{
                mAdapter.setMovie(data);
            }




        }

    }


}