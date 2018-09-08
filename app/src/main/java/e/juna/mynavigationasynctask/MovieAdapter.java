package e.juna.mynavigationasynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Emeth on 10/31/2016.
 */

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final MovieItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        // Pengecekan null diperlukan agar tidak terjadi force close ketika datanya null
        // return 0 sehingga adapter tidak akan menampilkan apapun
        if (mData == null)return 0;

        // Jika tidak null maka return banyaknya jumlah data yang ada
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MovieItems movieItems = getItem(position);


        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_movie, null);
            holder.textViewJudulMovie= (TextView)convertView.findViewById(R.id.texttitle);
            holder.textViewReleaseDate = (TextView)convertView.findViewById(R.id.textRelease);
            holder.textViewOverView = (TextView)convertView.findViewById(R.id.textOverview);
            holder.textViewOriginal_title = (TextView)convertView.findViewById(R.id.textOT);
            holder.textViewOriginal_language = (TextView)convertView.findViewById(R.id.textOL);
            holder.backdrop=(ImageView) convertView.findViewById(R.id.imgBackdrop);
            holder.img=(ImageView) convertView.findViewById(R.id.imgFavo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewJudulMovie.setText(mData.get(position).getJudul());
        holder.textViewReleaseDate.setText(mData.get(position).getReleaseDate());
        holder.textViewOverView.setText(mData.get(position).getOverview());
        holder.textViewOriginal_title.setText(mData.get(position).getOriginal_title());
        holder.textViewOriginal_language.setText(mData.get(position).getOriginal_language());
        Picasso.with(context).load(movieItems.getImg()).into(holder.backdrop);
        Picasso.with(context).load(movieItems.getImg()).into(holder.img);

        return convertView;


    }

    private static class ViewHolder {
        TextView textViewJudulMovie;
        TextView textViewReleaseDate;
        TextView textViewOverView;
        TextView textViewOriginal_title;
        TextView textViewOriginal_language;
        ImageView backdrop;
        ImageView img;
    }

}
