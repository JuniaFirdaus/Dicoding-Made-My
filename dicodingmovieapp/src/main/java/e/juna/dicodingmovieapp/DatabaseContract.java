package e.juna.dicodingmovieapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract {
    private static String TABLE_NOTE = "note";

    public static final class NoteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String OVERVIEW = "description";
        public static String RELEASEDATE = "release";
        public static String IMAGE = "image";
    }

    // Authority yang digunakan
    private static final String AUTHORITY = "e.juna.mynavigationasynctask";

    // Base content yang digunakan untuk akses content provider
    static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NOTE)
            .build();

    /*
    Digunakan untuk mempermudah akses data di dalam cursor dengan parameter nama column
    */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}