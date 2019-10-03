package com.example.islamicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;

public class Calling extends AppCompatActivity implements Connector.IConnect {
    private Connector vc;
    private FrameLayout videoFrame;
    String room="Hello";
    RelativeLayout activity_calling;
    Button start,connect,disconect;
    DatabaseReference mref;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        mref= FirebaseDatabase.getInstance().getReference();
        getToken();
        start=findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                start.setClickable(false);
            }
        });
        connect=findViewById(R.id.button2);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect();
            }
        });
        disconect=findViewById(R.id.button3);
        disconect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();

            }
        });

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        activity_calling=findViewById(R.id.activity_calling);
        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);


    }

    public void Start() {
        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 2, "warning info@VidyoClient info@VidyoConnector", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
    }

    public void Connect() {
//        String token = "cHJvdmlzaW9uAHVzZXIxQGIxNGExYy52aWR5by5pbwA2MzcyNjI3NTA3MAAAM2M3MTIyNTg3ZTEwZmIyMzBjMmRhYmMzZDAzYTBkN2NlOTMyZGNkYTUyNjhiMjUxMThlZWUwNzMxMmRjM2JiMTcwM2QxMTZjMTFlYzA0YWY1MDY2OTJkOGU1Mjc2MTE0";
        vc.connect("prod.vidyo.io", token, "DemoUser", room, this);
    }

    public void Disconnect() {
        vc.disconnect();
        finish();
        Intent intent=new Intent(this,OnlineCounselling.class);
        startActivity(intent);
    };
    public void rotate(View view){
        vc.cycleCamera();
    }
    @Override
    public void onSuccess() {

    };

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
    };

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {


    };
    public void getToken(){
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    token=dataSnapshot.child("token").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    public void checkforPermission(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show();
//            // Permission is not granted
//        }
//        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Microphone permission not granted", Toast.LENGTH_SHORT).show();
//            // Permission is not granted
//        }
//        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS)
//                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Speaker permission not granted", Toast.LENGTH_SHORT).show();
//            // Permission is not granted
//
//        }
//
//        else {
//            Intent intent = new Intent(Appoinments_details.this, Calling.class);
//            startActivity(intent);
//        }
//
//    }
};
