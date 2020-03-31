package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.ModalClasses.BooksModal;

import java.util.ArrayList;
import java.util.List;

public class TrackerGridActivity extends AppCompatActivity {

    GridView trackerGrid;
    String categoryTracker;
    TextView trackerCategoryTV;

    FirebaseDatabase firebaseDatabaseTracker;
    DatabaseReference databaseReferenceTracker;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    BestSellingAdapter featuredAdapter;
    String category;
    String subcategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_grid);

        categoryTracker = getIntent().getExtras().getString("Category");
        trackerCategoryTV = findViewById(R.id.trackerCategoryTV);
        trackerCategoryTV.setText(categoryTracker);

        Log.v("TAG","CategoryTracker:"+categoryTracker);

        List<String> featuredID = new ArrayList<>();
        trackerGrid = (GridView) findViewById(R.id.trackerGridView);
        List<BooksModal> booksModal = new ArrayList<>();
        featuredAdapter = new BestSellingAdapter(TrackerGridActivity.this,R.layout.featured_single,booksModal,featuredID);
        trackerGrid.setAdapter(featuredAdapter);

        firebaseDatabaseTracker = FirebaseDatabase.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceTracker = firebaseDatabaseTracker.getReference().child("Activity Trackers");
        databaseReferenceTracker.child(categoryTracker).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("BookDetails").child(ds.child("BookCategory").getValue().toString())
                            .child(ds.child("BookSubCategory").getValue().toString())
                            .child(ds.child("BookID").getValue().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                                    featuredAdapter.add(modal);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
