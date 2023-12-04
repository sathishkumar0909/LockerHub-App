package com.example.vit_lockerhub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class colortest extends AppCompatActivity {

    private ImageView locker1Icon, locker2Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colortest);

        locker1Icon = findViewById(R.id.locker1Icon);
        locker2Icon = findViewById(R.id.locker2Icon);

        // Assume these values represent the occupancy status of the lockers (0 for occupied, 1 for unoccupied)
        int locker1Status = 0;
        int locker2Status = 1;

        // Change locker icon colors based on occupancy status
        updateLockerIcon(locker1Icon, locker1Status);
        updateLockerIcon(locker2Icon, locker2Status);
    }

    private void updateLockerIcon(ImageView imageView, int status) {
        if (status == 0) {
            // Locker is occupied (red color)
            imageView.setColorFilter(getResources().getColor(android.R.color.holo_red_light));
        } else {
            // Locker is unoccupied (green color)
            imageView.setColorFilter(getResources().getColor(android.R.color.holo_green_light));
        }
    }
}


