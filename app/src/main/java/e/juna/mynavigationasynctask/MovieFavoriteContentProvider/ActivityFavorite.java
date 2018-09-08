package e.juna.mynavigationasynctask.MovieFavoriteContentProvider;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.adapter.MovieContentAdapter;
import e.juna.mynavigationasynctask.R;

import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.CONTENT_URI;


public class ActivityFavorite extends AppCompatActivity
        implements View.OnClickListener{
    RecyclerView rvMovie;
    ProgressBar progressBar;

    private Cursor list;
    private MovieContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dicoding);

        getSupportActionBar().setTitle("Notes");

        rvMovie = (RecyclerView)findViewById(R.id.rv_notes);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setHasFixedSize(true);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        adapter = new MovieContentAdapter(this);
        adapter.setListNotes(list);
        rvMovie.setAdapter(adapter);

        new LoadNoteAsync().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }


    public class LoadNoteAsync extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}