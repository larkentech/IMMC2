package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.larken.immc2.Fragments.CartFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.larken.immc2.ModalClasses.PaymentModal;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class CartAdapter extends ArrayAdapter<PaymentModal> {

    List<String> itemsCount;
    List<String> tempKeys;
    Fragment fragment;


    public CartAdapter(@NonNull Context context, int resource, @NonNull List<PaymentModal> objects, List<String> count, Fragment fragment) {
        super(context, resource, objects);
        this.tempKeys = count;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_cart_item, parent, false);
        }

        final PaymentModal modal = getItem(position);


        TextView bookName = convertView.findViewById(R.id.bookNameCart);
        TextView authorName = convertView.findViewById(R.id.bookDesignerCart);
        final TextView bookPrice = convertView.findViewById(R.id.bookPriceCart);
        final NumberPicker quantityPicker = convertView.findViewById(R.id.number_picker_cart);
        ImageView BookImage = convertView.findViewById(R.id.itemImageCart);
        Button deleteItem = convertView.findViewById(R.id.delete_cart);
        Glide
                .with(getContext())
                .load(modal.getCartImage())
                .centerCrop()
                .into(BookImage);

        bookName.setText(modal.getBookName());
        authorName.setText(modal.getBookCategory() + ", " + modal.getBookSubCategory());
        try {
            quantityPicker.setValue(Integer.parseInt(modal.getCount()));
            bookPrice.setText("Rs." + Integer.parseInt(modal.getSinglePrice()) * quantityPicker.getValue() + "/-");

        } catch (NumberFormatException ex) {
        }


        quantityPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                bookPrice.setText("Rs." + Integer.parseInt(modal.getSinglePrice()) * value + "/-");
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails")
                        .child(mAuth.getUid()).child("Cart");
                HashMap<String, Object> updatedMap = new HashMap<>();
                updatedMap.put("Count", String.valueOf(quantityPicker.getValue()));
                updatedMap.put("CartPrice", String.valueOf(Integer.parseInt(modal.getSinglePrice()) * value));
                databaseReference.child(tempKeys.get(position)).updateChildren(updatedMap);
                Log.v("TAG", "Count:" + String.valueOf(quantityPicker.getValue()));


            }
        });


        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails")
                        .child(mAuth.getUid()).child("Cart");

                databaseReference.child(tempKeys.get(position)).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toasty.error(getContext(), "Item Removed From Cart").show();

                        ((CartFragment) fragment).adapter.clear();
                        //  ((CartFragment)fragment).reloadData();

                    }
                });


            }
        });

        return convertView;

    }
}
