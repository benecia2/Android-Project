package com.example.blisgoproject.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blisgoproject.R;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] items;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item_layout, parent, false);

        TextView textView = row.findViewById(R.id.text_category);
        ImageView imageView = row.findViewById(R.id.check_image);

        textView.setText(items[position]);
        if (position == 0) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        return row;
    }
}
