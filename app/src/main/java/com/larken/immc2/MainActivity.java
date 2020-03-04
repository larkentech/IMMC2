package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.larken.immc2.Fragments.AccountFragment;
import com.larken.immc2.Fragments.CartFragment;
import com.larken.immc2.Fragments.HomeFragment;
import com.larken.immc2.Fragments.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);

        FragmentManager manager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainFragmentContainer,homeFragment);
        transaction.commit();




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_home:
                        FragmentManager manager = getSupportFragmentManager();
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.mainFragmentContainer,homeFragment);
                        transaction.commit();
                        break;

                    case R.id.navigation_search:

                        FragmentManager manager1 = getSupportFragmentManager();
                        CartFragment cartFragment = new CartFragment();
                        FragmentTransaction transaction1 = manager1.beginTransaction();
                        transaction1.replace(R.id.mainFragmentContainer,cartFragment);
                        transaction1.addToBackStack("MainFlow");
                        transaction1.commit();
                        break;


                    case R.id.navigation_notifications:
                        FragmentManager manager3 = getSupportFragmentManager();
                        NotificationsFragment notificationsFragment = new NotificationsFragment();
                        FragmentTransaction transaction3 = manager3.beginTransaction();
                        transaction3.replace(R.id.mainFragmentContainer,notificationsFragment);
                        transaction3.addToBackStack("MainFlow");
                        transaction3.commit();
                        break;

                    case R.id.navigation_account:
                        FragmentManager manager4 = getSupportFragmentManager();
                        AccountFragment accountFragment = new AccountFragment();
                        FragmentTransaction transaction4 = manager4.beginTransaction();
                        transaction4.replace(R.id.mainFragmentContainer,accountFragment);
                        transaction4.addToBackStack("MainFlow");
                        transaction4.commit();
                        break;

                }

                return false;
            }
        });

    }

}
