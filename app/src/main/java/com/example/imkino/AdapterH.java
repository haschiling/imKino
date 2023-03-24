package com.example.imkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdapterH extends ArrayAdapter<Item> {
    Context context;
    Item[] arr;


    public AdapterH(Context context, Item[] arr){
        super(context,R.layout.activity_item,arr);
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate(R.layout.activity_item,parent,false);
        TextView name = (TextView) view.findViewById(R.id.textViewNameItem);
        name.setText(arr[position].getName());
        TextView genre = (TextView) view.findViewById(R.id.textViewGenreItem);
        genre.setText(arr[position].getGenre());
        TextView year = (TextView) view.findViewById(R.id.textViewYearItem);
        year.setText(arr[position].getYear());
        ImageView image = (ImageView) view.findViewById(R.id.imageItem);
        image.setImageResource(R.drawable.ic_launcher_background);
        return view;
    }
}
