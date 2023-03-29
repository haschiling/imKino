package com.example.imkino;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FilmListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Item> list;
    private ArrayList<Item> items;
    private ArrayList<Item> filteredList;
    // private ItemFilter filter = new ItemFilter();


    public FilmListAdapter(Context context, int layout, List<Item> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.filteredList = new ArrayList<>(list);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName;
        TextView txtGenre;
        TextView txtYear;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtName = (TextView) row.findViewById(R.id.textViewNameItem);
            holder.txtGenre = (TextView) row.findViewById(R.id.textViewGenreItem);
            holder.txtYear = (TextView) row.findViewById(R.id.textViewYearItem);
            holder.imageView = (ImageView) row.findViewById(R.id.imageItem);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }
        Item film = list.get(position);
        holder.txtName.setText(film.getName());
        holder.txtGenre.setText(film.getGenre());
        holder.txtYear.setText(film.getYear());
        byte[] filmImage = film.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(filmImage,0,filmImage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;



    }




}
