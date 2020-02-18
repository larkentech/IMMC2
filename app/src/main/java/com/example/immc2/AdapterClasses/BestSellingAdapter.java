package com.example.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.immc2.BookDetailsActivity;
import com.example.immc2.ModalClasses.BooksModal;
import com.example.immc2.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

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
        ImageView BookImage = convertView.findViewById(R.id.best_seller_image_single);
        Glide
                .with(getContext())
                .load(bestSellingModal.getBookImage())
                .centerCrop()
                .into(BookImage);

        BookPrice.setText("Rs."+bestSellingModal.getBookPrice()+"/-");
        BooksName.setText(bestSellingModal.getBookName());

        bestSellercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), BookDetailsActivity.class);
                i.putExtra("BookID",bookID.get(position));
                i.putExtra("BookCategory",bestSellingModal.getBookCategory());
                i.putExtra("BookSubCategory",bestSellingModal.getBookSubCategory());
                getContext().startActivity(i);
            }
        });

        return convertView;
    }
}

