package e.juna.mynavigationasynctask.WidgetMovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import e.juna.mynavigationasynctask.MovieFavoriteContentProvider.db.DatabaseContract;
import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 3/4/2018.
 */

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context context;
    private Cursor cursor;
    private Intent intent;

    //For obtaining the activity's context and intent
    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initCursor(){
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(DatabaseContract.CONTENT_URI,
                new String[]{DatabaseContract.NoteColumns._ID, DatabaseContract.NoteColumns.TITLE, DatabaseContract.NoteColumns.RELEASEDATE,
                        DatabaseContract.NoteColumns.OVERVIEW, DatabaseContract.NoteColumns.IMAGE},
                DatabaseContract.NoteColumns._ID + " = ?",
                new String[]{"1"},null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        initCursor();
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        cursor.moveToPosition(i);
        remoteViews.setTextViewText(R.id.widget_date,cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteColumns.TITLE)));
//        remoteViews.setTextViewText(R.id.coba_layout_title,cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteColumns.RELEASEDATE)));
//        remoteViews.setTextViewText(R.id.coba_layout_department,cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteColumns.IMAGE)));

        Bitmap bmp = null;
        try {

            bmp = Glide.with(context)
                    .load(cursor.getString(cursor.getColumnIndex(DatabaseContract.NoteColumns.IMAGE)))
                    .asBitmap()
                    .error(new ColorDrawable(context.getResources().getColor(R.color.colorSubTitle)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        remoteViews.setImageViewBitmap(R.id.imageView,bmp);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
