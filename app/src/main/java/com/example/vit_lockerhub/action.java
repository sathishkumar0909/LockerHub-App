package com.example.vit_lockerhub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class action extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ToggleButton locker1=findViewById(R.id.locker1);
        ToggleButton locker2=findViewById(R.id.locker2);
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        locker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locker1.isChecked()){
                    db.child("lock_status").child("lock1").setValue(1);
                    Log.d("output", "On");
                }
                else
                    db.child("lock_status").child("lock1").setValue(0);
                    Log.d("Output", "OFF");
            }
        });

        locker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locker2.isChecked()){
                    db.child("lock_status").child("lock2").setValue(1);
                    Log.d("output", "On");
                }
                else
                    db.child("lock_status").child("lock2").setValue(0);
                Log.d("Output", "OFF");
            }
        });


    }
}