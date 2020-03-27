package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

public class MainAdapter extends ArrayAdapter<BooksModal> {

    List<String> bookCategories;
    List<String> bookSubcategories;

    @Override
    public int getCount() {
        return bookCategories.size();
    }

    public MainAdapter(@NonNull Context context, int resource, @NonNull List<BooksModal> objects, List<String> Categories, List<String> Subcategories) {
        super(context, resource, objects);
        this.bookCategories = Categories;
        this.bookSubcategories = Subcategories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.singl_book_list,parent,false);
        }

        TextView categoryHeading = convertView.findViewById(R.id.singleMainCategoryHeadingTV);
        categoryHeading.setText(bookCategories.get(position));

        HListView subcategoryList = convertView.findViewById(R.id.singleMainCategoryList);
        List<BooksModal> dummyList2 = new ArrayList<>();
        final List<String> engineeringList = new ArrayList<>();
        final List<String> engineeringSubcategoryList = new ArrayList<>();
        final SubCategoryAdapter engineeringAdapter = new SubCategoryAdapter(getContext(),R.layout.single_subcategory,dummyList2, bookCategories.get(position),engineeringList,engineeringSubcategoryList);
        subcategoryList.setAdapter(engineeringAdapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference4 = firebaseDatabase.getReference().child("CategoryImages").child(bookCategories.get(position));
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {

                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    engineeringAdapter.add(modal);
                    engineeringSubcategoryList.add(ds.getKey());
                    engineeringList.add(ds.getValue().toString());
                    Log.v("MainAdapter","Url:"+engineeringList.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return convertView;
    }
}
