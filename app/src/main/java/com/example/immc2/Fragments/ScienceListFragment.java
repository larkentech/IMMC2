package com.example.immc2.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.immc2.AdapterClasses.BestSellingAdapter;
import com.example.immc2.ModalClasses.BooksModal;
import com.example.immc2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScienceListFragment extends Fragment {

    HListView phyNoteBooksListView;
    HListView chemNoteBookListView;
    HListView bioNoteBookListView;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;

    BestSellingAdapter phyNoteBooksAdapter;
    BestSellingAdapter chemNoteBookAdapter;
    BestSellingAdapter bioNoteBookAdapter;

    List<String> phyID;
    List<String> cheID;
    List<String> bioID;


    public ScienceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_science_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phyID = new ArrayList<>();
        cheID = new ArrayList<>();
        bioID = new ArrayList<>();

        //Physics Notebooks

        chemNoteBookListView = view.findViewById(R.id.chemNoteBookList);
        List<BooksModal> chemNoteBookModalList = new ArrayList<>();
        chemNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,chemNoteBookModalList, phyID);
        chemNoteBookListView.setAdapter(chemNoteBookAdapter);


        //Chemistry NoteBooks

        phyNoteBooksListView = view.findViewById(R.id.phyNoteBooksList);
        List<BooksModal> phyNoteBookModalList = new ArrayList<>();
        phyNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,phyNoteBookModalList, cheID);
        phyNoteBooksListView.setAdapter(phyNoteBooksAdapter);


        //Biology NoteBooks

        bioNoteBookListView = view.findViewById(R.id.bioNoteBookList);
        List<BooksModal> bioNoteBookModalList = new ArrayList<>();
        bioNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,bioNoteBookModalList, bioID);
        bioNoteBookListView.setAdapter(bioNoteBookAdapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BookDetails").child("ScienceTheme").child("PhysicsNoteBooks");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal physicsNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                phyNoteBooksAdapter.add(physicsNoteBookModal);
                phyID.add(dataSnapshot.getKey());
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

        databaseReference1 = firebaseDatabase.getReference().child("BookDetails").child("ScienceTheme").child("ChemistryNoteBooks");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal chemistryBookModal = dataSnapshot.getValue(BooksModal.class);
                chemNoteBookAdapter.add(chemistryBookModal);
                cheID.add(dataSnapshot.getKey());
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

        databaseReference2 = firebaseDatabase.getReference().child("BookDetails").child("ScienceTheme").child("BiologyNoteBooks");
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal bioBooksModal = dataSnapshot.getValue(BooksModal.class);
                bioNoteBookAdapter.add(bioBooksModal);
                bioID.add(dataSnapshot.getKey());
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

    }
}
