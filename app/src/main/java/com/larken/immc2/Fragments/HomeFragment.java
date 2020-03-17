package com.larken.immc2.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
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
    HListView engineeringListView;
    HListView mathsListView;
    CardView engBooks;
    CardView quotesTheme;
    CardView scienceTheme;
    CardView mathsTheme;

    TextView bestSellingBtn;


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

    //engineering Theme
    List<String> engineeringSubcategoryList;
    List<String> engineeringList;
    SubCategoryAdapter engineeringAdapter;

    //maths Theme
    List<String> mathsSubcategoryList;
    List<String> mathsList;
    SubCategoryAdapter mathsAdapter;

    ShimmerFrameLayout homeShimmer;
    ScrollView homeSV;


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

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);

        homeSV=view.findViewById(R.id.homeSV);
        homeShimmer = view.findViewById(R.id.shimmer_view_home);
        homeShimmer.startShimmer();


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

                homeShimmer.stopShimmer();
                homeShimmer.setVisibility(View.GONE);
                homeSV.setVisibility(View.VISIBLE);
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

        //Engineering Model
        engineeringListView = view.findViewById(R.id.engineeringList);
        engineeringList = new ArrayList<>();
        engineeringSubcategoryList = new ArrayList<>();
        List<BooksModal> dummyList2 = new ArrayList<>();
        String category2 = "Engineering";
        engineeringAdapter = new SubCategoryAdapter(getContext(),R.layout.single_subcategory,dummyList2, category2,engineeringList,engineeringSubcategoryList);
        engineeringListView.setAdapter(engineeringAdapter);
        //Science Theme
        DatabaseReference databaseReference4 = firebaseDatabase.getReference().child("CategoryImages").child("Engineering");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    engineeringAdapter.add(modal);
                    engineeringSubcategoryList.add(ds.getKey());
                    engineeringList.add(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //maths Model
        mathsListView = view.findViewById(R.id.mathsList);
        mathsList = new ArrayList<>();
        mathsSubcategoryList = new ArrayList<>();
        List<BooksModal> dummyList3 = new ArrayList<>();
        String category3 = "MathsTheme";
        mathsAdapter = new SubCategoryAdapter(getContext(),R.layout.single_subcategory,dummyList3, category3,mathsList,mathsSubcategoryList);
        mathsListView.setAdapter(engineeringAdapter);
        //Science Theme
        DatabaseReference databaseReference5 = firebaseDatabase.getReference().child("CategoryImages").child("MathsTheme");
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    mathsAdapter.add(modal);
                    mathsSubcategoryList.add(ds.getKey());
                    mathsList.add(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
