package com.example.immc2.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.immc2.AdapterClasses.CartAdapter;
import com.example.immc2.ModalClasses.BooksModal;
import com.example.immc2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    CartAdapter adapter;
    ListView cartListView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceBooks;
    Button proceedToPay;
    FirebaseAuth mAuth;

    List<String> bookId;
    List<String> bookCategoryId;
    List<String> bookSubCategoryId;
    List<String> itemsCount;
    int count1;
    int count2 = 0;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getUid());
        databaseReferenceBooks = firebaseDatabase.getReference().child("BookDetails");

        bookId = new ArrayList<>();
        bookCategoryId = new ArrayList<>();
        bookSubCategoryId = new ArrayList<>();
        itemsCount = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartListView = view.findViewById(R.id.cartListView);
        List<BooksModal> cartItems = new ArrayList<>();
        adapter = new CartAdapter(getContext(), R.layout.single_cart_item, cartItems,itemsCount);
        cartListView.setAdapter(adapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Cart")) {
                    count1 = (int) dataSnapshot.child("Cart").getChildrenCount();
                    for (DataSnapshot ds : dataSnapshot.child("Cart").getChildren()) {
                        String key = ds.getKey();
                        databaseReference.child("Cart").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                count2++;
                                bookId.add(dataSnapshot.child("BookId").getValue(String.class));
                                bookCategoryId.add(dataSnapshot.child("BookCategory").getValue(String.class));
                                bookSubCategoryId.add(dataSnapshot.child("BookSubCategory").getValue(String.class));
                                itemsCount.add(dataSnapshot.child("Count").getValue(String.class));
                                if (count1 == count2){
                                    Log.v("TAG", "BookID:" + bookId);
                                    displayCart(bookId,bookCategoryId,bookSubCategoryId,itemsCount);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                    //getBookDetails(bookId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayCart(List<String> bookId, List<String> bookCategoryId, List<String> bookSubCategoryId, List<String> itemsCount) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        for (int i=0;i<bookId.size();i++)
        {
            DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryId.get(i))
                    .child(bookSubCategoryId.get(i)).child(bookId.get(i));
            databaseReference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    adapter.add(modal);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getBookDetails(List<String> bookId) {

        for (int i = 0; i < bookId.size(); i++) {
            databaseReferenceBooks.child(bookCategoryId.get(i)).child(bookSubCategoryId.get(i)).child(bookId.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    adapter.add(modal);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
