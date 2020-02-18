package com.example.immc2.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.immc2.AdapterClasses.BestSellingAdapter;
import com.example.immc2.BookDetailsActivity;
import com.example.immc2.ModalClasses.BooksModal;
import com.example.immc2.ModalClasses.FeaturedModal;
import com.example.immc2.R;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionFragment extends Fragment {

    NumberPicker numberPicker;


    private String BookDesc;
    public String[] keysURL=new String[100];
    public int k=0;


    Button add;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView Selected_Book_Desc;

    HListView bestSellingListView;
    BestSellingAdapter bestSellingAdapter;

    TextView bookDesc;
    DatabaseReference databaseReference2;


    public IntroductionFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       String bookID = ((BookDetailsActivity)getActivity()).bookID;
       String bookSubCategory = ((BookDetailsActivity)getActivity()).bookSubCategoryID;
       String bookCategoryID = ((BookDetailsActivity)getActivity()).bookCategoryID;

        bookDesc = view.findViewById(R.id.selected_book_desc);
        bestSellingListView = view.findViewById(R.id.best_selling_list);
        List<BooksModal> bestSellingList = new ArrayList<>();
        final List<String> bestSellingIDs = new ArrayList<>();
        bestSellingAdapter = new BestSellingAdapter(getContext(),R.layout.best_seller_single,bestSellingList,bestSellingIDs);
        bestSellingListView.setAdapter(bestSellingAdapter);

        //Number Picker
        numberPicker = view.findViewById(R.id.number_picker);
        String count = numberPicker.getDisplayedValues().toString();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BestSelling");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                bestSellingAdapter.add(modal);
                bestSellingIDs.add(dataSnapshot.getKey());

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

        if (bookSubCategory == null)
        {
            databaseReference2 = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryID).child(bookID);
        }
        else {
            databaseReference2 = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryID).child(bookSubCategory).child(bookID);
        }
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookDesc.setText(dataSnapshot.child("BookDesc").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
