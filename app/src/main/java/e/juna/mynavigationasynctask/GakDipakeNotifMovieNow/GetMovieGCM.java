package e.juna.mynavigationasynctask.GakDipakeNotifMovieNow;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import e.juna.mynavigationasynctask.NotifDailyReminder.AlarmPreference;
import e.juna.mynavigationasynctask.NotifDailyReminder.AlarmReceiver;
import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 2/23/2018.
 */

public class GetMovieGCM extends GcmTaskService{
    public static final String TAG = "GetMovie";
    private final String API_KEY = "4af97664fc4bc872bc1fe3d8872d5f6b";
    public static String TAG_TASK_WEATHER_LOG = "MovieTask";

    private Calendar  calRepeatTimeTime, calOneTimeDate;
    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_WEATHER_LOG)){
            getCurrentWeather();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }
    private void getCurrentWeather(){
        Log.d("GetWeather", "Running");
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    String nowMovie = responseObject.getJSONArray("results").getJSONObject(0).getString("title");
                    String releaseMovie = responseObject.getJSONArray("results").getJSONObject(0).getString("release_date");
                    String title = nowMovie;
                    String message ="Today "+ nowMovie + " Release "+releaseMovie ;
                    int notifId = 1;
                    showNotification(getApplicationContext(), title, message, notifId);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("GetMovie", "Failed");
            }
        });
    }
    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
//        SchedulerTask mSchedulerTask = new SchedulerTask(this);
//        mSchedulerTask.createPeriodicTask();
    }
    private void showNotification(Context context, String title, String message, int notifId){
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.dico)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.white))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManagerCompat.notify(notifId, builder.build());
    }
}
