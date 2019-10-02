package com.example.islamicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.islamicapp.Models.User;
import com.example.islamicapp.Utils.Save;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Signup extends AppCompatActivity {
    protected EditText Name, EmailAddress,Password, CPassword;
    protected FirebaseAuth auth;
    private Spinner secSinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = findViewById(R.id.name);
        EmailAddress = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        CPassword = findViewById(R.id.cPassword);
        secSinner = findViewById(R.id.selectSect);
        auth = FirebaseAuth.getInstance();

    }
    public void signup(View view){
       Name.setError(null);
       EmailAddress.setError(null);
       Password.setError(null);
       CPassword.setError(null);

       View focusView = null;
       boolean cancel = false;

       final String name = Name.getText().toString();
       String emai = EmailAddress.getText().toString();
       String password = Password.getText().toString();
       String cPassword = CPassword.getText().toString();
       final String sect = secSinner.getSelectedItem().toString();

       if (!cPassword.equals(password)){
           CPassword.setError("Password doesn't match");
           cancel = true;
           focusView = CPassword;

       }
       if (password.length()<6){
           Password.setError("It must be more than 6");
           cancel = true;
           focusView = Password;
       }
        if (password.isEmpty()){
            Password.setError("Must enter password");
            cancel = true;
            focusView = Password;

        }
        if (!emai.contains("@")|| !emai.contains(".com")){
            EmailAddress.setError("Email is invalid");
            cancel = true;
            focusView = CPassword;

        }
        if (name.isEmpty()){
            Name.setError("Enter Name");
            cancel = true;
            focusView= Name;

        }
        if (cancel){
            focusView.requestFocus();
        }else if (!cancel){
        auth.createUserWithEmailAndPassword(emai,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()){
                showErrorDialog("There was a problem in signing up");
            }else {
                User    user   = new User(
                        name,sect
                );
                FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(auth.getUid())).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Signup.this, "Signed Up", Toast.LENGTH_SHORT).show();
                        Save.save(getApplicationContext(),"session","true");
                    }
                    }
                });



            }
            }
        });
        }


    }
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
