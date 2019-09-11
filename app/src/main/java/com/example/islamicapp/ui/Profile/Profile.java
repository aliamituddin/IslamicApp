package com.example.islamicapp.ui.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.islamicapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {
    TextView name,email;
    String sName,sSect,sImageUri,newName,newSect;
    String uid;
    Button btnDone;
    ImageView btnEditDp;
    Spinner sectSpinner;
    ImageView dp;
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    FirebaseUser mUser;


    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 10;
    FirebaseStorage storage;
    StorageReference    storageReference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        btnDone = root.findViewById(R.id.btnDone);
        sectSpinner = root.findViewById(R.id.sectSpinner);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dp  = root.findViewById(R.id.displayDP);
        btnEditDp = root.findViewById(R.id.editDP);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        email.setEnabled(false);

        mReference = FirebaseDatabase.getInstance().getReference();
         uid = mAuth.getCurrentUser().getUid();
        mReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {


                    sName = dataSnapshot1.child("name").getValue(String.class);
                    sImageUri = dataSnapshot1.child("ImageUri").getValue(String.class);
                    if (dataSnapshot1.hasChild("sect")){
                        sSect = dataSnapshot1.child("sect").getValue(String.class);


                email.setText(mUser.getEmail());



                }
                email.setText(mUser.getEmail());
                if (sSect.equals("All")){

                    sectSpinner.setSelection(0);
                }
                if (sSect.equals("Shia")){

                    sectSpinner.setSelection(1);
                }
                if (sSect.equals("Sunni")){

                    sectSpinner.setSelection(2);
                }
                if (sSect.equals("Wahabi")){

                    sectSpinner.setSelection(3);
                }

                name.setText(sName);
                Picasso.get().load(sImageUri).into(dp);
                name.setText(sName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newSect = sectSpinner.getSelectedItem().toString();
                mReference.child("Users").child(uid).child("sect").setValue(newSect);

                newName = name.getText().toString();

                mReference.child("Users").child(uid).child("name").setValue(newName);

            }
        });

        btnEditDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        return root;
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null ){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);

                dp.setImageBitmap(bitmap);

            } catch (IOException e){
                e.printStackTrace();
            }
            if (filePath!=null){
                uploadDP();
            }
        }
    }
    private void uploadDP(){
        final StorageReference ref= storageReference.child("images/");

        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUri = uri.toString();
                        mReference.child("Users").child(uid).child("ImageUri").setValue(imageUri);
                    }
                });
            }
        });

    }

}