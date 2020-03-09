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
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.AdapterClasses.OffersAdapter;
import com.larken.immc2.AdapterClasses.SubCategoryAdapter;
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
    HListView quotesListView;
    HListView scienceListView;
    CardView engBooks;
    CardView quotesTheme;
    CardView scienceTheme;
    CardView mathsTheme;

    TextView bestSellingBtn;
    TextView featuredBtn;

    OffersAdapter adapter;
    SubCategoryAdapter quotesAdapter;
    SubCategoryAdapter scienceAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<String> featuredID;
    List<String> bestSellingID;

    String category;
    List<String> subcategory;
    List<String> subcategoryImages;

    //Quotes Theme
    List<String>  subcategoryQuotes;
    List<String> quotesList;

    //Science Theme
    List<String> scienceSubcategoryList;
    List<String> scienceList;

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

        subcategory = new ArrayList<>();
        subcategoryImages = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        engBooks = (CardView) view.findViewById(R.id.engineeringBooks);
        scienceTheme = (CardView) view.findViewById(R.id.scienceTheme);
        mathsTheme = (CardView) view.findViewById(R.id.mathsTheme);
        bestSellingBtn = (TextView) view.findViewById(R.id.bestSellingBtn);
        featuredBtn = (TextView) view.findViewById(R.id.featuredBtn);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);

        quotesTheme = (CardView) view.findViewById(R.id.quotesTheme);

        offersListView = view.findViewById(R.id.offersList);
        quotesListView = view.findViewById(R.id.quotesList);
        scienceListView = view.findViewById(R.id.scienceList);

        List<BooksModal> imageList = new ArrayList<>();
        adapter = new OffersAdapter(getContext(),R.layout.offers_single,imageList);
        offersListView.setAdapter(adapter);
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


        //QuotesTheme model
        quotesList = new ArrayList<>();
        subcategoryQuotes = new ArrayList<>();
        List<BooksModal> dummyList = new ArrayList<>();
        category = "QuoteTheme";
        quotesAdapter = new SubCategoryAdapter(getContext(),R.layout.single_subcategory,dummyList,category,quotesList,subcategoryQuotes);
        quotesListView.setAdapter(quotesAdapter);
        //Quotes Theme
        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("CategoryImages").child("QuoteTheme");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    quotesAdapter.add(modal);
                    subcategoryQuotes.add(ds.getKey());
                    quotesList.add(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Science Model
        scienceList = new ArrayList<>();
        scienceSubcategoryList = new ArrayList<>();
        List<BooksModal> dummyList1 = new ArrayList<>();
        String category1 = "ScienceTheme";
        scienceAdapter = new SubCategoryAdapter(getContext(),R.layout.single_subcategory,dummyList1, category1,scienceList,scienceSubcategoryList);
        scienceListView.setAdapter(scienceAdapter);
        //Science Theme
        DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("CategoryImages").child("ScienceTheme");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    scienceAdapter.add(modal);
                    scienceSubcategoryList.add(ds.getKey());
                    scienceList.add(ds.getValue().toString());
                }
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
