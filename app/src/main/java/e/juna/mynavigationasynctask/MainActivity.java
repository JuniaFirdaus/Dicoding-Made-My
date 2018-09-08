package e.juna.mynavigationasynctask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.bumptech.glide.Glide;
import java.util.Calendar;
import de.hdodenhof.circleimageview.CircleImageView;
import e.juna.mynavigationasynctask.MovieCari.FragmentCari;
import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.FragmentFavorite;
import e.juna.mynavigationasynctask.MovieNowPlaying.FragmentNow;
import e.juna.mynavigationasynctask.MoviePopular.FragmentPopular;
import e.juna.mynavigationasynctask.MovieTopRated.FragmentTopRated;
import e.juna.mynavigationasynctask.MovieUpComing.FragmentUpComing;
import e.juna.mynavigationasynctask.NotifDailyReminder.AlarmPreference;
import e.juna.mynavigationasynctask.NotifDailyReminder.AlarmReceiver;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "GetNow";
    private static final String API_KEY = "4af97664fc4bc872bc1fe3d8872d5f6b";
    CircleImageView profileCircleImageView;
  //  String profileImageUrl = "https://media.licdn.com/mpr/mpr/shrinknp_400_400/AAEAAQAAAAAAAAb8AAAAJGVlMmE5ZmNiLTZlMDQtNDcyMi04OWUzLTcwYWIxZTMzYjhmZA.jpg";
    String profileImageUrl = "https://media.licdn.com/dms/image/C5103AQGaGmdlXMioJw/profile-displayphoto-shrink_100_100/0?e=1538611200&v=beta&t=Jej3_VW7WHizE0QmVnD6T1O7LHKGkB-Qh1dYAhBSO6Y";
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    public static final int NOTIFICATION_ID = 1;
    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;
    private final int NOTIF_ID_REPEATING = 101;
    private Calendar  calRepeatTimeTime, calRepeatTimeTime2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie");
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        profileCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Glide.with(MainActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
        navigationView.setNavigationItemSelectedListener(this);


//        SchedulerTask mSchedulerTask = new SchedulerTask(this);
//        mSchedulerTask.createPeriodicTask();
        //Toast.makeText(this, "Periodic Task Created", Toast.LENGTH_SHORT).show();


//        GetMovieGCM movieGCM = new GetMovieGCM();
//        movieGCM.onInitializeTasks();
//
//        calRepeatTimeTime2 = Calendar.getInstance();
//        calRepeatTimeTime = Calendar.getInstance();
//        alarmPreference = new AlarmPreference(this);
//        alarmReceiver = new AlarmReceiver();
//        alarmPreference.getRepeatingTime(); // setRepeatingText();
//        alarmPreference.getRepeatingTime2(); // setRepeatingText();


        if (savedInstanceState == null) {
            Fragment currentFragment = new FragmentNow();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }

//        if (!TextUtils.isEmpty(alarmPreference.getRepeatingTime())){

     //   }



       // new JSONParse().execute();
        //onRepeat();
    }

//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            nm.notify(NOTIFICATION_ID, notification.build());
//        }
//    };


//    public void onRepeat() {
//        calRepeatTimeTime2.set(Calendar.HOUR_OF_DAY, 7);
//        calRepeatTimeTime2.set(Calendar.MINUTE, 21);
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        String repeatTimeTime = timeFormat.format(calRepeatTimeTime.getTime());
//        String repeatTimeMessage = "Missing You";
//        alarmPreference.setRepeatingTime2(repeatTimeTime);
//        alarmPreference.setRepeatingMessage2(repeatTimeMessage);
//
//        alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
//                alarmPreference.getRepeatingTime2(), alarmPreference.getRepeatingMessage2());
//
//    }

//    private class JSONParse extends AsyncTask<String, String, JSONObject>{
//
//        @Override
//        protected JSONObject doInBackground(String... strings) {
//
//            SyncHttpClient client = new SyncHttpClient();
//            String url = "http://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY;
//            client.get(url, new AsyncHttpResponseHandler() {
//                @Override
//                public void onStart() {
//                    super.onStart();
//
//                    //Menggunakan synchronous karena pada dasarnya thread yang digunakan sudah asynchronous dan method
//                    //loadInBackground mengembalikan nilai balikan
//                    setUseSynchronousMode(true);
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    String result = new String(responseBody);
//                    Log.d(TAG, result);
//                    try {
//                        JSONObject responseObject = new JSONObject(result);
//                        String nowMovie = responseObject.getJSONArray("results").getJSONObject(0).getString("title");
//                        String releaseMovie = responseObject.getJSONArray("results").getJSONObject(0).getString("release_date");
//                        String title = nowMovie;
//                        String message ="Today "+ nowMovie + " Release "+releaseMovie ;
//                        //int notifId = 1;
//                       // showNotification(getApplicationContext(), title, message, notifId);
//
//                        calRepeatTimeTime.set(Calendar.HOUR_OF_DAY, 7);
//                        calRepeatTimeTime.set(Calendar.MINUTE, 10);
//                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
//                        String repeatTimeTime = timeFormat.format(calRepeatTimeTime.getTime());
//                        alarmPreference.setRepeatingTime(repeatTimeTime);
//                        alarmPreference.setRepeatingMessage(message);
//
//                        alarmReceiver.setRepeatingAlarm(MainActivity.this, AlarmReceiver.TYPE_REPEATING,
//                                alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    //Jika response gagal maka , do nothing
//                }
//            });
//
//            return null;
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String title = "";
        Bundle bundle = new Bundle();
        Fragment frag = null;
        if (id == R.id.action_settings) {
            title="Push Notification";
            frag = new FragmentSetting();
            bundle.putString(FragmentSetting.SETTING,"Setting");
            frag.setArguments(bundle);
        }
        if (frag != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, frag)
                    .commit();
        }
        getSupportActionBar().setTitle(title);
        return true;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Bundle bundle = new Bundle();

        Fragment fragment = null;

        String title = "";

        if (id == R.id.now_playing) {
            title = "Now Playing";
            fragment = new FragmentNow();

        } else if (id == R.id.top_rated) {
            title = "Top Rated";
            fragment = new FragmentTopRated();
            bundle.putString(FragmentTopRated.TOP_RATED, "Search Movie");
            fragment.setArguments(bundle);


        } else if (id == R.id.popular_movie) {
            title = "Popular";
            fragment = new FragmentPopular();
            bundle.putString(FragmentPopular.POPULAR, "Search Movie");
            fragment.setArguments(bundle);

        } else if (id == R.id.up_coming) {
            title = "Up Coming";
            fragment = new FragmentUpComing();
            bundle.putString(FragmentUpComing.UP_COMING, "Search Movie");
            fragment.setArguments(bundle);

        } else if (id == R.id.favorite_movie) {
            title = "Favorite";
            fragment = new FragmentFavorite();
            bundle.putString(FragmentFavorite.FAVORITE, "Favorite");
            fragment.setArguments(bundle);

        } else if (id == R.id.search_movie) {
            title = "Search";
            fragment = new FragmentCari();
            bundle.putString(FragmentCari.SEARCH, "Search Movie");
            fragment.setArguments(bundle);
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}