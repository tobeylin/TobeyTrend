package com.trend.tobeylin.tobeytrend.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.R;

import java.util.List;

/**
 * Created by tobeylin on 15/6/25.
 */
public class CountrySpinnerAdapter extends BaseAdapter {

    List<String> countries;

    public CountrySpinnerAdapter(List<String> countries){
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_spinner_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.countryNameTextView.setText(countries.get(position));

        return convertView;
    }

    private class ViewHolder {

        public TextView countryNameTextView;

        public ViewHolder(View view) {

            countryNameTextView = (TextView) view.findViewById(R.id.countrySpinnerItem_countryNameTextView);

        }

    }

}
