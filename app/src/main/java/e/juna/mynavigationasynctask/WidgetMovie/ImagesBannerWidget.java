package e.juna.mynavigationasynctask.WidgetMovie;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 3/4/2018.
 */

public class ImagesBannerWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.dicoding.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.dicoding.EXTRA_ITEM";

    /*
    Update widget berdasarkan id widget-nya di home screen
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);

        views.setRemoteAdapter(R.id.stack_view, intent);

        views.setEmptyView(R.id.stack_view, R.id.empty_view);


        Intent toastIntent = new Intent(context, ImagesBannerWidget.class);

        toastIntent.setAction(ImagesBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /*
    Update widget
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            // Get all ids
            ComponentName thisWidget = new ComponentName(context, StackRemoteViewsFactory.class);
            int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

            // Build the intent to call the service
            Intent intent = new Intent(context.getApplicationContext(), StackRemoteViewsFactory.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            // Update the widgets via the service
            context.startService(intent);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /*
    Gunakan onReceive untuk menerima broadcast
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);


        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }


        super.onReceive(context, intent);
    }
}
