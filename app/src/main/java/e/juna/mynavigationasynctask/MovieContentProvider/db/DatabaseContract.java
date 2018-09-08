package e.juna.mynavigationasynctask.MovieContentProvider.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Juna on 2/17/2018.
 */

public class DatabaseContract {
    public static String TABLE_NOTE = "note";

    /*
    Penggunaan Base Columns akan memudahkan dalam penggunaan suatu table
    Untuk id yang autoincrement sudah default ada di dalam kelas BaseColumns dengan nama field _ID
     */
    public static final class NoteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String OVERVIEW = "description";
        public static String RELEASEDATE = "release";
        public static String IMAGE = "image";
    }

    // Authority yang digunakan
    public static final String AUTHORITY = "e.juna.mynavigationasynctask";

    // Base content yang digunakan untuk akses content provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
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