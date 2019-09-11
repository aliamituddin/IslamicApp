package com.example.islamicapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList; //DataSnapshot is from firebase for passing our
    //.... data to app,App read data from cloud in form DataSnapshot
    //TODO: add a listener called ChildEventListen to get notify by change in database
    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot,  String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot,  String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot,  String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    //TODO: Add constructor to hold object of ChatListAdapter
    public ChatListAdapter(Activity activity,DatabaseReference ref,String name,String uid){
        mActivity = activity;
        mDisplayName = name;

        mDatabaseReference =    ref.child("Users").child("Counselling");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();

    }
    //ViewHolder class will hold all values in single row
    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params; //for designing  of message box

    }
    //using getcount();list view asks the adapter"How many items are in list to display
    @Override
    // returning size of array
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public InstantMessage getItem(int i) {
        DataSnapshot snapshot = mSnapshotList.get(i); //since i is position of item
        return snapshot.getValue(InstantMessage.class); // converts json from snapshot into instant
        //.... message object and getItem returning now InstantMessage object
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    //using getView call listView asks adpater for the data of first item at postion 0 means row 1
    //and so on for further rows
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //if there's existing row that can be reused
        if (view == null){
            //if now row than we have to create row from layout file
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_msg_row, viewGroup , false);
            // these things will help to makeup an individual chat message row
            final  ViewHolder holder = new ViewHolder();
            holder.authorName =  view.findViewById(R.id.author);
            holder.body = view.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams)holder.authorName.getLayoutParams();
            //storing viewholder in view for a short period so it can be used later , it will avoid calling
            // ...findViewById method again
            view.setTag(holder);

        }

        //TODO: check are we showing right author and message in listitem
        final InstantMessage message = getItem(i); // i is position and it was showing a error here
        // which could be handle by changing return type of this getitem() method at top of class
        //using get tag to retrieve view holder that we temporarily saved in view
        //setTag and getTag are used to reuse viewholder for each row in the list
        final ViewHolder holder = (ViewHolder) view.getTag();
        //uppper holder holds old data so we gonna change it to use again
        boolean isMe = message.getAuthor().equals(mDisplayName);
        setChatRowAppearance(isMe,holder);
        String author = message.getAuthor();
        holder.authorName.setText(author);
        String msg  = message.getMessage();
        holder.body.setText(msg);
        return view;
    }
    private void setChatRowAppearance(boolean isItMe,ViewHolder holder){
        if (isItMe){
            //own messages align to right
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.RED);
            holder.body.setBackgroundResource(R.drawable.bubble2);

        }else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }
        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }
    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }

}
