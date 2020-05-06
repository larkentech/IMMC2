package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private static final int RC_SIGN_IN = 123;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        printHashKey(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d("AppLog", "key:" + FacebookSdk.getApplicationSignature(this));

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (haveNetworkConnection())
                {

                    if (mAuth.getCurrentUser() != null)
                    {
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    else {

                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(providers)
                                        .setTheme(R.style.LoginTheme)
                                        .setLogo(R.drawable.logo)
                                        .build(),
                                RC_SIGN_IN);
                    }

                }
                else {
                    Intent i = new Intent(getApplicationContext(),NoInternetActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },3000);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                checkUser();
                // ...
            } else {

            }
        }
    }

    public void checkUser()
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(mAuth.getUid()))
                {
                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    Intent intent=new Intent(SplashActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }
}
