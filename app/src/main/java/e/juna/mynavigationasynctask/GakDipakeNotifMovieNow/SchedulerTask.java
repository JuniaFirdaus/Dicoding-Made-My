package e.juna.mynavigationasynctask.GakDipakeNotifMovieNow;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by Juna on 2/23/2018.
 */

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {

            Task periodicTask = new PeriodicTask.Builder()
                .setService(GetMovieGCM.class)
                .setPeriod(1000 * 60 * 60 * 24)
                .setFlex(1000 * 60 * 60 * 24)
                .setTag(GetMovieGCM.TAG_TASK_WEATHER_LOG)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }
    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(GetMovieGCM.TAG_TASK_WEATHER_LOG, GetMovieGCM.class);
        }
    }
}