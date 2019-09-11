package com.example.islamicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Invitation extends AppCompatActivity {
    String title,city,sect,address,desc,time,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        title = getIntent().getStringExtra("title");
        city = getIntent().getStringExtra("city");
        sect = getIntent().getStringExtra("sect");
        address = getIntent().getStringExtra("address");
        desc = getIntent().getStringExtra("description");
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");





    }
    public void btnwtsapp(View view){
        Intent sendIntent = new Intent();
               sendIntent.setAction(Intent.ACTION_SEND);
               sendIntent.putExtra(Intent.EXTRA_TEXT,  "Title : "+title+System.lineSeparator()+"Description :"+desc
                       +System.lineSeparator()+ "Date :"+ date+System.lineSeparator()+"Time :"+time
                       +System.lineSeparator()+"City : "+city +System.lineSeparator() +"Address : "+address);
               sendIntent.setType("text/plain");
               startActivity(sendIntent);
    }
    public void btnfbm(View view){


            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,   "Title : "+title+System.lineSeparator()+"Description :"+desc
                    +System.lineSeparator()+ "Date :"+ date+System.lineSeparator()+"Time :"+time
                    +System.lineSeparator()+"City : "+city +System.lineSeparator() +"Address : "+address);
            intent.setType("text/plain");
            intent.setPackage("com.facebook.orca");
            startActivity(intent);

    }
}
