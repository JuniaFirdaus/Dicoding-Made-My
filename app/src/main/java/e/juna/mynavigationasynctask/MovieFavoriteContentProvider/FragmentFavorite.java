package e.juna.mynavigationasynctask.MovieFavoriteContentProvider;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE.Controllerdb;
import e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE.CustomeAdapterAsync;
import e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE.DetailActivity;
import e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE.MyAsyncTaskFavorite;
import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.adapter.MovieContentAdapter;
import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.R;

import static e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment implements View.OnClickListener {
    static final String EXTRAS_CITY = "extra_city";
    public static final String FAVORITE = "Favorite";
    RecyclerView rvMovie;
    ProgressBar progressBar;

    private Cursor list;
    private MovieContentAdapter adapter;

    public FragmentFavorite() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_dicoding, container, false);

        rvMovie = (RecyclerView)view.findViewById(R.id.rv_notes);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);

        adapter = new MovieContentAdapter(getActivity());
        adapter.setListNotes(list);
        rvMovie.setAdapter(adapter);

         new  LoadNoteAsync().execute();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }


    public class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
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
    public void onDestroy() {
        super.onDestroy();
    }
}