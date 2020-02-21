package com.larken.immc2.Fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.BookDetailsActivity;
import com.larken.immc2.ModalClasses.BooksModal;

import com.larken.immc2.R;


import android.widget.Button;
import com.travijuu.numberpicker.library.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
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
    Button addToCart;
    FirebaseAuth mAuth;
    int count;


    public IntroductionFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       final String bookID = ((BookDetailsActivity)getActivity()).bookID;
       final String bookSubCategory = ((BookDetailsActivity)getActivity()).bookSubCategoryID;
       final String bookCategoryID = ((BookDetailsActivity)getActivity()).bookCategoryID;

        bookDesc = view.findViewById(R.id.selected_book_desc);
        bestSellingListView = view.findViewById(R.id.best_selling_list);
        List<BooksModal> bestSellingList = new ArrayList<>();
        final List<String> bestSellingIDs = new ArrayList<>();
        bestSellingAdapter = new BestSellingAdapter(getContext(),R.layout.best_seller_single,bestSellingList,bestSellingIDs);
        bestSellingListView.setAdapter(bestSellingAdapter);

        //Number Picker
        numberPicker = view.findViewById(R.id.number_picker);

        firebaseDatabase = FirebaseDatabase.getInstance();
        //Setting BestSelling List
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

        //Getting Book Details
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

        addToCart = view.findViewById(R.id.addtocart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count = numberPicker.getValue();

                if (count <= 0)
                {
                    Toasty.error(getContext(),"Select Quantity").show();
                }
                else {
                    HashMap<String,String> cartMap = new HashMap<>();
                    cartMap.put("Count",Integer.toString(count));
                    cartMap.put("BookId",bookID);
                    cartMap.put("BookCategory",bookCategoryID);
                    cartMap.put("BookSubCategory",bookSubCategory);

                    DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getCurrentUser().getUid());
                    databaseReference3.child("Cart").push().setValue(cartMap);
                    addToCart.setBackgroundColor(getResources().getColor(R.color.finalColor));
                    addToCart.setText("Added");
                    Drawable image = addToCart.getContext().getDrawable(R.drawable.add_to_cart_check);
                    addToCart.setCompoundDrawablesWithIntrinsicBounds(image,null,null,null);
                    Toasty.success(getContext(), "Successfully added to the cart", Toast.LENGTH_SHORT, true).show();

                }


            }
        });


    }
}
