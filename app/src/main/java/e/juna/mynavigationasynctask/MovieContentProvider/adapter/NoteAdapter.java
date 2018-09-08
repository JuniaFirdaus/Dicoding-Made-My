package e.juna.mynavigationasynctask.MovieContentProvider.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import e.juna.mynavigationasynctask.MovieContentProvider.CustomOnItemClickListener;
import e.juna.mynavigationasynctask.MovieContentProvider.entity.Note;
import e.juna.mynavigationasynctask.R;

import static e.juna.mynavigationasynctask.MovieContentProvider.db.DatabaseContract.CONTENT_URI;


/**
 * Created by Juna on 2/17/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewholder>{
    private Cursor listNotes;
    private Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(Cursor listNotes) {
        this.listNotes = listNotes;
    }

    @Override
    public NoteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewholder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewholder holder, int position) {
        final Note note = getItem(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvDate.setText(note.getDate());
        holder.tvDescription.setText(note.getDescription());
        Picasso.with(activity).load(note.getImage()).into(holder.imgFavor);

        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
               // Intent intent = new Intent(activity, FormAddUpdateActivity.class);

                // Set intent dengan data uri row note by id
                // content://com.dicoding.mynotesapp/note/id
                Uri uri = Uri.parse(CONTENT_URI+"/"+note.getId());
                //intent.setData(uri);
                //activity.startActivityForResult(intent, FormAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (listNotes == null) return 0;
        return listNotes.getCount();
    }

    private Note getItem(int position){
        if (!listNotes.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Note(listNotes);
    }

    class NoteViewholder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvDescription, tvDate;
        CardView cvNote;
        ImageView imgFavor;

        NoteViewholder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_item_title);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_item_description);
            tvDate = (TextView)itemView.findViewById(R.id.tv_item_date);
            imgFavor = (ImageView)itemView.findViewById(R.id.imgFavorite);
            cvNote = (CardView)itemView.findViewById(R.id.cv_item_note);
        }
    }
}