package com.example.mfekr.popularmoviesmaster.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mfekr.popularmoviesmaster.Model.Review;
import com.example.mfekr.popularmoviesmaster.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    Context mContext;
    List<Review> mReviews;

    public ReviewAdapter(Context context, List<Review> movies) {
        this.mContext = context;
        this.mReviews = movies;
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_listitem, parent,false);
        ReviewViewHolder mviewViewHolder = new ReviewViewHolder(view);
        return mviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.author.setText("Author name: " + mReviews.get(position).getAuthor());
        holder.content.setText(mReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void setReviews(List<Review> review){
        this.mReviews = review;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView author;
        public TextView content;
        public View mView;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            author = itemView.findViewById(R.id.tv_author);
            content = itemView.findViewById(R.id.tv_content);
        }
    }
}
