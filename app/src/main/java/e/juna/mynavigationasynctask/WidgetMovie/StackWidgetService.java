package e.juna.mynavigationasynctask.WidgetMovie;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Juna on 3/4/2018.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}