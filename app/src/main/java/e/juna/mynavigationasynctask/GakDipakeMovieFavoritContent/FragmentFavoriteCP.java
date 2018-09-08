package e.juna.mynavigationasynctask.GakDipakeMovieFavoritContent;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.adapter.MovieContentAdapter;
import e.juna.mynavigationasynctask.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavoriteCP extends Fragment implements View.OnClickListener {
//    static final String EXTRAS_CITY = "extra_city";
//    private static final int LOADER_ID = 1;
//    Controllerdb m;
//    CustomeAdapterAsync customAdapter;
//    ListView mList;
//    ArrayList<MovieItems> allList;

    RecyclerView rvNotes;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    private Cursor list;
    private MovieContentAdapter adapter;
    public FragmentFavoriteCP() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_movie, container, false);

        rvNotes = (RecyclerView)view.findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotes.setHasFixedSize(true);

        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);

        adapter = new MovieContentAdapter(getActivity());
        adapter.setListNotes(list);
        rvNotes.setAdapter(adapter);

        new  LoadNoteAsync().execute();

        return view;
    }
    public class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
           // return getContentResolver().query(CONTENT_URI,null,null,null,null);
            return null;
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

    @Override
    public void onClick(View view) {

    }
}
