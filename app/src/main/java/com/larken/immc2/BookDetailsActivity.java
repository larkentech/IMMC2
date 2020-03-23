package com.larken.immc2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.firebase.auth.FirebaseAuth;
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
import com.travijuu.numberpicker.library.NumberPicker;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class BookDetailsActivity extends AppCompatActivity {

    TextView bookName;
    TextView bookAuthor;
    TextView bookPrice;
    TextView bookPriceIncrement;
    TextView bookCategory;
    TextView bookDesc;
    SliderView bookImage;

    TextView addToCart;
    int count;
    NumberPicker numberPicker;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;


    public String bookID;
    public String bookCategoryID;
    public String bookSubCategoryID;

    String _160pages;
    String _200pages;
    String _240pages;



    String singleBookPrice;

    CardView _160pagescard;
    CardView _200pagescard;
    CardView _240pagescard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        mAuth = FirebaseAuth.getInstance();

        final List<String> url_list = new ArrayList<>();

        bookID = getIntent().getExtras().getString("BookID");
        bookCategoryID = getIntent().getExtras().getString("BookCategory");
        bookSubCategoryID = getIntent().getExtras().getString("BookSubCategory");
        bookImage = findViewById(R.id.imageSlider);
        final ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(this);
        bookImage.setSliderAdapter(sliderAdapter);
        numberPicker = findViewById(R.id.number_picker);
        bookDesc = findViewById(R.id.selected_book_desc);

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

                _160pages =  dataSnapshot.child("BookPrice160Pages").getValue(String.class) ;
                _200pages =  dataSnapshot.child("BookPrice200Pages").getValue(String.class);
                _240pages =  dataSnapshot.child("BookPrice240Pages").getValue(String.class);
                singleBookPrice = _160pages;
                bookName.setText(dataSnapshot.child("BookName").getValue(String.class));
                bookAuthor.setText("Designed By: " + dataSnapshot.child("BookDesigner").getValue(String.class));
                bookPrice.setText("Rs." +_160pages+ "/-");
                bookCategory.setText("Category: " + dataSnapshot.child("BookSubCategory").getValue(String.class));
                bookDesc.setText(dataSnapshot.child("BookDesc").getValue(String.class));

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


        addToCart = findViewById(R.id.addtocart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = numberPicker.getValue();

                if (count <= 0)
                {
                    Toasty.error(BookDetailsActivity.this,"Select Quantity").show();
                }
                else {
                    HashMap<String,String> cartMap = new HashMap<>();
                    cartMap.put("Count",Integer.toString(count));
                    cartMap.put("BookID",bookID);
                    cartMap.put("BookCategory",bookCategoryID);
                    cartMap.put("BookSubCategory",bookSubCategoryID);
                    cartMap.put("CartPrice",String.valueOf(count*(Integer.parseInt(singleBookPrice))));

                    DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getCurrentUser().getUid());
                    databaseReference3.child("Cart").push().setValue(cartMap);
                    addToCart.setBackgroundColor(getResources().getColor(R.color.finalColor));
                    addToCart.setText("Added");
                    Drawable image = addToCart.getContext().getDrawable(R.drawable.add_to_cart_check);
                    addToCart.setCompoundDrawablesWithIntrinsicBounds(image,null,null,null);
                    Toasty.success(BookDetailsActivity.this, "Successfully added to the cart", Toast.LENGTH_SHORT, true).show();

                }

            }
        });

        _160pagescard = findViewById(R.id.pages_160_card);
        _200pagescard = findViewById(R.id.pages_200_card);
        _240pagescard = findViewById(R.id.pages_240_card);

        _160pagescard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _200pagescard.setCardElevation(0);
                _240pagescard.setCardElevation(0);
                _200pagescard.setCardBackgroundColor( Color.parseColor("#F7F7F7"));
                _240pagescard.setCardBackgroundColor( Color.parseColor("#F7F7F7"));
                _200pagescard.setBackground( null);
                _240pagescard.setBackground( null);
                _160pagescard.setBackground(getDrawable(R.drawable.select_pages_shadow));
                singleBookPrice  =_160pages;
                bookPrice.setText("Rs." +_160pages+ "/-");

            }
        });

        _200pagescard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _160pagescard.setCardElevation(0);
                _240pagescard.setCardElevation(0);
                _160pagescard.setCardBackgroundColor(Color.parseColor("#F7F7F7"));
                _240pagescard.setCardBackgroundColor(Color.parseColor("#F7F7F7"));
                _160pagescard.setBackground( null);
                _240pagescard.setBackground( null);
                _200pagescard.setBackground(getDrawable(R.drawable.select_pages_shadow));
                singleBookPrice = _200pages;
                bookPrice.setText("Rs." +_200pages+ "/-");
            }
        });

        _240pagescard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _200pagescard.setCardElevation(0);
                _160pagescard.setCardElevation(0);
                _160pagescard.setCardBackgroundColor(Color.LTGRAY);
                _200pagescard.setCardBackgroundColor(Color.LTGRAY);
                _200pagescard.setBackground( null);
                _160pagescard.setBackground( null);
                _240pagescard.setBackground(getDrawable(R.drawable.select_pages_shadow));
                singleBookPrice = _240pages;
                bookPrice.setText("Rs." +_240pages+ "/-");


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
