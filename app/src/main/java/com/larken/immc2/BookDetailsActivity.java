package com.larken.immc2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.kodmap.app.library.PopopDialogBuilder;
import com.larken.immc2.AdapterClasses.ImageSliderAdapter;
import com.larken.immc2.Fragments.IntroductionFragment;
import com.larken.immc2.Fragments.ReviewFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.ModalClasses.BooksModal;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;
import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    TextView bookName;
    TextView bookAuthor;
    TextView bookPrice;
    TextView bookPriceIncrement;
    TextView bookCategory;
    RatingBar bookRatings;
    SliderView bookImage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public String bookID;
    public String bookCategoryID;
    public String bookSubCategoryID;

    String _160pages;
    String _200pages;
    String _240pages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        final List<String> url_list = new ArrayList<>();

        bookID = getIntent().getExtras().getString("BookID");
        bookCategoryID = getIntent().getExtras().getString("BookCategory");
        bookSubCategoryID = getIntent().getExtras().getString("BookSubCategory");
        bookImage = findViewById(R.id.imageSlider);
        final ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(this);
        bookImage.setSliderAdapter(sliderAdapter);

        Log.v("BookDetails", "BookID:" + bookID);
        Log.v("BookDetails", "BookCategory:" + bookCategoryID);
        Log.v("BookDetails", "BookSubcategory:" + bookSubCategoryID);

        bookName = findViewById(R.id.selected_book_name);
        bookAuthor = findViewById(R.id.selected_book_designer);
        bookPrice = findViewById(R.id.selected_book_price);
        bookPriceIncrement = findViewById(R.id.book_offer_price);
        bookCategory = findViewById(R.id.selected_book_category);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryID).child(bookSubCategoryID).child(bookID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                _160pages = "Rs." + dataSnapshot.child("BookPrice160Pages").getValue(String.class) + "/-";
                bookName.setText(dataSnapshot.child("BookName").getValue(String.class));
                bookAuthor.setText("Designed By: " + dataSnapshot.child("BookDesigner").getValue(String.class));
                bookPrice.setText(_160pages);
                bookCategory.setText("Category: " + dataSnapshot.child("BookSubCategory").getValue(String.class));

                for (DataSnapshot ds : dataSnapshot.child("BookImages").getChildren()) {
                    url_list.add(ds.getValue(String.class));
                    sliderAdapter.addItem(ds.getValue(String.class));
                }

                String value = dataSnapshot.child("BookPrice160Pages").getValue(String.class);
                Log.v("BookDetails", "BookPrice:" + value);
                String i ="0.2";
                double value1 = Double.parseDouble(value) + ( Double.parseDouble(value) * Double.parseDouble(i)) ;
                int value2 = (int)value1;
                String value3 = Integer.toString(Math.round(value2));

                bookPriceIncrement.setText("Rs."+value3+"/-");

                //bookCategory.setText("Category: "+modal.getBookCategory());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}
