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

public class deliverylogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverylogin);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        EditText delusername=findViewById(R.id.delusername);
        EditText delpassword=findViewById(R.id.delpassword);
        Button dellogin=findViewById(R.id.dellogin);

        dellogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((delusername.getText().toString()).isEmpty()||(delusername.getText().toString()).isEmpty()){
                    Toast.makeText(deliverylogin.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.child("users").child("deliverypartner").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            String username = delusername.getText().toString();
                            if (username.equals(snapshot.getKey())) {
                                String password = delpassword.getText().toString();
                                if (password.equals(snapshot.child("password").getValue(String.class))){
                                    Toast.makeText(deliverylogin.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(deliverylogin.this,Dashboard.class));
                                }
                                else
                                    Toast.makeText(deliverylogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(deliverylogin.this, "User not exist", Toast.LENGTH_SHORT).show();
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