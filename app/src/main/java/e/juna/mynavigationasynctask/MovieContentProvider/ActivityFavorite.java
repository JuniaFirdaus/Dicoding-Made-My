package e.juna.mynavigationasynctask.MovieContentProvider;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import e.juna.mynavigationasynctask.MovieContentProvider.adapter.NoteAdapter;
import e.juna.mynavigationasynctask.R;

import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.CONTENT_URI;


public class ActivityFavorite extends AppCompatActivity
        implements View.OnClickListener{
    RecyclerView rvNotes;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    private Cursor list;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dicoding);

        getSupportActionBar().setTitle("Notes");

        rvNotes = (RecyclerView)findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        adapter = new NoteAdapter(this);
        adapter.setListNotes(list);
        rvNotes.setAdapter(adapter);

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