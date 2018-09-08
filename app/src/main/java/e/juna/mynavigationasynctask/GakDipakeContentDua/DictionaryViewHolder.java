package e.juna.mynavigationasynctask.GakDipakeContentDua;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 2/15/2018.
 */

public class DictionaryViewHolder extends RecyclerView.ViewHolder{
    public TextView judul, release, overview;
    public ImageView imageView;
    public DictionaryViewHolder(View itemView) {
        super(itemView);
        judul = (TextView)itemView.findViewById(R.id.texttitle);
        release = (TextView)itemView.findViewById(R.id.textRelease);
        overview = (TextView)itemView.findViewById(R.id.textOverview);
        imageView = (ImageView)itemView.findViewById(R.id.imgFavo);
    }
}
