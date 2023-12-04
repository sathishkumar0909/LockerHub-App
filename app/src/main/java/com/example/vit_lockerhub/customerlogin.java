package com.example.vit_lockerhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customerlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerlogin);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        EditText custusername=findViewById(R.id.custusername);
        EditText custpassword=findViewById(R.id.custpassword);
        Button custlogin=findViewById(R.id.custlogin);

        custlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if ((custusername.getText().toString()).isEmpty()||(custusername.getText().toString()).isEmpty()){
                            Toast.makeText(customerlogin.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            db.child("users").child("customer").addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    String username = custusername.getText().toString();
                                    if (username.equals(snapshot.getKey())) {
                                        String password = custpassword.getText().toString();
                                        if (password.equals(snapshot.child("password").getValue(String.class))){
                                            Toast.makeText(customerlogin.this, "Logged in", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(customerlogin.this,customerdashboard.class);
                                            intent.putExtra("username",custusername.getText().toString());
                                            startActivity(intent);
                                        }
                                        else
                                            Toast.makeText(customerlogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(customerlogin.this, "User not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
    }
}