package com.example.islamicapp;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.islamicapp.Models.EventModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEvent extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    EditText title,description,address;
    TextView date,time;
    Spinner sect,city;
    String CUID,sTitle,sDesc,sAddress,sDate,sTime,sSect,sCity,eventID;
    Button btnPostEvent;
    FirebaseAuth auth;
    DatabaseReference refrence;
    FirebaseDatabase database;




    public CreateEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=  inflater.inflate(R.layout.fragment_create_event, container, false);
        title = root.findViewById(R.id.EventTitle);
        description = root.findViewById(R.id.eventDes);
        address = root.findViewById(R.id.eventAddress);
        btnPostEvent = root.findViewById(R.id.btnPostEvent);
        date = root.findViewById(R.id.datePicker);
        time = root.findViewById(R.id.timePicker);
        sect = root.findViewById(R.id.sectSpinner);
        city = root.findViewById(R.id.citySpinner);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refrence = database.getReference("Users");
        CUID = auth.getCurrentUser().getUid();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new DatePickerFragment(); // creating DialogFragment which creates DatePickerDialog
                newFragment.setTargetFragment(CreateEvent.this,0);  // Passing this fragment DatePickerFragment.
                // As i figured out this is the best way to keep the reference to calling activity when using FRAGMENT.
                newFragment.show(getFragmentManager(),"datePicker");

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new com.example.islamicapp.TimePickerDialog(); // creating DialogFragment which creates DatePickerDialog
                newFragment.setTargetFragment(CreateEvent.this,0);  // Passing this fragment DatePickerFragment.
                // As i figured out this is the best way to keep the reference to calling activity when using FRAGMENT.
                newFragment.show(getFragmentManager(),"timePicker");

            }
        });
        btnPostEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setError(null);
                description.setError(null);
                address.setError(null);
                date.setError(null);
                time.setError(null);
                View focusView  = null;
                boolean cancel  = false;


                sTitle = title.getText().toString();
                sDesc = description.getText().toString();
                sAddress = address.getText().toString();
                sDate = date.getText().toString();
                sTime = time.getText().toString();
                sSect = sect.getSelectedItem().toString();
                sCity = city.getSelectedItem().toString();

                if (sTitle.isEmpty()){
                    title.setError("Enter Title");
                    cancel = true;
                    focusView = title;
                }
                if (sAddress.isEmpty()){
                    address.setError("Enter Address");
                    cancel = true;
                    focusView = address;

                }
                if (sDate.isEmpty()){
                    date.setError("Select Date");
                    cancel = true;
                    focusView = date;
                }
                if (sTitle.isEmpty()){
                    time.setError("Select Time");
                    cancel = true;
                    focusView = time;
                }
                    if (cancel){
                    focusView.requestFocus();

                }else if (!cancel){
                    CUID = auth.getCurrentUser().getUid();

                    String eID = refrence.child("Events").push().getKey();

                    EventModel event = new EventModel(sTitle,sDesc,sAddress,sDate,sTime,CUID,sSect,sCity,eID);
                    refrence.child("Events").child(eID).setValue(event);
                Toast.makeText(getActivity(), "EventPosted", Toast.LENGTH_SHORT).show();
//                        getActivity().getSupportFragmentManager().beginTransaction().remove(getTargetFragment()).commit();

                }

            }
        });


    return  root;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        StringBuilder sb = new StringBuilder().append(i2).append("/").append(i1+1).append("/").append(i);
        String formattedDate = sb.toString();
        date.setText(formattedDate);
        Toast.makeText(getContext(), ""+formattedDate, Toast.LENGTH_SHORT).show();





    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        StringBuilder tb = new StringBuilder().append(i).append(":").append(i1);
        String formattedTime = tb.toString();
        time.setText(formattedTime);
        Toast.makeText(getContext(), ""+formattedTime, Toast.LENGTH_SHORT).show();

    }

}
