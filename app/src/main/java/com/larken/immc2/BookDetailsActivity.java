package com.larken.immc2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.travijuu.numberpicker.library.NumberPicker;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class BookDetailsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

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

    String[] pages = { "160", "200", "240"};

    SliderView sliderView;
    ImageSliderAdapter sliderAdapter;

    //From the fragment
    private String BookDesc;
    public String[] keysURL=new String[100];
    public int k=0;
    Button add;
    FirebaseDatabase firebaseDatabaseFrag;
    DatabaseReference databaseReferenceFrag;
    TextView addToCart;
    TextView bookDesc;
    FirebaseAuth mAuth;
    int count;
    NumberPicker numberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_layout);

        bookID = getIntent().getExtras().getString("BookID");
        bookCategoryID = getIntent().getExtras().getString("BookCategory");
        bookSubCategoryID = getIntent().getExtras().getString("BookSubCategory");

        bookName = findViewById(R.id.selected_book_name);
        bookAuthor = findViewById(R.id.selected_book_designer);
        bookPrice = findViewById(R.id.selected_book_price);
        bookPriceIncrement = findViewById(R.id.book_offer_price);
        bookCategory = findViewById(R.id.selected_book_category);

        //SliderView
        sliderView = findViewById(R.id.imageSlider);
        final List<String> url_list = new ArrayList<>();
        sliderAdapter = new ImageSliderAdapter(this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

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
                for (DataSnapshot ds:dataSnapshot.child("BookImages").getChildren())
                {
                    url_list.add(ds.getValue(String.class));
                    sliderAdapter.addItem(ds.getValue(String.class));
                }

                String value = dataSnapshot.child("BookPrice").getValue(String.class);
                String i ="0.2";
                double value1 = Double.parseDouble(value) + ( Double.parseDouble(value) * Double.parseDouble(i)) ;
                int value2 = (int)value1;
                String value3 = Integer.toString(Math.round(value2));

                //bookPriceIncrement.setText("Rs."+value3+"/-");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      /*

        bookName = findViewById(R.id.selected_book_name);
        bookAuthor = findViewById(R.id.selected_book_designer);
        bookPrice = findViewById(R.id.selected_book_price);
        bookPriceIncrement = findViewById(R.id.book_offer_price);
        bookCategory = findViewById(R.id.selected_book_category);
        //bookRatings = findViewById(R.id.ratingBar);
        //bookRatings.setEnabled(false);

        Spinner spin = (Spinner) findViewById(R.id.spinner_pages);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pages);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);






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
        tabLayout.setupWithViewPager(viewPager); */

      //From the fragment
        mAuth = FirebaseAuth.getInstance();
        bookDesc = findViewById(R.id.selected_book_desc);
        numberPicker = findViewById(R.id.number_picker);
        firebaseDatabaseFrag = FirebaseDatabase.getInstance();
        //Getting Book Details
        if (bookSubCategoryID == null)
        {
            databaseReferenceFrag = firebaseDatabaseFrag.getReference().child("BookDetails").child(bookCategoryID).child(bookID);
        }
        else {
            databaseReferenceFrag = firebaseDatabaseFrag.getReference().child("BookDetails").child(bookCategoryID).child(bookSubCategoryID).child(bookID);
        }
        databaseReferenceFrag.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookDesc.setText(dataSnapshot.child("BookDesc").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addToCart = findViewById(R.id.addtocart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count = numberPicker.getValue();

                if (count <= 0)
                {
                    Toasty.error(BookDetailsActivity.this,"Select Quantity").show();
                }
                else {
                    HashMap<String,String> cartMap = new HashMap<>();
                    cartMap.put("Count",Integer.toString(count));
                    cartMap.put("BookId",bookID);
                    cartMap.put("BookCategory",bookCategoryID);
                    cartMap.put("BookSubCategory",bookSubCategoryID);

                    DatabaseReference databaseReference3 = firebaseDatabaseFrag.getReference().child("UserDetails").child(mAuth.getCurrentUser().getUid());
                    databaseReference3.child("Cart").push().setValue(cartMap);
                    addToCart.setBackgroundColor(getResources().getColor(R.color.finalColor));
                    addToCart.setText("Added");
                    Drawable image = addToCart.getContext().getDrawable(R.drawable.add_to_cart_check);
                    addToCart.setCompoundDrawablesWithIntrinsicBounds(image,null,null,null);
                    Toasty.success(BookDetailsActivity.this, "Successfully added to the cart", Toast.LENGTH_SHORT, true).show();

                }


            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new IntroductionFragment(), "Introduction");
        adapter.addFragment(new ReviewFragment(), "Review");
        viewPager.setAdapter(adapter);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),pages[position]+" Page Book", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
