package e.juna.mynavigationasynctask.MovieNowPlaying;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.DetailActivity;
import e.juna.mynavigationasynctask.MovieAdapter;
import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNow extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>  {

    static final String NOW_PLAYING = "Now_Playing";
    static final String DETAIL_MOVIE = "detail_movie";
    private MovieAdapter adapter;
    ListView listView;

    public FragmentNow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_movie, container, false);

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        listView = view.findViewById(R.id.listview);
        getLoaderManager().initLoader(0, null, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                MovieItems weat = ((MovieAdapter)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DETAIL_MOVIE, weat);
                startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanKota = "Captain America";
        if (args != null) {
            kumpulanKota = args.getString(NOW_PLAYING);
        }

        return new LoaderMovieNow(getActivity(), kumpulanKota);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }
}
