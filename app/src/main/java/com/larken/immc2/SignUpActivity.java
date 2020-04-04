package com.larken.immc2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.larken.immc2.HelperClasses.CheckConnection;

import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    Button getOtp;
    EditText phoneNumberEt;
    String phoneNumber;

    CardView loginSocial;

    private static final int RC_SIGN_IN = 123;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getOtp = findViewById(R.id.getOTPLoginSignUp);
        phoneNumberEt = findViewById(R.id.phoneNumberEt);

        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = phoneNumberEt.getText().toString().trim();
                if (phoneNumber.matches("") || phoneNumber.length() < 10)
                {
                    Toasty.error(getApplicationContext(),"Enter a valid number").show();
                    phoneNumberEt.getText().clear();
                }
                else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    i.putExtra("PhoneNumber",phoneNumber);
                    i.putExtra("FlowType","OTP");
                    startActivity(i);
                }


            }
        });

        loginSocial = findViewById(R.id.otherSocialLogin);
        loginSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setTheme(R.style.LoginTheme)
                                .build(),
                        RC_SIGN_IN);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                if (new CheckConnection().checkLogin())
                {
                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    i.putExtra("PhoneNumber","9611967501");
                    i.putExtra("FlowType","SignUp");
                    startActivity(i);
                }










                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
