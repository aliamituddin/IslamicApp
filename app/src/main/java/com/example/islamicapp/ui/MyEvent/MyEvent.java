package com.example.islamicapp.ui.MyEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.Models.EventModel;
import com.example.islamicapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyEvent extends Fragment {


    ArrayList<EventModel> myEventsList;
    DatabaseReference mDatabaseReference;
    MyEventsAdapter mAdapter;
    FirebaseAuth mAuth;
    String CUID;
    RecyclerView MERV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        MERV = root.findViewById(R.id.myEventsRV);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Events");
        mAuth = FirebaseAuth.getInstance();

        getMyEvents();
        return root;
    }

    private void getMyEvents() {
        CUID = mAuth.getCurrentUser().getUid();
        myEventsList = new ArrayList<>();
        MERV.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = mDatabaseReference.orderByChild("oid").equalTo(CUID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    EventModel eventModel = dataSnapshot1.getValue(EventModel.class);
                    myEventsList.add(eventModel);
                }
                mAdapter = new MyEventsAdapter(getActivity(),myEventsList);
                MERV.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}