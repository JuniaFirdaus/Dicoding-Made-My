package e.juna.mynavigationasynctask.MovieFavoriteContentProvider.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.getColumnInt;
import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.getColumnString;

/**
 * Created by Juna on 2/17/2018.
 */

public class MovieContent implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String date;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.image);
    }

    public MovieContent() {

    }

    public MovieContent(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.NoteColumns.TITLE);
        this.description = getColumnString(cursor, DatabaseContract.NoteColumns.OVERVIEW);
        this.date = getColumnString(cursor, DatabaseContract.NoteColumns.RELEASEDATE);
        this.image = getColumnString(cursor, DatabaseContract.NoteColumns.IMAGE);
    }

    protected MovieContent(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.image = in.readString();
    }

    public static final Creator<MovieContent> CREATOR = new Creator<MovieContent>() {
        @Override
        public MovieContent createFromParcel(Parcel source) {
            return new MovieContent(source);
        }

        @Override
        public MovieContent[] newArray(int size) {
            return new MovieContent[size];
        }
    };
}