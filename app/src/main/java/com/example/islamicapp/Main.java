    package com.example.islamicapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.example.islamicapp.Utils.Save;
import com.google.android.gms.tasks.OnSuccessListener;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
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

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

public class Main extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvEmailAddress,tvUsername;
    private ImageView displayDP;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 10;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    FirebaseUser currentUser;
    String UID;
    boolean session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        SESSION();

//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
//                if (destination.getId() == controller.getGraph().getStartDestination()){
//
//                }
//            }
//        });

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Fragment fragment = null;
//                if (menuItem.getItemId() == R.id.nav_gallery){
//                    Toast.makeText(Main.this, "Gallery", Toast.LENGTH_SHORT).show();
//                    fragment = new WazaifFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.,fragment).commit();
//                    Toast.makeText(Main.this, "opened", Toast.LENGTH_SHORT).show();
//
//                }
//
//                return false;
//            }
//        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            Save.save(getApplicationContext(), "session", "false");
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        return false;
//    }
    public void updateNavHeader(){


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvEmailAddress = headerView.findViewById(R.id.id);
        tvUsername = headerView.findViewById(R.id.username);
        displayDP = headerView.findViewById(R.id.displayDP);
        UID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        mDatabaseReference.child("Users").child(UID).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String username = dataSnapshot.getValue(String.class);
                tvUsername.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvEmailAddress.setText(currentUser.getEmail());
        mDatabaseReference.child("Users").child(UID).child("ImageUri").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uri = dataSnapshot.getValue(String.class);
                Picasso.get().load(uri).into(displayDP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public  void SESSION(){
        session = Boolean.parseBoolean(Save.read(getApplicationContext(),"session","false"));
        if (!session){
            Toast.makeText(this, "Your not logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Main.this,Login.class);
            startActivity(intent);
            finish();

        }else {
            updateNavHeader();
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
        }
    }
}
