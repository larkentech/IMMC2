package com.example.immc2.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.immc2.MainActivity;
import com.example.immc2.R;
import com.example.immc2.UseraccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdrsFragment extends DialogFragment {

    EditText enterPincode;
    EditText enterHouseNo;
    EditText enterArea;
    EditText enterCity;
    EditText enterState;
    EditText enterLandmark;

    EditText enterName;
    EditText enterPhone;

    Button btnSave;

    String Name;
    String Phone;
    String City;
    String Area;
    String Landmark;
    String HouseNo;
    String Pincode;
    String State;


    public AdrsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Edit Address");
        return inflater.inflate(R.layout.fragment_adrs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      /*  enterName=view.findViewById(R.id.enterName);
        enterPhone=view.findViewById(R.id.enterNumber);
        enterPincode=view.findViewById(R.id.enterPincode);
        enterHouseNo=view.findViewById(R.id.enterFlatNo);
        enterArea=view.findViewById(R.id.enterArea);
        enterCity=view.findViewById(R.id.enterCity);
        enterState=view.findViewById(R.id.enterState);
        enterLandmark=view.findViewById(R.id.enterLandmark);

        btnSave=view.findViewById(R.id.btnsave);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Name = enterName.getText().toString();
                        Phone=enterPhone.getText().toString();
                        City = enterCity.getText().toString();
                        HouseNo = enterHouseNo.getText().toString();
                        Area = enterArea.getText().toString();
                        Landmark = enterLandmark.getText().toString();
                        Pincode = enterPincode.getText().toString();
                        State=enterState.getText().toString();




                        if (Name.isEmpty()  || City.isEmpty() || HouseNo.isEmpty() || Pincode.isEmpty() || Area.isEmpty() ) {
                            Toasty.error(getContext(), "Enter Required Details").show();
                            if (Pincode.length() < 6) {
                                Toasty.error(getContext(), "Invalid Pipcode").show();
                            }
                            Toasty.error(getContext(), "Enter Valid Pipcode").show();

                        } else {
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference();

                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("Name", enterName.getText().toString());
                            userMap.put("Phone", enterPhone.getText().toString());




                            databaseReference.child("UserAddress").child(currentFirebaseUser.getUid()).setValue(userMap);


                            DatabaseReference databaseReference1 = database.getReference();
                            HashMap<String, Object> addMap = new HashMap<>();
                            addMap.put("Pincode", enterPincode.getText().toString());
                            addMap.put("House", enterHouseNo.getText().toString());
                            addMap.put("Area", enterArea.getText().toString());
                            addMap.put("City", enterCity.getText().toString());
                            addMap.put("State",enterState.getText().toString());
                            addMap.put("Landmark", enterLandmark.getText().toString());




                            databaseReference1.child("UserAddress").child(currentFirebaseUser.getUid()).child("Address").setValue(addMap);


                            Intent i = new Intent(getContext(), MainActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }
                });
            }
        }); */



    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Address");
        builder.setView(R.layout.fragment_adrs);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getContext(),"HEllo", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        return builder.create();
    }
}
