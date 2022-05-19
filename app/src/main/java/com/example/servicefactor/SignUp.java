package com.example.servicefactor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {


    private EditText inputName, inputPhone, inputPassword;

    private TextView txtCa;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth

        setContentView(R.layout.signup_activity);
        Button signup = findViewById(R.id.btSignUp);
        inputName = findViewById(R.id.etnEmail);
        inputPassword = findViewById(R.id.etnPassword);
        inputPhone = findViewById(R.id.etnPhone);

        txtCa= findViewById(R.id.tvSignin);
        loadingBar = new ProgressDialog(this);
        txtCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(view -> createAccount());
    }

    public void createAccount() {
        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();
        String phone = inputPhone.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        } else {

            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait, until account is created.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidatingNumber(name, phone, password);
        }

    }

    private void ValidatingNumber(String name, String phone, String password) {

            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                    inputName.setText(" ");
                                    inputPassword.setText(" ");
                                    inputPhone.setText(" ");
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    loadingBar.dismiss();
                                    Toast.makeText(SignUp.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(SignUp.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUp.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

