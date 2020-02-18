package com.example.immc2.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.immc2.R;
import com.example.immc2.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView userName;
    TextView userEmail;
    TextView userphone;
    ImageView userImage;
    EditText userFlat;
    EditText userlandmark;
    EditText userArea;
    EditText userCity;

    FirebaseAuth mAuth;

    TextView signout;
    TextView feedback;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        feedback = view.findViewById(R.id.feedbackTxt);

        userName = view.findViewById(R.id.prsnName);
        userEmail = view.findViewById(R.id.prsnMail);
        userphone = view.findViewById(R.id.prsnNum);
        userImage = view.findViewById(R.id.prsnImage);
        userFlat = view.findViewById(R.id.addFlatnum);
        userlandmark = view.findViewById(R.id.addLandmark);
        userArea = view.findViewById(R.id.addArea);
        userCity = view.findViewById(R.id.addCity);
        signout = view.findViewById(R.id.signOut);
        feedback = view.findViewById(R.id.feedbackTxt);

        mAuth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child("Name").getValue(String.class));
                userEmail.setText(dataSnapshot.child("Email").getValue(String.class));
                userphone.setText(dataSnapshot.child("PhoneNumber").getValue(String.class));
                userFlat.setText(dataSnapshot.child("Address").child("Flatno").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getActivity(), SplashActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog();


            }
        });

    }

}
