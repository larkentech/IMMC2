package com.larken.immc2;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.larken.immc2.Fragments.BestSellingFragment;
import com.larken.immc2.Fragments.EngineeringListFragment;
import com.larken.immc2.Fragments.FeaturedFragment;
import com.larken.immc2.Fragments.MathsListFragment;
import com.larken.immc2.Fragments.QuoteSeriesListFragment;
import com.larken.immc2.Fragments.ScienceListFragment;

public class DetailsContainerActivity extends AppCompatActivity {


    FrameLayout detailsActivityContainer;
    String category;
    String subcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_container);

        category = getIntent().getExtras().getString("Category");
        subcategory = getIntent().getExtras().getString("SubCategory");

        detailsActivityContainer = findViewById(R.id.detailsActivityContainer);

        Bundle args = new Bundle();
        args.putString("Category",category);
        args.putString("SubCategory",subcategory);

        FragmentManager manager5 = getSupportFragmentManager();
        FeaturedFragment featuredFragment = new FeaturedFragment();
        featuredFragment.setArguments(args);
        FragmentTransaction transaction5 = manager5.beginTransaction();
        transaction5.add(R.id.detailsActivityContainer,featuredFragment);
        transaction5.commit();

    }
}
