package com.example.islamicapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.Models.WazifaModel;

import java.util.ArrayList;

public class DailyWazaifAdapter extends RecyclerView.Adapter<DailyWazaifAdapter.ViewHolder>  implements  View.OnClickListener{
private Context mContext;
private ArrayList<WazifaModel> wazaifList;
private itemClickListener itemClickListener;
private FragmentManager mFragmentManager;


    public DailyWazaifAdapter(Context context, ArrayList<WazifaModel> wazaifList) {
        mContext = context;
        this.wazaifList = wazaifList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View mView = mLayoutInflater.inflate(R.layout.aayat_cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }
    @Override
    public void onClick(View view) {


    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final WazifaModel model =wazaifList.get(position);
    holder.tvPurpose.setText(model.getPurpose());
    holder.tvAayat.setText(model.getWazifa());
    holder.tvTranslation.setText(model.getTranslation());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent intent = new Intent().setClass(view.getContext(), Counter.class);

            intent.putExtra("id",model.getId());
            intent.putExtra("ayat",model.getWazifa());
            intent.putExtra("num",model.getNum());
            intent.putExtra("type",model.getType());
            intent.putExtra("trans",model.getTranslation());
            intent.putExtra("purpose",model.getPurpose());

            view.getContext().startActivity(intent);

        }
    });


    }

    @Override
    public int getItemCount() {
        return wazaifList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPurpose,tvAayat,tvTranslation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                tvPurpose = itemView.findViewById(R.id.purpose);
                tvAayat = itemView.findViewById(R.id.aayat);
                tvTranslation = itemView.findViewById(R.id.translation);


        }
    }
    public void setitemClickListener(itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
