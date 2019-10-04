package com.example.islamicapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.ChatListAdapter;
import com.example.islamicapp.InstantMessage;
import com.example.islamicapp.Models.User;
import com.example.islamicapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineCounselling extends Fragment {

    private String mDisplayName, uid;
    private ListView mChatListVeiw;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private ImageButton callButton;
    private FirebaseAuth mAuth;
    private ChatListAdapter mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mFirebaseUser = mAuth.getCurrentUser();


        // Link the Views in the layout to the Java code
        mInputText = root.findViewById(R.id.messageInput);
        mSendButton = root.findViewById(R.id.sendButton);
        mChatListVeiw = root.findViewById(R.id.chat_list_view);
        callButton = root.findViewById(R.id.callButton);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkforPermission();
            }
        });


        getusername();


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mInputText.getText().toString().isEmpty()) {

                    String mMsg = mInputText.getText().toString();
                    sendMessage(mMsg, mDisplayName);
                }

            }

        });


        return root;
    }


    private void sendMessage(String displayName, String msg) {

        InstantMessage chat = new InstantMessage(displayName, msg);

        mDatabaseReference.child("Users").child("Counselling").push().setValue(chat);
        mInputText.setText("");


    }

    private void getusername() {
        mDatabaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDisplayName = dataSnapshot.child("name").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = dataSnapshot.child("name").getValue(String.class);

                mAdapter = new ChatListAdapter(getActivity(), mDatabaseReference, username, uid);
                mChatListVeiw.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
        public void checkforPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Camera permission not granted", Toast.LENGTH_SHORT).show();
            // Permission is not granted
        }
        else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Microphone permission not granted", Toast.LENGTH_SHORT).show();
            // Permission is not granted
        }
        else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.MODIFY_AUDIO_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Speaker permission not granted", Toast.LENGTH_SHORT).show();
            // Permission is not granted

        }

        else {
            Intent intent = new Intent(getActivity(), Calling.class);
            startActivity(intent);
        }

    }



}