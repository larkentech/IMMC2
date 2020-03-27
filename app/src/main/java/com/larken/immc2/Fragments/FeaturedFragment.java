package com.larken.immc2.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;
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
public class FeaturedFragment extends Fragment {

    GridView gridView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BestSellingAdapter featuredAdapter;
    List<String> featuredID;

    String category;
    String subcategory;

    TextView sub_category_title;


    public FeaturedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        category = getArguments().getString("Category");
        subcategory = getArguments().getString("SubCategory");

        return inflater.inflate(R.layout.fragment_featured, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v("TAG","Category:"+category);
        Log.v("TAG","SubCategory:"+subcategory);

        final RelativeLayout noBooksFound = view.findViewById(R.id.featuredFragmentAnimation);
        final LinearLayout featuredFragmentLL = view.findViewById(R.id.featuredFragmentLL);


        sub_category_title = (TextView) view.findViewById(R.id.sub_category_title);

        featuredID = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.featuredGridView);
        List<BooksModal> booksModal = new ArrayList<>();
        featuredAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,booksModal,featuredID);
        gridView.setAdapter(featuredAdapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BookDetails");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(category))
                {
                    databaseReference.child(category).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(subcategory))
                            {
                                databaseReference.child(category).child(subcategory).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        BooksModal featuredBooks = dataSnapshot.getValue(BooksModal.class);
                                        featuredAdapter.add(featuredBooks);
                                        sub_category_title.setText(subcategory);
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
                            else {
                                noBooksFound.setVisibility(View.VISIBLE);
                                featuredFragmentLL.setVisibility(View.GONE);
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    noBooksFound.setVisibility(View.VISIBLE);
                    featuredFragmentLL.setVisibility(View.GONE);
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
