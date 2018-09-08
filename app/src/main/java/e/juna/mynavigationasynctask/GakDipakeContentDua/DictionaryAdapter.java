package e.juna.mynavigationasynctask.GakDipakeContentDua;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 2/15/2018.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryViewHolder>{
    private Context context;
    private List<String> wordList;
    public DictionaryAdapter(Context context, ArrayList<String> wordList) {
        this.context = context;
        this.wordList = wordList;
    }
    @Override
    public DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);

        return new DictionaryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DictionaryViewHolder holder, int position) {
        String rowContent = wordList.get(position);
        holder.judul.setText(rowContent);
//        holder.release.setText(rowContent);
//        holder.overview.setText(rowContent);
    }
    @Override
    public int getItemCount() {
        return wordList.size();
    }
}
