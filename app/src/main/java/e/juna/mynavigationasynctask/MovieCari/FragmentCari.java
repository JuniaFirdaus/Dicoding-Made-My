package e.juna.mynavigationasynctask.MovieCari;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.DetailActivity;
import e.juna.mynavigationasynctask.MovieAdapter;
import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCari extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    ListView listView;
    EditText editKota;
    Button buttonCari;

    static final String DETAIL_MOVIE = "detail_movie";
    public static final String SEARCH = "extra_search";
    private MovieAdapter adapter;
    private View loadingBar;

    public FragmentCari() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cari, container,false);
        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        listView = (ListView)view.findViewById(R.id.listview);
        loadingBar = (ProgressBar)view.findViewById(R.id.loading);

        editKota = (EditText)view.findViewById(R.id.edt_kota);
        buttonCari = (Button)view.findViewById(R.id.btn_kota);

        buttonCari.setOnClickListener(myListener);


        String kota = editKota.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_MOVIE, kota);
        getLoaderManager().initLoader(0, bundle, this);

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
            kumpulanKota = args.getString(DETAIL_MOVIE);
        }

        return new LoaderCariMovie(getActivity(), kumpulanKota);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String kota = editKota.getText().toString();

            // Jika edit text-nya kosong maka do nothing
            if (TextUtils.isEmpty(kota)) return;

            loadingBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString(DETAIL_MOVIE, kota);
            getLoaderManager().restartLoader(0, bundle, FragmentCari.this);
        }
    };


}