package com.example.helloworldgrpc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UIExtension extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newui);

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setHideOnScroll(true);
        FloatingActionButton homeFab = findViewById(R.id.homeFab);
        //base image
        ImageView baseImage = findViewById(R.id.image);
        Button addButton = findViewById(R.id.addBtn);
        TextView baseHeadText = findViewById(R.id.headText);
        ImageView notifyIcon = findViewById(R.id.notifyIcon);
        TextView ratingText = findViewById(R.id.ratingText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        //Detail image
        CardView cardView = findViewById(R.id.cardView);
        ImageView detailImage = findViewById(R.id.imageDetail);
        Chip dataChip = findViewById(R.id.chip);
        ImageButton shareImage = findViewById(R.id.shareImg);
        ImageButton saveImage = findViewById(R.id.saveImg);
        TextView detailText = findViewById(R.id.contentText);
        CheckBox addCheckBox = findViewById(R.id.addCheck);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.VISIBLE);
                detailText.setText("In detailed content of an item");
            }
        });

        homeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseImage.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);
                baseHeadText.setText("Base Heading");
                notifyIcon.setVisibility(View.VISIBLE);
                ratingText.setText("Reviewed - 3.9");
                descriptionText.setText("Base details of the item");

            }
        });


    }
}
