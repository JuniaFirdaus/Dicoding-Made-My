package e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE;

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
import e.juna.mynavigationasynctask.R;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    static final String EXTRAS_CITY = "extra_city";
    private MovieItems mMovie;
    private ImageView imageView, poster;
    private static final int LOADER_ID = 1;
    TextView title, description, releaseDate;
    Button btnSave;
    Controllerdb m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_detail);
        imageView = findViewById(R.id.backdrop);
        btnSave = findViewById(R.id.btnFav);

        m = new Controllerdb(this);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        AddData();


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

    public void AddData() {
        btnSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dJudul = mMovie.getJudul();
                        String dRelease = mMovie.getReleaseDate();
                        String dOverView = mMovie.getOverview();
                        String dImage = mMovie.getImg();

                        MovieItems d = new MovieItems();
                        d.setJudul(dJudul);
                        d.setReleaseDate(dRelease);
                        d.setOverview(dOverView);
                        d.setImg(dImage);
                        m.addItem(d);
                        Toast.makeText(DetailActivity.this, dJudul + " Added Favorite", Toast.LENGTH_SHORT).show();

//                        sampleLoader.onContentChanged();
                    }
                }
        );
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanKota = "Captain America";
        if (args != null) {
            kumpulanKota = args.getString(EXTRAS_CITY);
        }

        return new MyAsyncTaskFavorite(getApplication(), kumpulanKota);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {

    }


    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {

    }
}

