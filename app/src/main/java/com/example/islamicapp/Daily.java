package com.example.islamicapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
public class    Daily extends Fragment {

    private RecyclerView dailyRv;
ArrayList<WazifaModel> wazaifList;
DatabaseReference mDatabaseReference;
DailyWazaifAdapter mAdapter;



    public Daily() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_daily, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Data");
        dailyRv = root.findViewById(R.id.dailyRV);

        getWazaif();

//        LinearLayout aaya1 = root.findViewById(R.id.aya1);
//        aaya1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Counter Now", Toast.LENGTH_SHORT).show();
//                Counter  counterF = new Counter();
//                FragmentManager manager = getFragmentManager();
//                manager.beginTransaction().replace(R.id.nav_host_fragment,counterF,getTag()).addToBackStack(null)
//                        .commit();
//            }
//        });

        return root;
    }

    private void getWazaif() {
        wazaifList = new ArrayList<>();
        dailyRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = mDatabaseReference.orderByChild("Type").equalTo("Daily");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    WazifaModel model =dataSnapshot1.getValue(WazifaModel.class);
                    wazaifList.add(model);

                }
                mAdapter = new DailyWazaifAdapter(getActivity(),wazaifList);
                dailyRv.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
