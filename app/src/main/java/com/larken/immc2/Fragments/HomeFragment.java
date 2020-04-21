package com.larken.immc2.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.AdapterClasses.ActivityTrackerSubAdapter;
import com.larken.immc2.AdapterClasses.ComingSoonAdapter;
import com.larken.immc2.AdapterClasses.ImageSliderAdapter;
import com.larken.immc2.AdapterClasses.MainAdapter;
import com.larken.immc2.AdapterClasses.OfferSliderAdapter;
import com.larken.immc2.AdapterClasses.OffersAdapter;
import com.larken.immc2.AdapterClasses.SubCategoryAdapter;
import com.larken.immc2.HelperClasses.NonScrollListView;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.ModalClasses.ComingSoonModal;
import com.larken.immc2.ModalClasses.OffersModal;
import com.larken.immc2.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    SliderView offersListView;
    HListView comingSoonListView;
    HListView quotesListView;
    HListView scienceListView;
    HListView engineeringListView;
    HListView mathsListView;
    CardView engBooks;
    CardView quotesTheme;
    CardView scienceTheme;
    CardView mathsTheme;

    TextView bestSellingBtn;


    OffersAdapter adapter;
    ComingSoonAdapter comingSoonAdapter;
    SubCategoryAdapter quotesAdapter;
    SubCategoryAdapter scienceAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference comingSoonref;

    List<String> featuredID;
    List<String> bestSellingID;

    String category;
    List<String> subcategory;
    List<String> subcategoryImages;

    //Quotes Theme
    List<String>  subcategoryQuotes;
    List<String> quotesList;

    //Science Theme
    List<String> scienceSubcategoryList;
    List<String> scienceList;

    //engineering Theme
    List<String> engineeringSubcategoryList;
    List<String> engineeringList;
    SubCategoryAdapter engineeringAdapter;

    //maths Theme
    List<String> mathsSubcategoryList;
    List<String> mathsList;
    SubCategoryAdapter mathsAdapter;

    ShimmerFrameLayout homeShimmer;
    ScrollView homeSV;

    //Activity Trackers
   HListView trackerList;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subcategory = new ArrayList<>();
        subcategoryImages = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);

        homeSV=view.findViewById(R.id.homeSV);
        homeShimmer = view.findViewById(R.id.shimmer_view_home);
        homeShimmer.startShimmer();


        offersListView = view.findViewById(R.id.offerSlider);
        SliderView bookImage = view.findViewById(R.id.offerSlider);


        final OfferSliderAdapter sliderAdapter = new OfferSliderAdapter(getContext());
        bookImage.setSliderAdapter(sliderAdapter);
        databaseReference = firebaseDatabase.getReference().child("Offers");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    sliderAdapter.addItem(ds.child("OfferImage").getValue(String.class),ds.child("OfferCategory").getValue(String.class),ds.child("OfferSubCategory").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        comingSoonListView = view.findViewById(R.id.comingList);

        List<ComingSoonModal> imageList2 = new ArrayList<>();
        comingSoonAdapter = new ComingSoonAdapter(getContext(),R.layout.coming_soon_single,imageList2);
        comingSoonListView.setAdapter(comingSoonAdapter);
        comingSoonref = firebaseDatabase.getReference().child("ComingSoon");
        comingSoonref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ComingSoonModal CM = dataSnapshot.getValue(ComingSoonModal.class);
                comingSoonAdapter.add(CM);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        NonScrollListView mainList = view.findViewById(R.id.mainPageList);
        List<BooksModal> dummyListMain = new ArrayList<>();
        final List<String> categoryList = new ArrayList<>();
        final List<String> subcategoryList = new ArrayList<>();
        final MainAdapter adapter2 = new MainAdapter(getContext(),R.layout.singl_book_list,dummyListMain,categoryList,subcategoryList);
        mainList.setAdapter(adapter2);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceMain = firebaseDatabase.getReference().child("CategoryImages");
        databaseReferenceMain.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    adapter2.add(modal);
                    categoryList.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Activity Tracker List
        trackerList = view.findViewById(R.id.trackerCategoryList);
        final List<String> trackerCategories = new ArrayList<>();
        final List<String> trackerUrl = new ArrayList<>();
        List<BooksModal> dummyTrackerList = new ArrayList<>();
        final ActivityTrackerSubAdapter adapter = new ActivityTrackerSubAdapter(getContext(),R.layout.single_subcategory,dummyTrackerList,trackerCategories,trackerUrl);
        trackerList.setAdapter(adapter);
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("ActivityTrackersImages");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    trackerCategories.add(ds.getKey());
                    trackerUrl.add(ds.getValue().toString());
                    adapter.add(modal);

                    homeShimmer.stopShimmer();
                    homeSV.setVisibility(View.VISIBLE);
                    homeShimmer.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
