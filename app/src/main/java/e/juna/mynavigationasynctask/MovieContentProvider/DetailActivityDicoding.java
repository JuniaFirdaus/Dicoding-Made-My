package e.juna.mynavigationasynctask.MovieContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.MovieContentProvider.db.NoteHelper;
import e.juna.mynavigationasynctask.MovieContentProvider.entity.Note;
import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.MovieNowPlaying.LoaderMovieNow;
import e.juna.mynavigationasynctask.R;

import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.CONTENT_URI;
import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.NoteColumns.IMAGE;
import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.NoteColumns.OVERVIEW;
import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.NoteColumns.RELEASEDATE;
import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.NoteColumns.TITLE;

public class DetailActivityDicoding extends AppCompatActivity
        implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    Button btnSubmit, btnRetrieve;
    private static final int LOADER_ID = 1;
    static final String EXTRAS_CITY = "extra_city";
    private MovieItems mMovie;
    ImageView imageView, poster;
    TextView title, description, releaseDate;

    private boolean isEdit = false;

    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;
    private Note note;
    //private int position;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.backdrop);
        btnSubmit = (Button) findViewById(R.id.btnFav);
        btnSubmit.setOnClickListener(this);

        noteHelper = new NoteHelper(this);
        noteHelper.open();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
         onClickADD();

        if (getIntent().hasExtra(EXTRAS_CITY)) {
            mMovie = getIntent().getParcelableExtra(EXTRAS_CITY);
            String dJudul = mMovie.getJudul();
            String dRelease = mMovie.getReleaseDate();
            String dOverView = mMovie.getOverview();
            String dImage = mMovie.getImg();
            String address = dJudul + " " + title + ", " + dRelease + ", " + dOverView + ", " + dImage;
            imageView.setTag(address);

        } else {
            throw new IllegalArgumentException("Detail activity Parcelable");
        }

        title = findViewById(R.id.movie_title);
        releaseDate = findViewById(R.id.movie_releaseDate);
        description = findViewById(R.id.movie_description);
        poster = findViewById(R.id.movie_poster);

        title.setText(mMovie.getJudul());
        releaseDate.setText(mMovie.getReleaseDate());
        description.setText(mMovie.getOverview());
        Picasso.with(this)
                .load(mMovie.getImg())
                .into(imageView);


        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {

                if (cursor.moveToFirst()) note = new Note(cursor);
                cursor.close();
            }
        }

        String actionBarTitle = null;
        String btnTitle = null;

        if (note != null) {
            isEdit = true;

            actionBarTitle = "Ubah";
            btnTitle = "Update";

            title.setText(note.getTitle());
            description.setText(note.getDescription());
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setText(btnTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }

    public void onClickADD() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gunakan contentvalues untuk menampung data
                ContentValues values = new ContentValues();
                values.put(TITLE,mMovie.getJudul());
                values.put(OVERVIEW, mMovie.getOverview());
                values.put(RELEASEDATE, mMovie.getReleaseDate());
                values.put(IMAGE, mMovie.getImg());
                getContentResolver().insert(CONTENT_URI, values);
                setResult(RESULT_ADD);
                finish();
            }
        });

        }
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanKota = "Captain America";
        if (args != null) {
            kumpulanKota = args.getString(EXTRAS_CITY);
        }

        return new LoaderMovieNow(getApplication(), kumpulanKota);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {

    }

    @Override
    public void onClick(View view) {

    }
}