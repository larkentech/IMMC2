package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;

public class LoginActivity extends AppCompatActivity {

    //OTP SignIn
    private EditText Phonenumber;
    private TextView Getotp;

    //Verify Otp
    private String varificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressbar;
    private OtpTextView enterOTP;
    TextView verifyOtp;
    private String mVerificationId;

    String phoneNumberFlag;
    String Name;
    String phonenumber;
    String Credential;
    String code;
    String signUpFlag="normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Phonenumber=findViewById(R.id.enternumber);
        Getotp=findViewById(R.id.btngetotp);
        enterOTP=findViewById(R.id.otpView);
        verifyOtp = findViewById(R.id.verifyotp);
        mAuth=FirebaseAuth.getInstance();


        findViewById(R.id.btngetotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String code=CountryData.CountryAreaCode[spinner.getSelectedItemPosition()];
                phonenumber=Phonenumber.getText().toString().trim();

                if(phonenumber.isEmpty()||phonenumber.length()<10){
                    Phonenumber.setError("Enter Valid Number");
                    Phonenumber.requestFocus();
                    return;
                }
                else {
                    verifyOtp.setVisibility(View.VISIBLE);
                    sendVerificationCode(phonenumber);
                }

            }
        });

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=enterOTP.getOTP().toString().trim();
                if((code.isEmpty()|| code.length()<6)){
                    Toasty.error(getApplicationContext(),"Invalid otp. Try Again").show();
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
                enterOTP.setOTP(code);
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


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try{
                            if (task.isSuccessful()) {

                                checkUser(mAuth.getCurrentUser().getUid());

                            } else {

                                String message = "Somthing is wrong, we will fix it soon...";

                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    message = "Invalid code entered...";
                                    Toasty.error(getApplicationContext(),"Invalid Code").show();
                                }
                            }
                        }catch (Exception e)
                        {
                            Log.v("TAG","OTP Exception"+e);

                            Toast.makeText(LoginActivity.this,"Incorrect OTP",Toast.LENGTH_LONG).show();
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
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(LoginActivity.this, UseraccountActivity.class);
                    i.putExtra("Phone",phonenumber);
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
