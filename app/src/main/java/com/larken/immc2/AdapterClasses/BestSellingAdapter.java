package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.larken.immc2.BookDetailsActivity;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;

import java.util.List;

public class BestSellingAdapter extends ArrayAdapter<BooksModal> {

    List<String> bookID;

    public BestSellingAdapter(@NonNull Context context, int resource, @NonNull List<BooksModal> objects, List<String> bookId) {
        super(context, resource, objects);
        this.bookID = bookId;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.best_seller_single,parent,false);
        }
        final BooksModal bestSellingModal = getItem(position);


        CardView bestSellercard = convertView.findViewById(R.id.bestSellerCard);
        TextView BookPrice = convertView.findViewById(R.id.best_seller_price_single);
        TextView BooksName = convertView.findViewById(R.id.best_seller_bookName_single);
        TextView BookDesigner = convertView.findViewById(R.id.best_seller_bookDesc_single);
        final ImageView BookImage = convertView.findViewById(R.id.best_seller_image_single);
        Glide
                .with(getContext())
                .load(bestSellingModal.getBookImage())
                .centerCrop()
                .into(BookImage);

        if(bestSellingModal.getBookPrice160Pages().matches("0"))
        {
            BookPrice.setText("Out Of Stock");
            BookPrice.setTextColor(Color.parseColor("#FF0000"));
        }
        else
            BookPrice.setText("Rs."+bestSellingModal.getBookPrice160Pages()+"/-");
        BooksName.setText(bestSellingModal.getBookName());
        BookDesigner.setText("Designed By "+bestSellingModal.getBookDesigner());

        bestSellercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), BookDetailsActivity.class);
                i.putExtra("BookID",bestSellingModal.getBookID());
                i.putExtra("BookCategory",bestSellingModal.getBookCategory());
                i.putExtra("BookSubCategory",bestSellingModal.getBookSubCategory());
                getContext().startActivity(i);
                ((Activity)getContext()).finish();
            }
        });


        return convertView;
    }
}

