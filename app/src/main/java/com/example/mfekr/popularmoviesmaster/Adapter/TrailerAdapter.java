package com.example.mfekr.popularmoviesmaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mfekr.popularmoviesmaster.Model.Trailer;
import com.example.mfekr.popularmoviesmaster.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    public static final String TAG = "TrailerAdapter";
    public static final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    Context mContext;
    List<Trailer> mTrailers;

    public TrailerAdapter(Context context, List<Trailer> movies) {
        this.mContext = context;
        this.mTrailers = movies;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_listitem, parent,false);
        TrailerViewHolder mviewViewHolder = new TrailerViewHolder(view);
        return mviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, final int position) {
        holder.name.setText(mTrailers.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trailer trailer = mTrailers.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mTrailers.get(position).getKey()));
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public void setTrailers(List<Trailer> trailer){
        this.mTrailers = trailer;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public View mView;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
           name = itemView.findViewById(R.id.tv_trailer);
        }
    }
}
