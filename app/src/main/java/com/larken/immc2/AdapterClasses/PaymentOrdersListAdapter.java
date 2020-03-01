package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.larken.immc2.Fragments.CartFragment;
import com.larken.immc2.ModalClasses.BooksModal;

import com.larken.immc2.PaymentActivity;
import com.larken.immc2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class PaymentOrdersListAdapter extends ArrayAdapter<BooksModal> {
    List<String> itemsCount;
    List<String> tempKeys;
    Fragment fragment;
    public int finalPrice;



    public PaymentOrdersListAdapter(@NonNull Context context, int resource, @NonNull List<BooksModal> objects, List<String> itemsCount,List<String> count) {
        super(context, resource, objects);
        this.itemsCount = itemsCount;
        this.tempKeys = count;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.single_cart_item,parent,false);
        }



        final BooksModal modal = getItem(position);


        TextView bookName = convertView.findViewById(R.id.bookNameCart);
        TextView authorName = convertView.findViewById(R.id.bookDesignerCart);
        final TextView bookPrice = convertView.findViewById(R.id.bookPriceCart);
        NumberPicker quantityPicker = convertView.findViewById(R.id.number_picker_cart);
        ImageView BookImage = convertView.findViewById(R.id.itemImageCart);
        Button deleteItem = convertView.findViewById(R.id.delete_cart);
        Glide
                .with(getContext())
                .load(modal.getBookImage())
                .centerCrop()
                .into(BookImage);

        bookName.setText(modal.getBookName());
        authorName.setText(modal.getBookDesigner());
        quantityPicker.setValue(Integer.parseInt(itemsCount.get(position).toString()));
        bookPrice.setText("Rs."+Integer.parseInt(modal.getBookPrice())*quantityPicker.getValue()+"/-");

        final List<String> tempList = new ArrayList();
        for(int i=0;i<itemsCount.size();i++){
            //View view=listView.getChildAt(i);
            tempList.add(i,bookPrice.getText().toString());
            Log.v("TAG","Price:"+tempList);

        }

        final View finalConvertView = convertView;
        quantityPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                try{
                    bookPrice.setText("Rs."+Integer.parseInt(modal.getBookPrice())*value+"/-");
                    ((PaymentActivity)getContext()).displayFinalPrice();
                }catch (Exception e)
                {
                    Log.v("TAG",e.getMessage());
                }
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
                        Toasty.error(getContext(),"Item Removed From Cart").show();
                        ((CartFragment)fragment).adapter.clear();
                        ((CartFragment)fragment).reloadData();

                    }
                });
            }
        });

        return convertView;

    }

}
