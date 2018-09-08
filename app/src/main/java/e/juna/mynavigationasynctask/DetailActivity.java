package e.juna.mynavigationasynctask;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.MovieContentHelper;
import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.entity.MovieContent;
import e.juna.mynavigationasynctask.MovieNowPlaying.LoaderMovieNow;

import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.CONTENT_URI;
import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.NoteColumns.IMAGE;
import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.NoteColumns.OVERVIEW;
import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.NoteColumns.RELEASEDATE;
import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.NoteColumns.TITLE;

public class DetailActivity extends AppCompatActivity
        implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    Button btnSubmit;
    private static final int LOADER_ID = 1;
    static final String DETAIL_MOVIE = "detail_movie";
    private MovieItems mMovie;
    ImageView imageView, poster;
    TextView title, description, releaseDate, originalTitle, originalLanguage;

    public static int RESULT_ADD = 101;
    private MovieContent note;
    private MovieContentHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_detail);
        imageView = findViewById(R.id.backdrop);
        btnSubmit = findViewById(R.id.btnFav);
        btnSubmit.setOnClickListener(this);

        noteHelper = new MovieContentHelper(this);
        noteHelper.open();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
         onClickADD();

        if (getIntent().hasExtra(DETAIL_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(DETAIL_MOVIE);
            String dJudul = mMovie.getJudul();
            String dRelease = mMovie.getReleaseDate();
            String dOverView = mMovie.getOverview();
            String dOT = mMovie.getOriginal_title();
            String dOL = mMovie.getOriginal_language();
            String dBack = mMovie.getBackdrop();
            String dImage = mMovie.getImg();
            String address = dJudul + " " + title + ", " + dRelease + ", " + dOverView + ", " + dImage + dOT + dOL + dBack;
            imageView.setTag(address);

        } else {
            throw new IllegalArgumentException("Detail activity Parcelable");
        }

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getJudul());

        title = findViewById(R.id.movie_title);
        releaseDate = findViewById(R.id.movie_releaseDate);
        description = findViewById(R.id.movie_description);
        originalTitle = findViewById(R.id.original_title);
        originalLanguage = findViewById(R.id.original_language);
        poster = findViewById(R.id.profile_image);

        title.setText(mMovie.getJudul());
        releaseDate.setText(mMovie.getReleaseDate());
        description.setText(mMovie.getOverview());
        originalTitle.setText(mMovie.getOriginal_title());
        originalLanguage.setText(mMovie.getOriginal_language());

        Picasso.with(this)
                .load(mMovie.getImg())
                .into(imageView);

 Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(poster);


        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {

                if (cursor.moveToFirst()) note = new MovieContent(cursor);
                cursor.close();
            }
        }

        String btnTitle;

        if (note != null) {

            btnTitle = "Update";

            title.setText(note.getTitle());
            description.setText(note.getDescription());
        } else {
            btnTitle = "Simpan";
        }

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
                Toast.makeText(DetailActivity.this,"Adding Favorite", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        }
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanKota = "Captain America";
        if (args != null) {
            kumpulanKota = args.getString(DETAIL_MOVIE);
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