package e.juna.mynavigationasynctask.GakDipakeContentDua;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.util.TextUtils;
import e.juna.mynavigationasynctask.MovieItems;

/**
 * Created by Juna on 2/15/2018.
 */

public class StudentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "e.juna.mynavigationasynctask.ContentDua.StudentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String JUDUL = "judul";
    public static final String RELEASE = "release";
    public static final String OVERVIEW = "overview";
    public static final String IMAGE = "image";

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int MOVIE_ID = 1;
    static final int JUDUL_MOV = 2;
    static final int RELEASE_MOV = 3;
    static final int OVERVIEW_MOV = 4;
    static final int IMAGE_MOV = 5;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", MOVIE_ID);
        uriMatcher.addURI(PROVIDER_NAME, "students", JUDUL_MOV);
        uriMatcher.addURI(PROVIDER_NAME, "students", RELEASE_MOV);
        uriMatcher.addURI(PROVIDER_NAME, "students", OVERVIEW_MOV);
        uriMatcher.addURI(PROVIDER_NAME, "students", IMAGE_MOV);
    }

    /**
     * Database specific constant declarations
     */

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "MovieCP";
    static final String STUDENTS_TABLE_NAME = "Favorite";
    static final int DATABASE_VERSION = 1;


    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + STUDENTS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " judul TEXT NOT NULL, " +
                    " release TEXT NOT NULL, " +
                    " overview TEXT NOT NULL, " +
                    " image TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(STUDENTS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(STUDENTS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;

            case JUDUL_MOV:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case RELEASE_MOV:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case OVERVIEW_MOV:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case IMAGE_MOV:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;


            default:
        }

        if (sortOrder == null || sortOrder == "") {
            /**
             * By default sort on student names
             */
            sortOrder = JUDUL;
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case JUDUL_MOV:
                count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
                break;

            case RELEASE_MOV:
                count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
                break;

            case OVERVIEW_MOV:
                count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
                break;

            case IMAGE_MOV:
                count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(STUDENTS_TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case JUDUL_MOV:
                count = db.update(STUDENTS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case RELEASE_MOV:
                count = db.update(STUDENTS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case OVERVIEW_MOV:
                count = db.update(STUDENTS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case IMAGE_MOV:
                count = db.update(STUDENTS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_ID:
                count = db.update(STUDENTS_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            /**
             * Get all student records
             */
            case JUDUL_MOV:
                return "vnd.android.cursor.dir/vnd.example.students";
            case RELEASE_MOV:
                return "vnd.android.cursor.dir/vnd.example.students";
            case OVERVIEW_MOV:
                return "vnd.android.cursor.dir/vnd.example.students";
            case IMAGE_MOV:
                return "vnd.android.cursor.dir/vnd.example.students";
            /**
             * Get a particular student
             */
            case MOVIE_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    public ArrayList<MovieItems> getAllItems() {
        ArrayList<MovieItems> items = new ArrayList<MovieItems>();

        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            cursor = query(CONTENT_URI, null, null, null, null, null);
        }
        MovieItems item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new MovieItems();
                item.setJudul(cursor.getString(JUDUL_MOV));
                item.setReleaseDate(cursor.getString(RELEASE_MOV));
                item.setOverview(cursor.getString(OVERVIEW_MOV));
                item.setImg(cursor.getString(IMAGE_MOV));
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

}