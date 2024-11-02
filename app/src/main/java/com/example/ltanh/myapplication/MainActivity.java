package com.example.ltanh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    ArrayList<Integer> arrayImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.myLinearlayout);

        arrayImage = new ArrayList<>();
        arrayImage.add(R.drawable.img11);
        arrayImage.add(R.drawable.img2);
        arrayImage.add(R.drawable.img3);

        Random random = new Random();
        int index = random.nextInt(arrayImage.size());

        linearLayout.setBackgroundResource(arrayImage.get(index));



    }

}
