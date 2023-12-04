package com.example.vit_lockerhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        LinearLayout locker1= findViewById(R.id.lockerd1);
        LinearLayout locker2=findViewById(R.id.locker2);

        db.child("objectdetection").child("Locker 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status=snapshot.getValue().toString();
                updateLockerIcon(locker1, status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.child("objectdetection").child("Locker 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status=snapshot.getValue().toString();
                updateLockerIcon(locker2, status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        locker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setCancelable(false);
                builder.setTitle("Confirm?");
                builder.setMessage("Do you Want to keep the Product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(Dashboard.this,customer_details.class);
                                intent.putExtra("lockername","Locker 1");
                                intent.putExtra("lockno","lock1");
                                db.child("lock_status").child("lock1").setValue(0);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
// Your code to handle No button click
                                dialog.dismiss();

                            }
                        });
// Create the AlertDialog
                builder.create();
// Show the Dialog
                builder.show();
        }
        });

        locker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setCancelable(false);
                builder.setTitle("Confirm?");
                builder.setMessage("Do you Want to keep the Product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(Dashboard.this,customer_details.class);
                                intent.putExtra("lockername","Locker 2");
                                intent.putExtra("lockno","lock2");
                                db.child("lock_status").child("lock2").setValue(0);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
// Your code to handle No button click
                                dialog.dismiss();

                            }
                        });
// Create the AlertDialog
                builder.create();
// Show the Dialog
                builder.show();
            }
        });
    }



    private void updateLockerIcon(LinearLayout linearLayout, String status) {
        if (status.equals("0")) {
            // Locker is occupied (red color)
            linearLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            linearLayout.setClickable(false);
        } else {
            // Locker is unoccupied (green color)
            linearLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            linearLayout.setClickable(true);
        }
    }
        }
