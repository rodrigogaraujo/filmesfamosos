package g3infotech.net.filmesfamososs.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import g3infotech.net.filmesfamososs.R;
import g3infotech.net.filmesfamososs.entity.Video;

/**
 * Created by g3infotech on 06/01/18.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.img_video) ImageView mImgVideo;
    @BindView(R.id.tv_video) TextView mTvVideo;
    Video mVideo;
    Context mContext;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(Video video){
        mVideo = video;
        mTvVideo.setText(video.getName());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v="+mVideo.getKey()));

        if(intent != null){
            mContext.startActivity(intent);
        }
    }
}
