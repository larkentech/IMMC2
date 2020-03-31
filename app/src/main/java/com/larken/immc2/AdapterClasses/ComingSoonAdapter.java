package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.bumptech.glide.Glide;
import com.larken.immc2.DetailsContainerActivity;
import com.larken.immc2.Fragments.ComingSoonFragment;
import com.larken.immc2.ModalClasses.ComingSoonModal;
import com.larken.immc2.ModalClasses.OrderModal;
import com.larken.immc2.R;

import java.util.List;

public class ComingSoonAdapter extends ArrayAdapter<ComingSoonModal> {
    Fragment fragment;
    Context context;
    Activity activity;

    public ComingSoonAdapter(@NonNull Context context, int resource, @NonNull List<ComingSoonModal> objects) {
        super(context, resource, objects);
        this.context = context;

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
                ComingSoonFragment dialogFragment = new ComingSoonFragment();
                FragmentActivity activity = (FragmentActivity)context;
                FragmentManager manager = activity.getSupportFragmentManager();
                Bundle args = new Bundle();
                args.putString("ProductName",comingSoonModal.getProductName());
                args.putString("ProductImage",comingSoonModal.getProductImage());
                args.putString("ReleaseDate",comingSoonModal.getReleaseDate());
                args.putString("Description",comingSoonModal.getProductDesc());

                dialogFragment.setArguments(args);
                dialogFragment.show(manager,"Dialog");


            }
        });

        return convertView;







    }

}

