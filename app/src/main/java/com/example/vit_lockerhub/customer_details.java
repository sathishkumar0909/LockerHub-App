package com.example.vit_lockerhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class customer_details extends AppCompatActivity {
    Random rand = new Random();
    String otp;
    String lockername;
    //String lockername="locker1";
    String lockno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        lockername=getIntent().getStringExtra("lockername");
        lockno=getIntent().getStringExtra("lockno");
        EditText custname=findViewById(R.id.custname);
        EditText custph=findViewById(R.id.custph);
        EditText custmail=findViewById(R.id.custmail);
        EditText productid=findViewById(R.id.productid);
        Button submit=findViewById(R.id.finish);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressBar = new ProgressDialog(customer_details.this);
                progressBar.setCancelable(false);//you can cancel it by pressing back button
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setMessage("Please Keep the product Inside the Locker ...");
                progressBar.setProgress(0);//initially progress is 0
                progressBar.setMax(100);//sets the maximum value 100
                progressBar.show();//displays the progress bar
                db.child("objectdetection").child(lockername).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String status=snapshot.getValue().toString();
                        if (status.equals("0")){
                            progressBar.dismiss();
                            db.child("users").child("customer").child(custph.getText().toString()).child("products").child(productid.getText().toString()).child("Customer_Name").setValue(custname.getText().toString());
                            db.child("users").child("customer").child(custph.getText().toString()).child("products").child(productid.getText().toString()).child("Customer_Phone").setValue(custph.getText().toString());
                            db.child("users").child("customer").child(custph.getText().toString()).child("products").child(productid.getText().toString()).child("Customer_Mail").setValue(custmail.getText().toString());
                            db.child("users").child("customer").child(custph.getText().toString()).child("products").child(productid.getText().toString()).child("LockerID").setValue(lockername);
                            db.child("users").child("customer").child(custph.getText().toString()).child("products").child(productid.getText().toString()).child("productID").setValue(productid.getText().toString());
                            db.child("lock_status").child(lockno).setValue(1);
                            otp= String.valueOf(rand.nextInt((9999 - 100) + 1) + 10);
                            db.child("users").child("customer").child(custph.getText().toString()).child("products").child(productid.getText().toString()).child("OTP").setValue(otp);
                            progressBar.setMessage("Please Close the locker ...");
                            progressBar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.dismiss();
                                    String recipient = custmail.getText().toString();
                                    String subject = "OTP To Open Locker";
                                    String message = "Do Not share this OTP to anyone. Your OTP is "+otp + "Your Product Id is "+ productid.getText().toString();

                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.putExtra(Intent.EXTRA_EMAIL, recipient);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                    intent.putExtra(Intent.EXTRA_TEXT, message);
                                    intent.setType("message/rfc822");
                                    startActivity(Intent.createChooser(intent, "Choose an email client"));
                                    finish();
                                    Toast.makeText(customer_details.this, "Thank you,Your job is Done", Toast.LENGTH_SHORT).show();
                                }
                            },5000);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
    private void sendEmail() {

    }
}

