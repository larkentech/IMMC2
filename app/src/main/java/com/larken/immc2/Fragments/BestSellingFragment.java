package com.larken.immc2.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestSellingFragment extends Fragment {

    GridView gridView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BestSellingAdapter bestSellingAdapter;
    List<String> bestSellingID;



    public BestSellingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_best_selling, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bestSellingID = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.bestSellingGridView);
        List<BooksModal> booksModal = new ArrayList<>();
        bestSellingAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,booksModal,bestSellingID);
        gridView.setAdapter(bestSellingAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BestSelling");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal bestSelling = dataSnapshot.getValue(BooksModal.class);
                bestSellingAdapter.add(bestSelling);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
