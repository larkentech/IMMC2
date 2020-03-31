package com.larken.immc2.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.AdapterClasses.ComingSoonAdapter;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.ModalClasses.ComingSoonModal;
import com.larken.immc2.ModalClasses.PaymentModal;
import com.larken.immc2.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComingSoonFragment extends DialogFragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ComingSoonAdapter comingSoonAdapter;

    String productImage;
    String productName;
    String releaseDate;
    String productDesc;
    private Object ComingSoonModal;


    public ComingSoonFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        productName = getArguments().getString("ProductName");
        productImage = getArguments().getString("ProductImage");
        releaseDate = getArguments().getString("ReleaseDate");
        productDesc = getArguments().getString("Description");


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);



    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }catch (Exception e){
            return;
        }


    }



    }

