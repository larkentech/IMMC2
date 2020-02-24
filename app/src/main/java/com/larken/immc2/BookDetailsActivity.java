package com.larken.immc2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.kodmap.app.library.PopopDialogBuilder;
import com.larken.immc2.Fragments.IntroductionFragment;
import com.larken.immc2.Fragments.ReviewFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView bookName;
    TextView bookAuthor;
    TextView bookPrice;
    TextView bookPriceIncrement;
    TextView bookCategory;
    RatingBar bookRatings;
    ImageView bookImage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public String bookID;
    public String bookCategoryID;
    public String bookSubCategoryID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        final List<String> url_list = new ArrayList<>();




        bookID = getIntent().getExtras().getString("BookID");
        bookCategoryID = getIntent().getExtras().getString("BookCategory");
        bookSubCategoryID = getIntent().getExtras().getString("BookSubCategory");
        bookImage = findViewById(R.id.selected_book_image);

        bookName = findViewById(R.id.selected_book_name);
        bookAuthor = findViewById(R.id.selected_book_designer);
        bookPrice = findViewById(R.id.selected_book_price);
        bookPriceIncrement = findViewById(R.id.book_offer_price);
        bookCategory = findViewById(R.id.selected_book_category);



        //bookRatings = findViewById(R.id.ratingBar);
        //bookRatings.setEnabled(false);





        firebaseDatabase = FirebaseDatabase.getInstance();
        if (bookSubCategoryID != null)
        {
            databaseReference = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryID).child(bookSubCategoryID).child(bookID);
        }
        else {
            databaseReference = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryID).child(bookID);
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookName.setText(dataSnapshot.child("BookName").getValue(String.class));
                bookAuthor.setText("Designed By: "+dataSnapshot.child("BookDesigner").getValue(String.class));
                bookPrice.setText("Rs."+dataSnapshot.child("BookPrice").getValue(String.class)+"/-");
                bookCategory.setText("Category: "+dataSnapshot.child("BookSubCategory").getValue(String.class));
                Glide
                        .with(getApplicationContext())
                        .load(dataSnapshot.child("BookImage").getValue(String.class))
                        .centerCrop()
                        .into(bookImage);


                for (DataSnapshot ds:dataSnapshot.child("BookImages").getChildren())
                {
                    url_list.add(ds.getValue(String.class));
                }

                String value = dataSnapshot.child("BookPrice").getValue(String.class);
                String i ="0.2";
                double value1 = Double.parseDouble(value) + ( Double.parseDouble(value) * Double.parseDouble(i)) ;
                int value2 = (int)value1;
                String value3 = Integer.toString(Math.round(value2));

                bookPriceIncrement.setText("Rs."+value3+"/-");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dialog dialog = new PopopDialogBuilder(BookDetailsActivity.this)
                        // Set list like as option1 or option2 or option3
                        .setList(url_list,0)
                        // or setList with initial position that like .setList(list,position)
                        // Set dialog header color
                        .setHeaderBackgroundColor(android.R.color.white)
                        // Set dialog background color
                        .setDialogBackgroundColor(R.color.color_dialog_bg)
                        // Set close icon drawable
                        .setCloseDrawable(R.drawable.ic_close_black_24dp)
                        // Set loading view for pager image and preview image
                        .setLoadingView(R.layout.loading_view)
                        // Set dialog style
                        .setDialogStyle(R.style.DialogStyle)
                        // Choose selector type, indicator or thumbnail
                        .showThumbSlider(true)
                        // Set image scale type for slider image
                        .setSliderImageScaleType(ImageView.ScaleType.FIT_XY)
                        // Set indicator drawable
                        .setSelectorIndicator(R.drawable.sample_indicator_selector)
                        // Enable or disable zoomable
                        .setIsZoomable(true)
                        // Build Km Slider Popup Dialog
                        .build();
                dialog.show();

            }
        });



        FragmentManager manager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerDetails);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new IntroductionFragment(), "Introduction");
        adapter.addFragment(new ReviewFragment(), "Review");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }


}
