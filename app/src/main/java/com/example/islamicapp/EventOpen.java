package com.example.islamicapp;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islamicapp.Models.EventModel;
import com.example.islamicapp.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventOpen extends AppCompatActivity {
    TextView titleTv,sectTv,cityTv,timeTv,dateTv,descriptionTv,addressTv,peoplesTv;
    String title,city,sect,address,desc,time,date,eID,peoples;
    ArrayList<String> peoplesList;
    FirebaseAuth mAuth;
    DatabaseReference mReference;

    boolean cancel;

    Button btnInterested,btnInvite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_open);
        titleTv  = findViewById(R.id.tite);
        sectTv = findViewById(R.id.sect);
        cityTv = findViewById(R.id.city);
        timeTv = findViewById(R.id.time);
        dateTv = findViewById(R.id.date);
        peoplesTv = findViewById(R.id.tvInterested);
        descriptionTv = findViewById(R.id.desc);
        descriptionTv.setMovementMethod(new ScrollingMovementMethod());
        addressTv = findViewById(R.id.address);
        addressTv.setMovementMethod(new ScrollingMovementMethod());
        btnInterested = findViewById(R.id.btnInterested);
        btnInvite = findViewById(R.id.btnInvite);
        btnInvite.setVisibility(View.GONE);
        title = getIntent().getStringExtra("title");
        city = getIntent().getStringExtra("city");
        sect = getIntent().getStringExtra("sect");
        address = getIntent().getStringExtra("address");
        desc = getIntent().getStringExtra("description");
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");
        eID = getIntent().getStringExtra("eID");
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        cancel = false;
        titleTv.setText(title);
        sectTv.setText(sect);
        cityTv.setText(city);
        timeTv.setText(time);
        dateTv.setText(date);
        descriptionTv.setText(desc);
        addressTv.setText(address);

        final String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

       mReference.child("Users").child("Events").child(eID).child("oid").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String OID = dataSnapshot.getValue(String.class);
            if (OID.equals(uid)){
                btnInterested.setVisibility(View.GONE);
                btnInvite.setVisibility(View.VISIBLE);

            }




           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



        mReference.child("Users").child("Events").child(eID).child("InterestedPeoples").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                ArrayList<String> list = new ArrayList<>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    String s = childDataSnapshot.getValue(String.class);

                    list.add(s);

                }
                String node = String.valueOf(list.size());

               peoplesTv.setText(node);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EventOpen.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void btnInterested(View view){
        final String uid = mAuth.getCurrentUser().getUid();

        mReference.child("Users").child("Events").child(eID).child("InterestedPeoples").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            ArrayList<String> list = new ArrayList<>();
           for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

               String s = childDataSnapshot.getValue(String.class);
               if (s.equals(uid)){
                   cancel = true;
               }

               list.add(s);

           }
           String node = String.valueOf(list.size());

           if (cancel)
           {
               Toast.makeText(EventOpen.this, "Already Added", Toast.LENGTH_SHORT).show();
           }else {
               mReference.child("Users").child("Events").child(eID).child("InterestedPeoples").child(node).setValue(uid);

           }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EventOpen.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void btnInvite(View view){

        if (!title.isEmpty()&!city.isEmpty()&!time.isEmpty()&!date.isEmpty()&!desc.isEmpty()&!address.isEmpty()){

            Intent intent = new Intent(this,Invitation.class);

            intent.putExtra("title",title);
            intent.putExtra("city",city);
            intent.putExtra("sect",sect);
            intent.putExtra("address",address);
            intent.putExtra("description",desc);
            intent.putExtra("time",time);
            intent.putExtra("date",date);
            startActivity(intent);

//            Intent sendIntent = new Intent();
//               sendIntent.setAction(Intent.ACTION_SEND);
//               sendIntent.putExtra(Intent.EXTRA_TEXT,  "Title : "+title+System.lineSeparator()+"Description :"+desc
//                       +System.lineSeparator()+ "Date :"+ date+System.lineSeparator()+"Time :"+time
//                       +System.lineSeparator()+"City : "+city +System.lineSeparator() +"Address : "+address);
//               sendIntent.setType("text/plain");
//               startActivity(sendIntent);


//            Intent intent= new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, "Hello");
//            intent.setType("text/plain");
//            intent.setPackage("com.facebook.orca");
//            startActivity(intent);

        }
    }

}
