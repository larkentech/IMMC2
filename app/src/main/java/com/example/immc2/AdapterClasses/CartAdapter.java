package com.example.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.immc2.ModalClasses.BooksModal;
import com.example.immc2.R;

import java.text.ParsePosition;
import java.util.List;

public class CartAdapter extends ArrayAdapter<BooksModal> {

    List<String> itemsCount;


    public CartAdapter(@NonNull Context context, int resource, @NonNull List<BooksModal> objects, List<String> itemsCount) {
        super(context, resource, objects);
        this.itemsCount = itemsCount;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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

        quantityPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                try{
                    bookPrice.setText("Rs."+Integer.parseInt(modal.getBookPrice())*value+"/-");
                }catch (Exception e)
                {
                    Log.v("TAG",e.getMessage());
                }
            }
        });



        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;

    }
}
