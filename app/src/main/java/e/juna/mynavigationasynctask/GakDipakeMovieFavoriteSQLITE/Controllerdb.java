package e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.MovieItems;

/**
 * Created by Juna on 2/11/2018.
 */
 //query = "CREATE TABLE IF NOT EXISTS Movie(Id INTEGER PRIMARY KEY AUTOINCREMENT,Judul VARCHAR,Release VARCHAR,Overview VARCHAR,Image VARCHAR);";
   //      db.execSQL(query);

public class Controllerdb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MovieFavorite";
    private static final String TABLE_NAME = "Movie";
    public static final String ID = "ID";
    private static final String JUDUL = "JUDUL";
    private static final String TANGGAL = "TANGGAL";
    private static final String OVERVIEW = "OVERVIEW";
    private static final String IMAGE = "IMAGE";
    private static Controllerdb dbInstance;
    private static SQLiteDatabase db;

    public Controllerdb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static Controllerdb getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new Controllerdb(context);
            db = dbInstance.getWritableDatabase();
        }
        return dbInstance;
    }

    @Override
    public synchronized void close() {
        super.close();
        if (dbInstance != null) {
            dbInstance.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,JUDUL VARCHAR, TANGGAL VARCHAR,OVERVIEW VARCHAR, IMAGE VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String judul, String tanggal, String overview, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL, judul);
        contentValues.put(TANGGAL, tanggal);
        contentValues.put(OVERVIEW, overview);
        contentValues.put(IMAGE, image);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void addItem(MovieItems item) {

        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(JUDUL, item.getJudul()); // get title
        values.put(TANGGAL, item.getReleaseDate()); // get author
        values.put(OVERVIEW, item.getOverview()); // get author
        values.put(IMAGE, item.getImg()); // get author

        // Insert
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // close the db
        db.close();

    }

    public ArrayList<MovieItems> getAllItems() {
        ArrayList<MovieItems> items = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        MovieItems item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new MovieItems();
                item.setJudul(cursor.getString(1));
                item.setReleaseDate(cursor.getString(2));
                item.setOverview(cursor.getString(3));
                item.setImg(cursor.getString(4));
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

}
