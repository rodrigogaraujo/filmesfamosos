package g3infotech.net.filmesfamososs.entity;

import android.content.Loader;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by g3infotech on 06/12/17.
 */

public class Movie implements Parcelable {


    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Movie[size];
        }
    };

    protected Movie(Parcel in) {
        id = in.readInt();
        voteAverage = in.readDouble();
        title = in.readString();
        popularity = in.readDouble();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        if(videos != null){
            videos = in.readArrayList((ClassLoader) Video.CREATOR);
        }
    }

    public Movie(int id, String posterPath, String overview, String releaseDate,
                String title,
                 double popularity, double voteAverage,
                 int runtime, List<Video> videos){
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.popularity =popularity;
        this.voteAverage = voteAverage;
        this.runtime = runtime;
        this.videos = videos;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        if(videos != null){
            dest.writeArray(videos.toArray());
        }
    }

    @Expose
    private int id;

    @Expose @SerializedName("poster_path")
    private String posterPath;


    @Expose
    private String overview;

    @Expose @SerializedName("release_date")
    private String releaseDate;

    @Expose
    private String title;


    @Expose
    private double popularity;

    @Expose @SerializedName("vote_average")
    private double voteAverage;

    @Expose
    private int runtime;

    @Expose
    private boolean favorite;

    @Expose
    private List<Video> videos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                ", runtime=" + runtime +
                ", favorite=" + favorite +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
