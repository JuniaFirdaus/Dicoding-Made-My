package e.juna.mynavigationasynctask.GakDipakeMovieFavoriteSQLITE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 2/13/2018.
 */

public class CustomeAdapterAsync extends BaseAdapter {

    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public CustomeAdapterAsync(Context context, ArrayList<MovieItems> mData) {
        this.mData=mData;
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
        CustomeAdapterAsync.ViewHolder holder = null;
        MovieItems movieItems = getItem(position);


        if (convertView == null) {
            holder = new CustomeAdapterAsync.ViewHolder();
            convertView = mInflater.inflate(R.layout.list_movie, null);
            holder.textViewJudulMovie= convertView.findViewById(R.id.texttitle);
            holder.textViewReleaseDate = convertView.findViewById(R.id.textRelease);
            holder.textViewOverView = convertView.findViewById(R.id.textOverview);
            holder.img= convertView.findViewById(R.id.imgFavo);
            convertView.setTag(holder);
        } else {
            holder = (CustomeAdapterAsync.ViewHolder) convertView.getTag();
        }
        holder.textViewJudulMovie.setText(mData.get(position).getJudul());
        holder.textViewReleaseDate.setText(mData.get(position).getReleaseDate());
        holder.textViewOverView.setText(mData.get(position).getOverview());
        Picasso.with(context).load(movieItems.getImg()).into(holder.img);

        return convertView;


    }

    private static class ViewHolder {
        TextView textViewJudulMovie;
        TextView textViewReleaseDate;
        TextView textViewOverView;
        ImageView img;
    }

}