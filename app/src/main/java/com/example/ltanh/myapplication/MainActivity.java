package com.example.ltanh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView txtNumber;
    Button btnRandom;
    EditText edtNumber1, edtNumber2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AnhXa();



        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chuoi1 = edtNumber1.getText().toString().trim();
                String chuoi2 = edtNumber2.getText().toString().trim();
                if(chuoi1.isEmpty() || chuoi2.length() == 0){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ số!",Toast.LENGTH_SHORT).show();
                }else{
                    //ép kiểu
                    int min = Integer.parseInt(chuoi1);
                    int max = Integer.parseInt(chuoi2);

                    //tạo số ngẫu nghiên qua hàm random
                    Random random = new Random();
                    int number = random.nextInt(max-min +1)+min; //random trong khoảng 0 đến 99
                    //ép số sang chuỗi
//                txtNumber.setText(number + "");
                    txtNumber.setText(String.valueOf(number));
                }


            }
        });
    }

    private void AnhXa(){
        txtNumber = (TextView) findViewById(R.id.textViewNumber);
        btnRandom = (Button) findViewById(R.id.buttonRandom);
        edtNumber1 = (EditText) findViewById(R.id.editTextNumber1);
        edtNumber2 = (EditText) findViewById(R.id.editTextNumber2);
    }
}
