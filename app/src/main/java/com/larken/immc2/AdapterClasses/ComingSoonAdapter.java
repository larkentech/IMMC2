package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.larken.immc2.DetailsContainerActivity;
import com.larken.immc2.ModalClasses.ComingSoonModal;
import com.larken.immc2.ModalClasses.OrderModal;
import com.larken.immc2.R;

import java.util.List;

public class ComingSoonAdapter extends ArrayAdapter<ComingSoonModal> {
    Fragment fragment;

    public ComingSoonAdapter(@NonNull Context context, int resource, @NonNull List<ComingSoonModal> objects) {
        super(context, resource, objects);

    }
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.coming_soon_single, parent, false);

        }
        final ComingSoonModal comingSoonModal = getItem(position);



        final ImageView displayImage = convertView.findViewById(R.id.coming_soon_single);
        Glide
                .with(getContext())
                .load(comingSoonModal.getDisplayImage())
                .centerCrop()
                .into(displayImage);

        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), DetailsContainerActivity.class);
                intent.putExtra("ProductName",comingSoonModal.getProductName());
                intent.putExtra("ReleaseDate",comingSoonModal.getReleaseDate());
                intent.putExtra("ProductImage",comingSoonModal.getProductImage());
                getContext().startActivity(intent);

            }
        });

        return convertView;







    }

}

