package com.larken.immc2.AdapterClasses;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.larken.immc2.DetailsContainerActivity;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.ModalClasses.OffersModal;
import com.larken.immc2.R;

import java.util.List;

public class OffersAdapter extends ArrayAdapter<OffersModal> {



    public OffersAdapter(@NonNull Context context, int resource, @NonNull List<OffersModal> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)


        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.offers_single,parent,false);
        }



        final OffersModal modal = getItem(position);
        final ImageView offersImage = convertView.findViewById(R.id.offer_image_single);
        Glide
                .with(getContext())
                .load(modal.getOfferImage())
                .centerCrop()
                .into(offersImage);



        offersImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailsContainerActivity.class);
                intent.putExtra("Category",modal.getOfferCategory());
                intent.putExtra("SubCategory",modal.getOfferSubCategory());
                getContext().startActivity(intent);

            }
        });


        return convertView;

    }
}
