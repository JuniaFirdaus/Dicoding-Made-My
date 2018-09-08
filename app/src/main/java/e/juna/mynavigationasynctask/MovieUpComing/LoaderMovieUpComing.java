package e.juna.mynavigationasynctask.MovieUpComing;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import e.juna.mynavigationasynctask.MovieItems;

/**
 * Created by Juna on 2/22/2018.
 */

public class LoaderMovieUpComing extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    private String mCariMovie;

    public LoaderMovieUpComing(final Context context, String cariMovie) {
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

    private static final String API_KEY = "4af97664fc4bc872bc1fe3d8872d5f6b";
    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                //Menggunakan synchronous karena pada dasarnya thread yang digunakan sudah asynchronous dan method
                //loadInBackground mengembalikan nilai balikan
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String obj = new String(responseBody);
                    JSONObject responseObject = new JSONObject(obj);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        MovieItems weatherItems = new MovieItems(weather);
                        movieItemses.add(weatherItems);
                    }
                } catch (Exception e) {
                    //Jika terjadi error pada saat parsing maka akan masuk ke catch()
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        });

        return movieItemses;
    }

    protected void onReleaseResources(ArrayList<MovieItems> data) {
        //nothing to do.
    }
}
