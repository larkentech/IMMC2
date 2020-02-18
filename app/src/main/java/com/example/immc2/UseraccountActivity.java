package com.example.immc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class UseraccountActivity extends AppCompatActivity {
    EditText username;
    EditText usermail;
    EditText usercity;
    EditText userflat;
    EditText userarea;
    EditText userzipcode;
    EditText userlandmark;
    Button saveprofile;

    CircleImageView profilepic;




    String Name;
    String Email;
    String City;
    String Flat;
    String Area;
    String Zipcode;
    String Landmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccount);

        username= findViewById(R.id.name);
        usermail=findViewById(R.id.entermail);
        usercity=findViewById(R.id.entercity);
        userflat=findViewById(R.id.enterflat);
        userarea=findViewById(R.id.enterarea);
        userzipcode=findViewById(R.id.enterzipcode);
        userlandmark=findViewById(R.id.enterlandmark);
        saveprofile=findViewById(R.id.btnsave);
        profilepic=findViewById(R.id.propicMain);


        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = username.getText().toString();
                Email = usermail.getText().toString();
                City = usercity.getText().toString();
                Flat = userflat.getText().toString();
                Area = userarea.getText().toString();
                Landmark = userlandmark.getText().toString();
                Zipcode = userzipcode.getText().toString();

                if (Name.isEmpty() || Email.isEmpty() || City.isEmpty() || Flat.isEmpty() ||
                        Landmark.isEmpty() || Zipcode.isEmpty() || Area.isEmpty())
                {
                    Toasty.error(UseraccountActivity.this, "Enter Required Details").show();
                    if (Zipcode.length() < 6) {
                        Toasty.error(UseraccountActivity.this, "Invalid Zipcode").show();
                    }
                    Toasty.error(UseraccountActivity.this, "Enter Valid Zipcode").show();

                }
                else{
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();

                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("Name", username.getText().toString());
                    userMap.put("Email", usermail.getText().toString());


                    userMap.put("PhoneNumber", currentFirebaseUser.getPhoneNumber().toString());
                    databaseReference.child("UserDetails").child(currentFirebaseUser.getUid()).setValue(userMap);


                    DatabaseReference databaseReference1=database.getReference();
                    HashMap<String,Object> addMap=new HashMap<>();
                    addMap.put("Flatno",userflat.getText().toString());
                    addMap.put("Landmark",userlandmark.getText().toString());
                    addMap.put("Area",userarea.getText().toString());
                    addMap.put("City",usercity.getText().toString());
                    addMap.put("Zipcode",userzipcode.getText().toString());


                    databaseReference1.child("UserDetails").child(currentFirebaseUser.getUid()).child("Address").setValue(addMap);


                    Intent i = new Intent(UseraccountActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

        });
    }


}

