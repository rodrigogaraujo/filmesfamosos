package g3infotech.net.filmesfamososs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by g3infotech on 27/12/17.
 */

public class Video implements Parcelable {

    protected Video(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
    }

    public Video(String id, String key, String name){
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Expose
    private String id;

    @Expose
    private String key;

    @Expose
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
