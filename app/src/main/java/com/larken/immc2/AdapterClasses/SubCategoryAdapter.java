package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.larken.immc2.DetailsContainerActivity;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;

import java.util.List;

public class SubCategoryAdapter extends ArrayAdapter<BooksModal> {

    List<String> imagesUrl;
    String category;
    List<String> subcategory;

    public SubCategoryAdapter(@NonNull Context context, int resource, @NonNull List<BooksModal> objects, String category, @NonNull List<String> objects1, @NonNull List<String> objects2) {
        super(context, resource, objects);
        imagesUrl = objects1;
        this.category = category;
        this.subcategory = objects2;
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.single_subcategory,parent,false);
        }

        ImageView categoryImag = (ImageView)convertView.findViewById(R.id.single_subcategory_image);
        Glide
                .with(getContext())
                .load(imagesUrl.get(position))
                .centerCrop()
                .into(categoryImag);

        categoryImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetailsContainerActivity.class);
                intent.putExtra("Category",category);
                intent.putExtra("SubCategory",subcategory.get(position));
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
