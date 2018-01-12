package g3infotech.net.filmesfamososs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import g3infotech.net.filmesfamososs.R;
import g3infotech.net.filmesfamososs.entity.Video;
import g3infotech.net.filmesfamososs.viewholder.VideoViewHolder;

/**
 * Created by g3infotech on 06/01/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private List<Video> mVideos;
    public VideoAdapter(List<Video> videos){
        mVideos = videos;
        notifyDataSetChanged();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_list, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.onBind(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        if(mVideos != null)
            return mVideos.size();
        else
            return 0;
    }

    public void setVideos(List<Video> videos){
        mVideos = videos;
        notifyDataSetChanged();
    }
}
