package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.Fragments.HomeFragment;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class OtpActivity extends AppCompatActivity {
    private String varificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressbar;
    private EditText enterOTP;
    Button verifyOtp;
    private String mVerificationId;


    String phoneNumberFlag;
    String Name;
    String phonenumber;
    String Credential;
    String code;
    String signUpFlag="normal";

    PhoneAuthCredential phoneCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);



        progressbar=findViewById(R.id.progressbar);
        enterOTP=findViewById(R.id.enterotp);

        String phonenumber=getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        verifyOtp = findViewById(R.id.btnenterotp);

        mAuth=FirebaseAuth.getInstance();
        sendVerificationCode(phonenumber);

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=enterOTP.getText().toString().trim();
                if((code.isEmpty()|| code.length()<6)){
                    enterOTP.setError("Enter code..");
                    enterOTP.requestFocus();
                }
                verifyVerificationCode(code);

            }
        });

    }
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                enterOTP.setText(code);
                verifyOtp.setEnabled(true);
                //verifyButton.setBackgroundColor(getColor(R.color.primaryTrans));


            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            //mResendToken = forceResendingToken;

        }


    };

    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


            mAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try{
                                if (task.isSuccessful()) {

                                    checkUser(mAuth.getCurrentUser().getUid());

                                } else {

                                    String message = "Somthing is wrong, we will fix it soon...";

                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        message = "Invalid code entered...";
                                        Toasty.error(getApplicationContext(),message).show();
                                    }
                                }
                            }catch (Exception e)
                            {
                                Log.v("TAG","OTP Exception"+e);

                                Toast.makeText(OtpActivity.this,"Incorrect OTP",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    public void checkUser(final String userUID){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userUID))
                {
                    Intent i = new Intent(OtpActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else {
                    Intent i = new Intent(OtpActivity.this, UseraccountActivity.class);
                    startActivity(i);
                    finish();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}


