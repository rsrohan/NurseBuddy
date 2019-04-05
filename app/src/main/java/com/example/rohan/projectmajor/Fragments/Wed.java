package com.example.rohan.projectmajor.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohan.projectmajor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Wed extends Fragment {



    RecyclerView recyclerView;
    DatabaseReference databaseReference2;
    FirebaseUser mUser;
    List<Routine> routineList = new ArrayList<Routine>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_wed, container, false);

        mUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference2= FirebaseDatabase.getInstance().getReference("UsersTimeTable").child("gj8YkpyEEhN3y80eVOu5cOfxc7q2");

        recyclerView=v.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...Make sure you have internet connection !!!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Calendar c=Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_WEEK);

        databaseReference2.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                routineList.clear();
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {
                    Routine routine= dataSnapshot1.getValue(Routine.class);
                    routineList.add(routine);
                }
                RecyclerAdapter recyclerAdapter=new RecyclerAdapter(routineList,getContext());
                recyclerView.setAdapter(recyclerAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

}
