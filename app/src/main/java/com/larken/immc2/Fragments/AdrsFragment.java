package com.larken.immc2.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
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

import com.larken.immc2.PaymentActivity;
import com.larken.immc2.R;

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

    Button buttonOK;



    public AdrsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getDialog().setTitle("Edit Address");
        return inflater.inflate(R.layout.fragment_adrs, container, false);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonOK=view.findViewById(R.id.btnsAVE);



        enterName=view.findViewById(R.id.enterName);
        enterPhone=view.findViewById(R.id.enterNumber);
        enterCity=view.findViewById(R.id.enterCity);
        enterArea=view.findViewById(R.id.enterArea);
        enterHouseNo=view.findViewById(R.id.enterFlatNo);
        enterLandmark=view.findViewById(R.id.enterLandmark);
        enterState=view.findViewById(R.id.enterState);
        enterPincode=view.findViewById(R.id.enterPincode);





        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(enterName.getText().toString().matches("") || enterPhone.getText().toString().matches("")
                || enterHouseNo.getText().toString().matches("")||enterLandmark.getText().toString().matches("") || enterArea.getText().toString().matches("")
                ||enterCity.getText().toString().matches("") ||enterState.getText().toString().matches("")|| enterPincode.getText().toString().matches("") )
                {
                    Toasty.error(getContext(),"Please enter all the details").show();
                }
                else {
                    String name = enterName.getText().toString();
                    String number=enterPhone.getText().toString();
                    String finalAddress = enterHouseNo.getText().toString() +",\nLandmark: "+
                            enterLandmark.getText().toString() +","+ enterArea.getText().toString() +","+
                            enterCity.getText().toString() +","+ enterState.getText().toString() +","+
                            enterPincode.getText().toString();


                    ((PaymentActivity)(getContext())).userName.setText(name);
                    ((PaymentActivity)getContext()).userPhone.setText(number);
                    ((PaymentActivity)getContext()).userAddress.setText(finalAddress);
                    getDialog().dismiss();
                }

            }
        });

    }
}
