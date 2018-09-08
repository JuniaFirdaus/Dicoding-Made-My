package e.juna.mynavigationasynctask;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;


public class MovieItems implements Parcelable {

    private int id;
    private String judul;
    private String releaseDate;
    private String overview;
    private String original_title;
    private String original_language;
    private String backdrop;
    private String img;

    protected MovieItems(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        backdrop = in.readString();
        img = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };

    public MovieItems() {

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String jJudul = object.getString("title");
            String jOverView = object.getString("overview");
            String jReleaseDate   = object.getString("release_date");
            String jOriginal_title = object.getString("original_title");
            String jOriginal_language = object.getString("original_language");
            String jBackdrop ="http://image.tmdb.org/t/p/w500"+ object.getString("backdrop_path");
            String jImage ="http://image.tmdb.org/t/p/w500"+ object.getString("poster_path");

            this.id = id;
            this.judul = jJudul;
            this.releaseDate = jReleaseDate;
            this.overview = jOverView;
            this.original_title = jOriginal_title;
            this.original_language = jOriginal_language;
            this.backdrop = jBackdrop;
            this.img = jImage;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(judul);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(backdrop);
        dest.writeString(img);
    }
}