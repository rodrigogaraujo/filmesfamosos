package g3infotech.net.filmesfamososs.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import g3infotech.net.filmesfamososs.DetailsActivity;
import g3infotech.net.filmesfamososs.R;
import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.entity.Movie;

/**
 * Created by g3infotech on 06/12/17.
 */

public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_film_banner) ImageView mIvFilmBanner;
    Context mContext;
    Movie mFilm;

    public FilmViewHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        mContext = context;
        ButterKnife.bind(this, itemView );
    }
    public void onBind(Movie film){
        Picasso.with(mContext).load(Constants.IMAGES.BASE+film.getPosterPath()).into(mIvFilmBanner);
        mFilm = film;
    }

    @Override
    public void onClick(View v) {
        if(mFilm != null && mFilm.getId()!=0){
            Intent i = new Intent(mContext, DetailsActivity.class);
            i.putExtra(Constants.FILM.ID, mFilm);
            mContext.startActivity(i);
        }
    }
}
