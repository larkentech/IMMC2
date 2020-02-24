package com.larken.immc2.AdapterClasses;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;

import java.util.List;

public class OffersAdapter extends ArrayAdapter<BooksModal> {



    public OffersAdapter(@NonNull Context context, int resource, @NonNull List<BooksModal> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)


        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.offers_single,parent,false);
        }



        BooksModal modal = getItem(position);
        ImageView offersImage = convertView.findViewById(R.id.offer_image_single);
        Glide
                .with(getContext())
                .load(modal.getBookImage())
                .centerCrop()
                .into(offersImage);



        offersImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Hello Image:"+position,Toast.LENGTH_LONG).show();

            }
        });


        return convertView;

    }
}
