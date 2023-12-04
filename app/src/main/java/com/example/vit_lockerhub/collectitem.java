package com.example.vit_lockerhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class collectitem extends AppCompatActivity {

    String locker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectitem);
        CardView todashboard=findViewById(R.id.todasboard);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        locker= getIntent().getStringExtra("lock");
        todashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locker=="Locker 1") {
                    db.child("lock_status").child("lock1").setValue(1);
                }
                else{
                    db.child("lock_status").child("lock2").setValue(1);
                }                startActivity(new Intent(collectitem.this,Landing_Page.class));
            }
        });

    }
}