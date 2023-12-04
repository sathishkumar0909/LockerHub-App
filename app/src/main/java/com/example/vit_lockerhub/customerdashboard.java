package com.example.vit_lockerhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customerdashboard extends AppCompatActivity {

    String lockerselected;
//    String username=getIntent().getStringExtra("username");
    String username="7904746974";
    Button btnview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdashboard);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        EditText productid=findViewById(R.id.productidcust);
        EditText otp=findViewById(R.id.otp);
        Spinner locker=findViewById(R.id.spinner);
        Button open=findViewById(R.id.open);
        String[] lockers = { "Locker 1","Locker 2"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,lockers);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locker.setAdapter(aa);

        locker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 lockerselected=lockers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child("users").child("customer").child(username).child("products").child(productid.getText().toString()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String lockername= (String) snapshot.child("LockerID").getValue();
                        String otpdb= (String) snapshot.child("OTP").getValue();
                        if (lockername.equals(lockerselected) && otp.getText().toString().equals(otpdb)){
                            db.child("users").child("customer").child(username).child("products").child(productid.getText().toString()).removeValue();
                            Log.d("Status","open");
                            Toast.makeText(customerdashboard.this, "Take your Products and close the locker", Toast.LENGTH_SHORT).show();
                            if(lockerselected=="Locker 1") {
                                db.child("lock_status").child("lock1").setValue(0);
                            }
                            else{
                                db.child("lock_status").child("lock2").setValue(0);
                            }
                            Intent intent=new Intent(customerdashboard.this,collectitem.class);
                            intent.putExtra("lock",lockerselected);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}