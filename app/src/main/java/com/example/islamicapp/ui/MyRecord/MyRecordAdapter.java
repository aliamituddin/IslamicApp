package com.example.islamicapp.ui.MyRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.Models.WazifaModel;
import com.example.islamicapp.R;
import com.example.islamicapp.RecordModel;

import java.util.ArrayList;

public class MyRecordAdapter extends RecyclerView.Adapter<MyRecordAdapter.ViewHolder> {
    private ArrayList<RecordModel> recordList;
    private Context mContext;

    public  MyRecordAdapter( Context context,ArrayList<RecordModel> recordList) {
        this.recordList = recordList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View mView = mLayoutInflater.inflate(R.layout.record_cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecordModel model = recordList.get(position);
        holder.tvPurpose.setText(model.getPurpose());
        holder.tvWazifa.setText(model.getWazifa());
        holder.tvTrans.setText(model.getTranslation());
        holder.tvType.setText(model.getType());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPurpose,tvWazifa,tvTrans,tvType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPurpose = itemView.findViewById(R.id.purpose);
            tvWazifa = itemView.findViewById(R.id.aayat);
            tvTrans = itemView.findViewById(R.id.translation);
            tvType = itemView.findViewById(R.id.type);


        }
    }
}
