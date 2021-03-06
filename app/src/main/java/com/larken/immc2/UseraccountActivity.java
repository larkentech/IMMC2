package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.larken.immc2.Fragments.AccountFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class UseraccountActivity extends AppCompatActivity {
    MaterialEditText username;
    MaterialEditText usermail;
    MaterialEditText usercity;
    MaterialEditText userflat;
    MaterialEditText userarea;
    MaterialEditText userzipcode;
    MaterialEditText userlandmark;
    MaterialEditText userPhone;
    Button saveprofile;


    ImageView profilepic;

    public static final int IMAGE_CODE=1;
    Uri imageuri;
    String userProfileUrl;

    StorageReference mStorageRef;

    String Name;
    String Email;
    String City;
    String Flat;
    String Area;
    String Zipcode;
    String Landmark;
    String phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccount);
        mStorageRef = FirebaseStorage.getInstance().getReference("Profile_Pictures");
        phone = getIntent().getExtras().getString("Phone");

        username = findViewById(R.id.name);
        usermail = findViewById(R.id.entermail);
        usercity = findViewById(R.id.entercity);
        userflat = findViewById(R.id.enterflat);
        userarea = findViewById(R.id.enterarea);
        userzipcode = findViewById(R.id.enterzipcode);
        userlandmark = findViewById(R.id.enterlandmark);
        saveprofile = findViewById(R.id.btnsave);
        profilepic = findViewById(R.id.propicMain);
        userPhone = findViewById(R.id.phone);
        userPhone.setText("+91 "+phone);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        username.setText(mAuth.getCurrentUser().getDisplayName());
        usermail.setText(mAuth.getCurrentUser().getEmail());
        Glide
                .with(getApplicationContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .centerCrop()
                .into(profilepic);



        profilepic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openImageForm();
            }
        });



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
                        Landmark.isEmpty() || Zipcode.isEmpty() || Area.isEmpty()) {
                    Toasty.error(UseraccountActivity.this, "Enter Required Details").show();

                    }
                else
                    {
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();

                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("Name", Name);
                    userMap.put("Email", Email);
                    userMap.put("ProfilePhoto",mAuth.getCurrentUser().getPhotoUrl().toString());
                    userMap.put("PhoneNumber", mAuth.getCurrentUser().getPhoneNumber().toString());

                    databaseReference.child("UserDetails").child(mAuth.getUid()).setValue(userMap);

                    DatabaseReference databaseReference1 = database.getReference();
                    HashMap<String, Object> addMap = new HashMap<>();
                    addMap.put("Flatno", userflat.getText().toString());
                    addMap.put("Landmark", userlandmark.getText().toString());
                    addMap.put("Area", userarea.getText().toString());
                    addMap.put("City", usercity.getText().toString());
                    addMap.put("Zipcode", userzipcode.getText().toString());


                    databaseReference1.child("UserDetails").child(mAuth.getUid()).child("Address").setValue(addMap);


                    Intent i = new Intent(UseraccountActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }


    private String getExtension(Uri uri)
    {

        ContentResolver contentResolver=getContentResolver();
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void openImageForm() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_CODE && requestCode == RESULT_OK && data != null && data.getData() !=null);

        imageuri=data.getData();
        profilepic.setImageURI(imageuri);


        final StorageReference reference=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imageuri));

        reference.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                userProfileUrl = uri.toString();
                            }
                        });
                        Toast.makeText(UseraccountActivity.this,"Image Uploaded Successfully",Toast.LENGTH_LONG).show();





                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...


                    }
                });


    }


}

