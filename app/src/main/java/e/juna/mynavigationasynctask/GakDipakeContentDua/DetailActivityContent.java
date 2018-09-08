package e.juna.mynavigationasynctask.GakDipakeContentDua;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
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

import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.MovieNowPlaying.LoaderMovieNow;
import e.juna.mynavigationasynctask.R;

public class DetailActivityContent extends AppCompatActivity
        implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    Button btnSubmit, btnRetrieve;
    private static final int LOADER_ID = 1;
    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;

    static final String EXTRAS_CITY = "extra_city";
    private MovieItems mMovie;
    ImageView imageView, poster;
    TextView title, description, releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_detail);
        imageView = findViewById(R.id.backdrop);
        btnSubmit = (Button) findViewById(R.id.btnFav);
        btnSubmit.setOnClickListener(this);
        btnRetrieve.setOnClickListener(this);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        onClickAddName();
        //onClickRetrieveStudents();


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
    }

    public void onClickAddName() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(StudentProvider.JUDUL, mMovie.getJudul());
                // ((EditText)findViewById(R.id.editText2)).getText().toString());

                values.put(StudentProvider.RELEASE, mMovie.getReleaseDate());
                // ((EditText)findViewById(R.id.editText3)).getText().toString());

                values.put(StudentProvider.OVERVIEW, mMovie.getOverview());
                //((EditText)findViewById(R.id.editText2)).getText().toString());

                values.put(StudentProvider.IMAGE, mMovie.getImg());
                //((EditText)findViewById(R.id.editText3)).getText().toString());


                Uri uri = getContentResolver().insert(
                        StudentProvider.CONTENT_URI, values);

                Toast.makeText(getBaseContext(),
                        uri.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void onClickRetrieveStudents() {
//        // Retrieve student records
//        btnRetrieve.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String URL = "content://e.juna.mynavigationasynctask.ContentDua.StudentProvider";
//
//                Uri students = Uri.parse(URL);
//                Cursor c = managedQuery(students, null, null, null, "judul");
//
//                if (c.moveToFirst()) {
//                    do {
//                        Toast.makeText(DetailActivityContent.this, c.getString(c.getColumnIndex(StudentProvider._ID)) +
//                                        ", " + c.getString(c.getColumnIndex(StudentProvider.JUDUL)) +
//                                        ", " + c.getString(c.getColumnIndex(StudentProvider.RELEASE)) +
//                                        ", " + c.getString(c.getColumnIndex(StudentProvider.OVERVIEW)) +
//                                        ", " + c.getString(c.getColumnIndex(StudentProvider.IMAGE)),
//                                Toast.LENGTH_SHORT).show();
//                    } while (c.moveToNext());
//                }
//            }
//        });
//    }

    @Override
    public void onClick(View view) {

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
}