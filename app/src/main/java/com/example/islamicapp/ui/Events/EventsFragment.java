package com.example.islamicapp.ui.Events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.CreateEvent;
import com.example.islamicapp.DailyWazaifAdapter;
import com.example.islamicapp.Models.EventModel;
import com.example.islamicapp.Models.WazifaModel;
import com.example.islamicapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsFragment extends Fragment {


    ArrayList<EventModel> eventsList;
    DatabaseReference mDatabaseReference;
    EventFragmentAdapter mAdapter;
    FirebaseAuth mAuth;
    String uid,sect;

    Button btnCreateEvent;
    RecyclerView eventsRV;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        btnCreateEvent = root.findViewById(R.id.btnCreateEvent);
        eventsRV = root.findViewById(R.id.eventsRV);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateEvent CE = new CreateEvent();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,CE,getTag()).addToBackStack(null)
                        .commit();
            }
        });
        getsect();
        getEvents();

        return root;
    }

    private void getsect() {
        uid = mAuth.getCurrentUser().getUid();
        mDatabaseReference.child("Users").child(uid).child("sect").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sect = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getEvents() {


        eventsList = new ArrayList<>();
        eventsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabaseReference.child("Users").child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    EventModel eventModel = dataSnapshot1.getValue(EventModel.class);

                    if (sect.equals("All")){
                        eventsList.add(eventModel);
                    }
                    else {
                        if (eventModel.getSect().equals(sect)) {
                            eventsList.add(eventModel);

                        }
                    }


                }
                mAdapter = new EventFragmentAdapter(getActivity(),eventsList);
                eventsRV.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}