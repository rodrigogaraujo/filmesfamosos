package g3infotech.net.filmesfamososs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import g3infotech.net.filmesfamososs.R;
import g3infotech.net.filmesfamososs.entity.Review;
import g3infotech.net.filmesfamososs.viewholder.ReviewViewHolder;

/**
 * Created by g3infotech on 10/01/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<Review> mReviews;

    public ReviewAdapter(List<Review> reviews){
        mReviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.onBind(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        if(mReviews != null)
            return mReviews.size();
        else
            return 0;
    }

    public void setReviews(List<Review> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
