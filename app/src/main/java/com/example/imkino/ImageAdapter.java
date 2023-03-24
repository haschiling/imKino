package com.example.imkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Upload> {
    private Context context;
    private List<Upload> uploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        super(context, 0, uploads);
        this.context = context;
        this.uploads = uploads;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        }

        Upload uploadCurrent = uploads.get(position);
        TextView txtName = listItemView.findViewById(R.id.textViewNameItem);
        TextView txtGenre = listItemView.findViewById(R.id.textViewGenreItem);
        TextView txtYear = listItemView.findViewById(R.id.textViewYearItem);
        //TextView txtLimit = listItemView.findViewById(R.id.textViewLimitItem);
        //TextView txtAbout = listItemView.findViewById(R.id.textViewAboutItem);
        ImageView image = listItemView.findViewById(R.id.imageItem);

        txtName.setText(uploadCurrent.getName());
        txtGenre.setText(uploadCurrent.getGenre());
        txtYear.setText(uploadCurrent.getYear());
        //txtLimit.setText(uploadCurrent.getLimit());
        //txtAbout.setText(uploadCurrent.getAbout());

        Picasso.get().load(uploadCurrent.getImageUri()).fit().centerCrop().into(image);

        return listItemView;
    }
}
