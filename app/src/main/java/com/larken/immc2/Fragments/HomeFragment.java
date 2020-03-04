package com.larken.immc2.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.AdapterClasses.OffersAdapter;
import com.larken.immc2.DetailsContainerActivity;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HListView offersListView;
    HListView featuredListView;
    HListView bestSellerListView;
    CardView engBooks;
    CardView quotesTheme;
    CardView scienceTheme;
    CardView mathsTheme;

    TextView bestSellingBtn;
    TextView featuredBtn;

    OffersAdapter adapter;
    BestSellingAdapter featuredAdapter;
    BestSellingAdapter bestSellingAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<String> featuredID;
    List<String> bestSellingID;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        featuredID = new ArrayList<>();
        bestSellingID = new ArrayList<>();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);

        quotesTheme = (CardView) view.findViewById(R.id.quotesTheme);

        offersListView = view.findViewById(R.id.offersList);
        featuredListView = view.findViewById(R.id.featuredList);
        bestSellerListView = view.findViewById(R.id.bestSellerList);

        List<BooksModal> imageList = new ArrayList<>();
        adapter = new OffersAdapter(getContext(),R.layout.offers_single,imageList);
        offersListView.setAdapter(adapter);

        //featurede model

        List<BooksModal> featuredModelList = new ArrayList<>();
        featuredAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,featuredModelList, featuredID);
        featuredListView.setAdapter(featuredAdapter);

        //Best Selling Model

        List<BooksModal> bestSellingModalList = new ArrayList<>();
        bestSellingAdapter = new BestSellingAdapter(getContext(),R.layout.best_seller_single,bestSellingModalList, bestSellingID);
        bestSellerListView.setAdapter(bestSellingAdapter);

        //Engineering Books

        //CS NoteBooks
        engBooks = (CardView) view.findViewById(R.id.engineeringBooks);
        scienceTheme = (CardView) view.findViewById(R.id.scienceTheme);
        mathsTheme = (CardView) view.findViewById(R.id.mathsTheme);
        bestSellingBtn = (TextView) view.findViewById(R.id.bestSellingBtn);
        featuredBtn = (TextView) view.findViewById(R.id.featuredBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Offers");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal csNoteBooksModal = dataSnapshot.getValue(BooksModal.class);
                adapter.add(csNoteBooksModal);
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

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("Featured");

        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal csNoteBooksModal = dataSnapshot.getValue(BooksModal.class);
                featuredAdapter.add(csNoteBooksModal);
                featuredID.add(dataSnapshot.getKey());
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

        DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("BestSelling");

        databaseReference3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal csNoteBooksModal = dataSnapshot.getValue(BooksModal.class);
                bestSellingAdapter.add(csNoteBooksModal);
                bestSellingID.add(dataSnapshot.getKey());
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

        engBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEnginneringbooks();

            }
        });

        quotesTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuoteSeries();
            }
        });

        scienceTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScienceTheme();
            }
        });

        mathsTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMathsTheme();
            }
        });

        bestSellingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBestSeelingBtn();
            }
        });

        featuredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeaturedBtn();
            }
        });

        /*

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference().child("BookDetails");
        HashMap<String,String> engMap = new HashMap<>();
        engMap.put("BookName","How to Crack Test of Arithmetic");
        engMap.put("BookDesigner","Hemanth");
        engMap.put("BookPrice","792");
        engMap.put("BookCategory","Maths");
        engMap.put("BookDesc","This is a reproduction of a classic text optimised for kindle devices. " +
                "We have endeavoured to create this version as close to the original artefact as possible.");
        engMap.put("BookImage","https://firebasestorage.googleapis.com/v0/b/iammc2-f61a0.appspot.com/o/Arithmemtic.jpg?alt=media&token=29e00124-c5ff-4eec-a237-479ab1b237de");
        databaseReference1.child("MathsTheme").child("ArithmeticNoteBooks").push().setValue(engMap);

         */







    }

    public void openEnginneringbooks(){

        Intent intent = new Intent(getContext(), DetailsContainerActivity.class);
        intent.putExtra("Category","Engineering");
        startActivity(intent);

    }

    public void openQuoteSeries(){
        Intent intent = new Intent(getContext(),DetailsContainerActivity.class);
        intent.putExtra("Category","Quotes");
        startActivity(intent);
    }

    public void openScienceTheme(){
        Intent intent = new Intent(getContext(),DetailsContainerActivity.class);
        intent.putExtra("Category","ScienceTheme");
        startActivity(intent);
    }

    public void openMathsTheme(){

        Intent intent = new Intent(getContext(),DetailsContainerActivity.class);
        intent.putExtra("Category","MathsTheme");
        startActivity(intent);

    }

    public void openBestSeelingBtn(){

        Intent intent = new Intent(getContext(),DetailsContainerActivity.class);
        intent.putExtra("Category","BestSelling");
        startActivity(intent);
    }

    public void openFeaturedBtn(){
        Intent intent = new Intent(getContext(),DetailsContainerActivity.class);
        intent.putExtra("Category","Featured");
        startActivity(intent);
    }

}
