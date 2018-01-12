
package g3infotech.net.filmesfamososs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import g3infotech.net.filmesfamososs.R;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.viewholder.FilmViewHolder;

/**
 * Created by g3infotech on 06/12/17.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmViewHolder>{

    List<Movie> mFilms = new ArrayList<>();

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.film_list, parent, false);
        return new FilmViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        holder.onBind(mFilms.get(position));
    }

    @Override
    public int getItemCount() {
        if(mFilms != null)
            return mFilms.size();
        else
            return 0;
    }

    public void setDataFilm(List<Movie> dataFilm) {
        mFilms = dataFilm;
        notifyDataSetChanged();
    }

}
