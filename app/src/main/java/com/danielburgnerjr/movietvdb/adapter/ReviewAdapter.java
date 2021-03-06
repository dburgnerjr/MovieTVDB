package com.danielburgnerjr.movietvdb.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielburgnerjr.movietvdb.R;
import com.danielburgnerjr.movietvdb.model.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final ArrayList<Review> mReviews;
    private final Callbacks mCallbacks;

    public ReviewAdapter(ArrayList<Review> reviews, Callbacks callbacks) {
        mReviews = reviews;
        mCallbacks = callbacks;
    }

    public interface Callbacks {
        void read(Review review, int position);
    }

    @Override
    @NonNull
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_list, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewViewHolder holder, final int position) {
        final Review review = mReviews.get(position);

        holder.mReview = review;
        holder.mAuthorView.setText(review.getAuthor());
        holder.mContentView.setText(review.getContent());

        holder.mView.setOnClickListener(v -> mCallbacks.read(review, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView mAuthorView;
        TextView mContentView;
        public Review mReview;

        public ReviewViewHolder(View view) {
            super(view);
            mAuthorView = view.findViewById(R.id.review_author);
            mContentView = view.findViewById(R.id.review_content);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }

    public void add(List<Review> reviews) {
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> mReview) {
        mReviews.clear();
        mReviews.addAll(mReview);
        notifyDataSetChanged();
    }
}
