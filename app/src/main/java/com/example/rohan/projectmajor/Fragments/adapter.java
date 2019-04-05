package com.example.rohan.projectmajor.Fragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rohan.projectmajor.R;

public class adapter extends ArrayAdapter<String> {
    Activity act;
    String[] topic, time;
    public adapter(Activity a, String[] sub, String[] t) {
        super(a, R.layout.listview, sub);
        act=a;
        topic=sub;
        time=t;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li=act.getLayoutInflater();
        View v=li.inflate(R.layout.listview, null, true);
        TextView t1=v.findViewById(R.id.textView1),

                t4=v.findViewById(R.id.textView4);
        t1.setText(topic[position]);
        t4.setText(time[position]);


        return v;
    }
}
