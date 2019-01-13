package com.example.mfekr.popularmoviesmaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfekr.popularmoviesmaster.DetailsActivity;
import com.example.mfekr.popularmoviesmaster.Model.Movie;
import com.example.mfekr.popularmoviesmaster.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";
    public static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    Context mContext;
    List<Movie> mMovies;

    public RecyclerViewAdapter(Context context,List<Movie> mMovies) {

        this.mContext = context;
        this.mMovies = mMovies;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_listitem, parent,false);
        ViewHolder mviewViewHolder = new ViewHolder(view);
        return mviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String poster_Path = "http://image.tmdb.org/t/p/" +"w342"+ mMovies.get(position).getPosterPath();
        Picasso.get().load(Uri.parse(poster_Path)).into(holder.poster);
        holder.title.setText(mMovies.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Movie movie = mMovies.get(position);
                Intent i = new Intent(mContext,DetailsActivity.class);
                i.putExtra("movie",movie);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (!isNetworkAvailable()) {
            Toast.makeText(mContext, "Network not available!", Toast.LENGTH_SHORT).show();
        }
        return mMovies.size();

    }

    public void setMovie(List<Movie> movie){
        this.mMovies = movie;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView poster;
        public TextView title;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mContext = itemView.getContext();
            poster = itemView.findViewById(R.id.iv_poster);
            title = itemView.findViewById(R.id.tv_movieTitle);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}

