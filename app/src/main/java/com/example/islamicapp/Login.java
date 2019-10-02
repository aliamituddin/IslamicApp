package com.example.islamicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.islamicapp.Utils.Save;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    protected EditText Email, Password;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Objects.requireNonNull(getSupportActionBar()).hide();

        auth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);




    }
    public void btnSignup(View view){
        Intent intent = new Intent(Login.this,Signup.class);
        startActivity(intent);
    }
    public void login(View view){
        Email.setError(null);
        Password.setError(null);
        View focusView =null;
        boolean canel = false;


        String email = Email.getText().toString();
        final String password = Password.getText().toString();

        if (password.isEmpty()){
            Password.setError("Enter Password");
            canel = true;
            focusView = Password;
        }

        if (!email.contains("@") || !email.contains(".com")){
        Email.setError("Email is invalid");
        focusView = Email;
        canel = true;

        }

        if (canel){
            focusView.requestFocus();
        }
        if (!canel){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()){
                        showErrorDialog("There was a problem in logging in");
                        Password.setText("");
                    }
                    else{
                        Save.save(getApplicationContext(),"session","true");
                        Intent intent = new Intent(Login.this,Main.class);
                        startActivity(intent);
                        finish();

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