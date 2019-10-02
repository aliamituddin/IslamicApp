package com.example.islamicapp.ui.MyRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.DailyWazaifAdapter;
import com.example.islamicapp.Models.WazifaModel;
import com.example.islamicapp.R;
import com.example.islamicapp.RecordModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRecord extends Fragment {
    RecyclerView    recordRV;
    ArrayList<RecordModel> recordList;
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    String uid;
    MyRecordAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_share, container, false);
        recordRV = root.findViewById(R.id.recordRV);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mReference = FirebaseDatabase.getInstance().getReference("Users");

        getRecords();


        return root;
    }

    private void getRecords() {
        recordList = new ArrayList<>();
        recordRV.setLayoutManager( new LinearLayoutManager(getActivity()));
        Query query = mReference.child(uid).child("Record").orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    RecordModel model = dataSnapshot1.getValue(RecordModel.class);
                    recordList.add(model);
                }
            mAdapter = new MyRecordAdapter(getActivity(),recordList);
                recordRV.setAdapter(mAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}