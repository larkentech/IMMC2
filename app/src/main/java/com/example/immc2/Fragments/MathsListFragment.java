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
public class MathsListFragment extends Fragment {

    HListView agbNoteBooksListView;
    HListView gmNoteBookListView;
    HListView tgmNoteBookListView;
    HListView atmNoteBookListView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;

    BestSellingAdapter agbNoteBooksAdapter;
    BestSellingAdapter tgmNoteBookAdapter;
    BestSellingAdapter atmNoteBookAdapter;
    BestSellingAdapter gmNoteBookAdapter;

    List<String> agList;
    List<String> trList;
    List<String> arList;
    List<String> geList;


    public MathsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maths_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instatntiating Book ID
        agList = new ArrayList<>();
        arList = new ArrayList<>();
        geList = new ArrayList<>();
        trList = new ArrayList<>();

        //Algebra NoteBooks

        agbNoteBooksListView = view.findViewById(R.id.agbNoteBooksList);
        List<BooksModal> agbNoteBookModalList = new ArrayList<>();
        agbNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,agbNoteBookModalList, agList);
        agbNoteBooksListView.setAdapter(agbNoteBooksAdapter);

        //Geometry NoteBooks

        gmNoteBookListView = view.findViewById(R.id.gmNoteBookList);
        List<BooksModal> gmNoteBooksModalList = new ArrayList<>();
        gmNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,gmNoteBooksModalList, geList);
        gmNoteBookListView.setAdapter(gmNoteBookAdapter);

        //Trignometry NoteBooks

        tgmNoteBookListView = view.findViewById(R.id.tgmNoteBookList);
        List<BooksModal> tgmNoteBookModalList = new ArrayList<>();
        tgmNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,tgmNoteBookModalList, trList);
        tgmNoteBookListView.setAdapter(tgmNoteBookAdapter);

        //Arithmetic NoteBooks

        atmNoteBookListView = view.findViewById(R.id.atmNoteBookList);
        List<BooksModal> atmNoteBookModalList = new ArrayList<>();
        atmNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,atmNoteBookModalList, arList);
        atmNoteBookListView.setAdapter(atmNoteBookAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BookDetails").child("MathsTheme").child("AlgebraNoteBooks");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal agbNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                agbNoteBooksAdapter.add(agbNoteBookModal);
                agList.add(dataSnapshot.getKey());
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

        databaseReference1 = firebaseDatabase.getReference().child("BookDetails").child("MathsTheme").child("GeometryNoteBooks");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal gmBooksModal = dataSnapshot.getValue(BooksModal.class);
                gmNoteBookAdapter.add(gmBooksModal);
                geList.add(dataSnapshot.getKey());
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

        databaseReference2 = firebaseDatabase.getReference().child("BookDetails").child("MathsTheme").child("TrigonometryNoteBooks");
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal tgmBooksModal = dataSnapshot.getValue(BooksModal.class);
                tgmNoteBookAdapter.add(tgmBooksModal);
                trList.add(dataSnapshot.getKey());
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

        databaseReference3 = firebaseDatabase.getReference().child("BookDetails").child("MathsTheme").child("ArithmeticNoteBooks");
        databaseReference3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal atmBooksModal = dataSnapshot.getValue(BooksModal.class);
                atmNoteBookAdapter.add(atmBooksModal);
                arList.add(dataSnapshot.getKey());
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
