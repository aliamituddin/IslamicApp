package com.example.islamicapp.ui.MyEvent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.EventOpen;
import com.example.islamicapp.Models.EventModel;
import com.example.islamicapp.R;

import java.util.ArrayList;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<EventModel> myEventsList;

    public MyEventsAdapter(Context context, ArrayList<EventModel> myEventsList) {
        mContext = context;
        this.myEventsList = myEventsList;
    }

    @NonNull
    @Override
    public MyEventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View mView = mLayoutInflater.inflate(R.layout.event_cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsAdapter.ViewHolder holder, int position) {
        final EventModel event = myEventsList.get(position);

        holder.tvTitle.setText(event.getTitle());
        holder.tvCity.setText(event.getCity());
        holder.tvSect.setText(event.getSect());
        holder.tvAddress.setText(event.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent().setClass(view.getContext(), EventOpen.class);


                intent.putExtra("title",event.getTitle());
                intent.putExtra("city",event.getCity());
                intent.putExtra("sect",event.getSect());
                intent.putExtra("address",event.getAddress());
                intent.putExtra("description",event.getDescription());
                intent.putExtra("time",event.getTime());
                intent.putExtra("date",event.getDate());
                intent.putExtra("eID",event.getEventId());

                view.getContext().startActivity(intent);





            }
        });
    }

    @Override
    public int getItemCount() {
        return myEventsList.size();
    }

    public class
    ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvSect,tvCity,tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSect = itemView.findViewById(R.id.tvSect);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);

        }
    }
}
