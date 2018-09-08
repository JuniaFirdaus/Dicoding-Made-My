package e.juna.dicodingmovieapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import e.juna.dicodingmovieapp.R;

import static e.juna.dicodingmovieapp.DatabaseContract.NoteColumns.OVERVIEW;
import static e.juna.dicodingmovieapp.DatabaseContract.NoteColumns.RELEASEDATE;
import static e.juna.dicodingmovieapp.DatabaseContract.NoteColumns.TITLE;
import static e.juna.dicodingmovieapp.DatabaseContract.getColumnString;


public class DicodingMovieAdapter extends CursorAdapter {

    public DicodingMovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_dicoding_movie, viewGroup, false);
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvTitle = view.findViewById(R.id.tv_item_title);
            TextView tvDate = view.findViewById(R.id.tv_item_date);
            TextView tvDescription = view.findViewById(R.id.tv_item_description);

            tvTitle.setText(getColumnString(cursor,TITLE));
            tvDescription.setText(getColumnString(cursor,OVERVIEW));
            tvDate.setText(getColumnString(cursor,RELEASEDATE));
        }
    }
}