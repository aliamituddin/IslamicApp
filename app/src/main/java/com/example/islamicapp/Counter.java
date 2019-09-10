package com.example.islamicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islamicapp.Models.WazifaModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.Objects;

public class Counter extends AppCompatActivity {
    TextView tvCounter , tvAayat;
    Button btnCounter;
    FirebaseAuth mAuth;
    DatabaseReference   mDatabaseReference;
    String UID;
    int count;
    String counted;
    int days;
    int limit;
   final  String key="key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailyy);
        tvCounter =findViewById(R.id.tvCounter);
        btnCounter = findViewById(R.id.btnCounter);
        mAuth = FirebaseAuth.getInstance();
        tvAayat = findViewById(R.id.wazifa);
        UID = mAuth.getCurrentUser().getUid();



        final String id = getIntent().getExtras().getString("id");
        final String num = getIntent().getExtras().getString("num");
        final String type = getIntent().getExtras().getString("type");
        final String aayat = getIntent().getExtras().getString("ayat");
        final  String trans = getIntent().getExtras().getString("trans");
        final String purpose = getIntent().getExtras().getString("purpose");

        tvAayat.setText(aayat);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Wazaif")){
                    mDatabaseReference.child(UID).child("Wazaif").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(id)){
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Days").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        days = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue(String.class)));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        count = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue(String.class)));
                                        tvCounter.setText(String.valueOf(count));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        count = 0;
                                        mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue(String.valueOf(count));
                                    }
                                });

                            }
                            else {
                                mDatabaseReference.child(UID).child("Wazaif").child(id);
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Days").setValue("0");
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue("0");
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Status").setValue("pending");
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("WazifaID").setValue(id);
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Wazifa").setValue(aayat);
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Translation").setValue(trans);
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("WazifaID").setValue(id);
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Type").setValue(type);
                                mDatabaseReference.child(UID).child("Wazaif").child(id).child("Purpose").setValue(purpose);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }else {
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Days").setValue("0");
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue("0");
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Status").setValue("pending");
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("WazifaID").setValue(id);
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Wazifa").setValue(aayat);
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Translation").setValue(trans);
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("WazifaID").setValue(id);
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Type").setValue(type);
                    mDatabaseReference.child(UID).child("Wazaif").child(id).child("Purpose").setValue(purpose);


                    days = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        btnCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;

                if (type.equals("Daily"))
                {
                   if (count<Integer.parseInt(num)){
                       String s = String.valueOf(count);
                       tvCounter.setText(s);

                       mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue(s);
                   }
                   else {
                       finish();

                       days++;
                       if (days<2) {
                           mDatabaseReference.child(UID).child("Wazaif").child(id).child("Status").setValue("completed");
                       }

                   }

                }

                if (type.equals("Monthly")){

                    if (count<Integer.parseInt(num)){
                        String s = String.valueOf(count);
                        tvCounter.setText(s);

                        mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue(s);
                    }
                    else {
                        finish();
                        days++;

                        if (days<30) {

                            mDatabaseReference.child(UID).child("Wazaif").child(id).child("Days").setValue(String.valueOf(days));
                            mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue("0");
                        }
                        else{
                            mDatabaseReference.child(UID).child("Wazaif").child(id).child("Status").setValue("completed");


                        }

                    }
                }

                //for yearly wazaif
                if (type.equals("Yearly")){

                    if (count<Integer.parseInt(num)){
                        String s = String.valueOf(count);
                        tvCounter.setText(s);

                        mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue(s);
                    }
                    else {
                        finish();
                        days++;
                        if (days<366) {

                            mDatabaseReference.child(UID).child("Wazaif").child(id).child("Days").setValue(String.valueOf(days));
                            mDatabaseReference.child(UID).child("Wazaif").child(id).child("Counted").setValue("0");
                        }
                        else{
                            mDatabaseReference.child(UID).child("Wazaif").child(id).child("Status").setValue("completed");

                        }

                    }
                }


            }
        });
            counted = String.valueOf(count);
        if (savedInstanceState!=null){
            String counted = savedInstanceState.getString(key);
            tvCounter.setText(counted);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(key,tvCounter.getText().toString());

        super.onSaveInstanceState(outState);

    }

    public void save(View view){

    }



}
