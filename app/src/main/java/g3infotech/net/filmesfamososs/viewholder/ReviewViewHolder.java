package g3infotech.net.filmesfamososs.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import g3infotech.net.filmesfamososs.R;
import g3infotech.net.filmesfamososs.entity.Review;

/**
 * Created by g3infotech on 10/01/18.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_author) TextView mTvAuthor;
    @BindView(R.id.tv_content) TextView mTvContent;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Review review){
        mTvAuthor.setText(review.getAuthor());
        mTvContent.setText(review.getContent());
    }
}
