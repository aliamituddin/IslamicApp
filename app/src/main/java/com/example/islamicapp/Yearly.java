package com.example.islamicapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicapp.Models.WazifaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Yearly extends Fragment {
    private RecyclerView YearlyRV;
    ArrayList<WazifaModel> wazaifList;
    DatabaseReference mDatabaseReference;
    DailyWazaifAdapter mAdapter;

    public Yearly() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_yearly, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Data");
        YearlyRV = root.findViewById(R.id.YearlyRV);

        getWazaif();

        return root;
    }
    private void getWazaif() {
        wazaifList = new ArrayList<>();
        YearlyRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = mDatabaseReference.orderByChild("Type").equalTo("Yearly");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    WazifaModel model =dataSnapshot1.getValue(WazifaModel.class);
                    wazaifList.add(model);

                }
                mAdapter = new DailyWazaifAdapter(getActivity(),wazaifList);
                YearlyRV.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
