package com.example.immc2;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.immc2.Fragments.EngineeringListFragment;
import com.example.immc2.Fragments.MathsListFragment;
import com.example.immc2.Fragments.QuoteSeriesListFragment;
import com.example.immc2.Fragments.ScienceListFragment;

public class DetailsContainerActivity extends AppCompatActivity {


    FrameLayout detailsActivityContainer;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_container);

        category = getIntent().getExtras().getString("Category");

        detailsActivityContainer = findViewById(R.id.detailsActivityContainer);

        switch (category)
        {
            case "Engineering":
                FragmentManager manager = getSupportFragmentManager();
                EngineeringListFragment homeFragment = new EngineeringListFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.detailsActivityContainer,homeFragment);
                transaction.commit();
                break;

            case "Quotes":
                FragmentManager manager1 = getSupportFragmentManager();
                QuoteSeriesListFragment quoteSeriesListFragment = new QuoteSeriesListFragment();
                FragmentTransaction transaction1 = manager1.beginTransaction();
                transaction1.add(R.id.detailsActivityContainer,quoteSeriesListFragment);
                transaction1.commit();
                break;

            case "ScienceTheme":
                FragmentManager manager2 = getSupportFragmentManager();
                ScienceListFragment scienceListFragment = new ScienceListFragment();
                FragmentTransaction transaction2 = manager2.beginTransaction();
                transaction2.add(R.id.detailsActivityContainer,scienceListFragment);
                transaction2.commit();
                break;

            case "MathsTheme":
                FragmentManager manager3 = getSupportFragmentManager();
                MathsListFragment mathsListFragment = new MathsListFragment();
                FragmentTransaction transaction3 = manager3.beginTransaction();
                transaction3.add(R.id.detailsActivityContainer,mathsListFragment);
                transaction3.commit();
                break;

        }




    }
}
