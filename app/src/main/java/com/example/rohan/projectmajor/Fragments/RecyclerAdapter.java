package com.example.rohan.projectmajor.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rohan.projectmajor.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    List<Routine> routines;
    Context context;

    public RecyclerAdapter(List<Routine> routines, Context context)
    {
        this.routines=routines;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview, parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyHolder holder, int position) {

        Routine routine=routines.get(position);

        holder.topic.setText(routine.getTopic());
        holder.timing.setText(routine.getTiming());
    }

    @Override
    public int getItemCount() {
        int arr=0;
        try {
            if(routines.size()==0)
            {
                arr=0;
            }else
            {
                arr=routines.size();
            }
        }catch (Exception e){

        }
        return arr;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView topic,timing;
        public MyHolder(View itemView) {
            super(itemView);
            topic=itemView.findViewById(R.id.textView1);
            timing=itemView.findViewById(R.id.textView4);
        }
    }
}
