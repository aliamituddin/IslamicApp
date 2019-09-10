package com.example.islamicapp.ui.Events;

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

public class EventFragmentAdapter extends RecyclerView.Adapter<EventFragmentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<EventModel> eventList;

    public EventFragmentAdapter(Context context, ArrayList<EventModel> eventList) {
        mContext = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View mView = mLayoutInflater.inflate(R.layout.event_cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EventModel event = eventList.get(position);

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
                intent.putExtra("peoples",event.getPeoplesList());
                view.getContext().startActivity(intent);





            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView    tvTitle,tvSect,tvCity,tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSect = itemView.findViewById(R.id.tvSect);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);

        }
    }
}
