package com.example.josue.carpool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josue on 10/8/2016.
 */

public class SpinnerAdapter extends ArrayAdapter<School> {
    Context context;
    private ArrayList<School> schools = new ArrayList<>();
    TextView SchoolName;

    public SpinnerAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return this.schools.size();
    }

    @Nullable
    @Override
    public School getItem(int position) {
        return this.schools.get(position);
    }

    @Override
    public void add(School object) {
        this.schools.add(object);
        super.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.source_spinner_school, parent, false);
        }


        SchoolName = (TextView) convertView.findViewById(R.id.school_name);

        School selectedSchool = getItem(position);

        Log.e("d", ""  + selectedSchool.getName());
        SchoolName.setText(String.valueOf(selectedSchool.getName()));

        return convertView;
    }
}
