package com.example.imkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<Upload> {
    private List<Upload> uploadsList;
    private List<Upload> filteredUploadsList;
    private Filter filter;
    private Context context;
    private List<Upload> uploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        super(context, 0, uploads);
        this.context = context;
        this.uploads = uploads;
        this.filteredUploadsList = uploads;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<Upload> filteredList = new ArrayList<>();

                    if (constraint == null || constraint.length() == 0) {
                        filteredList.addAll(uploadsList);
                    } else {
                        String filterPattern = constraint.toString().toLowerCase().trim();
                        for (Upload upload : uploadsList) {
                            if (upload.getName().toLowerCase().contains(filterPattern)) {
                                filteredList.add(upload);
                            }
                        }
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredUploadsList = (List<Upload>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        return filter;
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


