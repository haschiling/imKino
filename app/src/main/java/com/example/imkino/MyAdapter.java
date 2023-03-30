package com.example.imkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Filterable {
    private ArrayList<String> originalData;
    private ArrayList<String> filteredData;

    // Constructor for MyAdapter
    public MyAdapter(Context context, ArrayList<String> data) {
        this.originalData = data;
        this.filteredData = data;
    }

    // Implement other methods of BaseAdapter here...

    @Override
    public Filter getFilter() {
        return new MyFilter();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<String> filteredList = new ArrayList<>();

            // Check if the constraint is null or empty
            if (constraint == null || constraint.length() == 0) {
                // Set the original data to be displayed
                results.count = originalData.size();
                results.values = originalData;
            } else {
                // Filter the data based on the constraint
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String item : originalData) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
