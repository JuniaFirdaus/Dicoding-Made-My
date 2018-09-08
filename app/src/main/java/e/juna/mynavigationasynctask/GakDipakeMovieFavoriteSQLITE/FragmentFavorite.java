package e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE;


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

import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    static final String EXTRAS_CITY = "extra_city";
    private static final int LOADER_ID = 1;
    Controllerdb m;
    CustomeAdapterAsync customAdapter;
    ListView mList;
    ArrayList<MovieItems> allList;
    public FragmentFavorite() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite, container, false);

        m = new Controllerdb(getActivity());

        getLoaderManager().initLoader(LOADER_ID, null, this);

        mList = (ListView) view.findViewById(R.id.listview);

        allList =  m.getAllItems();
        customAdapter = new CustomeAdapterAsync(getActivity(), allList);
        mList.setAdapter(customAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                MovieItems weat = ((CustomeAdapterAsync)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(EXTRAS_CITY, weat);
                startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanKota = "Captain America";
        if (args != null) {
            kumpulanKota = args.getString(EXTRAS_CITY);
        }

        return new MyAsyncTaskFavorite(getActivity(), kumpulanKota);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
    }
}
