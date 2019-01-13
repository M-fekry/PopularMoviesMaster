package com.example.mfekr.popularmoviesmaster;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfekr.popularmoviesmaster.Adapter.ReviewAdapter;
import com.example.mfekr.popularmoviesmaster.Adapter.TrailerAdapter;
import com.example.mfekr.popularmoviesmaster.Database.AppDatabase;
import com.example.mfekr.popularmoviesmaster.Model.Movie;
import com.example.mfekr.popularmoviesmaster.Model.Review;
import com.example.mfekr.popularmoviesmaster.Model.Trailer;
import com.example.mfekr.popularmoviesmaster.Utils.AppExecutor;
import com.example.mfekr.popularmoviesmaster.Utils.ReviewUtils;
import com.example.mfekr.popularmoviesmaster.Utils.TrailerUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {

    ImageView mPoster;
    TextView mTitle;
    TextView mRating;
    TextView mReleaseDate;
    TextView mOverview;
    Intent intent;

    public static final String LOG_TAG = DetailsActivity.class.getName();
    private AppDatabase mAppDatabase;

    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;
    private List<Trailer> trailerList;

    private List<Review> mReviewList;
    private RecyclerView mReviewRecylcerView;
    private ReviewAdapter mReviewAdapter;

    public static final String BASIC_URL = "http://api.themoviedb.org/3/movie/";
    public static final String VIDEOS = "/videos";
    public static final String REVIEWS = "/reviews";
    public static final String API_KEY = "?api_key=fab79d61f8bf7a29a59e741c6b2053b6";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        intent = getIntent();
        final Movie movie = intent.getParcelableExtra("movie");

        mPoster = findViewById(R.id.iv_poster);
        mTitle = findViewById(R.id.tv_movieTitle);
        mRating = findViewById(R.id.tv_rating);
        mReleaseDate = findViewById(R.id.tv_releaseDate);
        mOverview = findViewById(R.id.tv_movieOverview);
        final Button favorite = findViewById(R.id.fav_btn);

        String poster_Path = "http://image.tmdb.org/t/p/" +"w342"+ movie.getPosterPath();

        mRating.setText(movie.getVoteAverage()+"/10");
        mTitle.setText(movie.getTitle());
        Picasso.get().load(Uri.parse(poster_Path)).into(mPoster);
        mOverview.setText(movie.getOverview());
        mReleaseDate.setText(movie.getReleaseDate());

        mAppDatabase = AppDatabase.getInstance(this.getApplicationContext());

        int movieId = movie.getId();
        DetailsViewModelFactory factory = new DetailsViewModelFactory(mAppDatabase, movieId);
        DetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);

        viewModel.getMovie().observe(this, new Observer<Movie>(){

            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie == null) {
                    favorite.setText(getString(R.string.fav_btn));
                } else {
                    favorite.setText(getString(R.string.unfav_btn));
                }
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (favorite.getText().toString().equals(getString(R.string.fav_btn))) {
                    saveMovie();
                } else if (favorite.getText().toString().equals(getString(R.string.unfav_btn))) {
                   removeMovie();
                }
            }
        });

        mRecyclerView =   findViewById(R.id.recycler_view_trailer);
        trailerList = new ArrayList<>();
        mTrailerAdapter = new TrailerAdapter(this, trailerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mTrailerAdapter);

        String FULL_TRAILER_URL = BASIC_URL+movieId+VIDEOS+API_KEY;
        TrailerAsyncTask task = new TrailerAsyncTask();
                task.execute(FULL_TRAILER_URL);

        mReviewRecylcerView =   findViewById(R.id.recycler_view_review);
        mReviewList = new ArrayList<>();
        mReviewAdapter = new ReviewAdapter(this, mReviewList);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecylcerView.setLayoutManager(mlayoutManager);
        mReviewRecylcerView.setHasFixedSize(true);
        mReviewRecylcerView.setAdapter(mReviewAdapter);

        String FULL_Review_URL = BASIC_URL+movieId+REVIEWS+API_KEY;
        ReviewAsyncTask mtask = new ReviewAsyncTask();
        mtask.execute(FULL_Review_URL);

    }

    private void saveMovie(){
        final Movie movie = intent.getParcelableExtra("movie");
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.movieDao().insertMovie(movie);

            }

        });
    }

    private void removeMovie(){
        final Movie movie = intent.getParcelableExtra("movie");
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.movieDao().deleteMovie(movie);

            }
        });
    }


    private class TrailerAsyncTask extends AsyncTask<String, Void, List<Trailer>> {

        @Override
        protected List<Trailer> doInBackground(String... urls) {
            if (urls.length <1 || urls[0] == null){
                return null;
            }
            List<Trailer>  result = TrailerUtils.fetchTrailerData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Trailer> data) {
            if (data == null) {
                Toast.makeText(DetailsActivity.this, "Something went wrong, please check your internet connection and try again! ", Toast.LENGTH_SHORT).show();
            } else{
                mTrailerAdapter.setTrailers(data);
            }




        }

    }

    private class ReviewAsyncTask extends AsyncTask<String, Void, List<Review>> {

        @Override
        protected List<Review> doInBackground(String... urls) {
            if (urls.length <1 || urls[0] == null){
                return null;
            }
            List<Review>  result = ReviewUtils.fetchReviewData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Review> data) {
            if (data == null) {
                Toast.makeText(DetailsActivity.this, "Something went wrong, please check your internet connection and try again! ", Toast.LENGTH_SHORT).show();
            } else{
                mReviewAdapter.setReviews(data);
            }




        }

    }


}

