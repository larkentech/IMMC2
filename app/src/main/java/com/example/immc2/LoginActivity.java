package com.example.immc2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //OTP SignIn
    private EditText Phonenumber;
    private Button Getotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Phonenumber=findViewById(R.id.enternumber);
        Getotp=findViewById(R.id.btngetotp);

        findViewById(R.id.btngetotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String code=CountryData.CountryAreaCode[spinner.getSelectedItemPosition()];
                String number=Phonenumber.getText().toString().trim();

                if(number.isEmpty()||number.length()<10){
                    Phonenumber.setError("Enter Valid Number");
                    Phonenumber.requestFocus();
                    return;
                }
                else {
                    String phonenumber = number;
                    Intent intent=new Intent(LoginActivity.this,OtpActivity.class);
                    intent.putExtra("phonenumber",phonenumber);
                    startActivity(intent);
                }

            }
        });


    }
}
