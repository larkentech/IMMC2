package com.larken.immc2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.larken.immc2.AdapterClasses.ComingSoonAdapter;
import com.larken.immc2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComingSoonFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ComingSoonAdapter comingSoonAdapter;

    String productImage;
    String productName;
    String releaseDate;

    public ComingSoonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        productName = getArguments().getString("ProductName");
        productImage = getArguments().getString("ProductImage");
        releaseDate = getArguments().getString("ReleaseDate");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
