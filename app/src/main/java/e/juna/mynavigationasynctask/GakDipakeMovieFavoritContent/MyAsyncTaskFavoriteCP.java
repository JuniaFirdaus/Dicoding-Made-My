package e.juna.mynavigationasynctask.GakDipakeMovieFavoritContent;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE.Controllerdb;
import e.juna.mynavigationasynctask.MovieItems;

/**
 * Created by Juna on 2/14/2018.
 */

public class MyAsyncTaskFavoriteCP extends AsyncTaskLoader<ArrayList<MovieItems>> {
    public static final String TAG = "MyAsyncTaskFavorite";
    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    private String mCariMovie;

    public MyAsyncTaskFavoriteCP(final Context context, String cariMovie) {
        super(context);
        onContentChanged();
        this.mCariMovie = cariMovie;
    }

    //Ketika data loading,
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {

        Controllerdb mySQLiteHelper = new Controllerdb(getContext());
        List allItems = mySQLiteHelper.getAllItems();
        Log.i(TAG,"Size : " + allItems.size());
        return null;
    }

    protected void onReleaseResources(ArrayList<MovieItems> data) {
        //nothing to do.
    }
}