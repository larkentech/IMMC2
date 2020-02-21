package com.larken.immc2.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larken.immc2.AdapterClasses.BestSellingAdapter;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.R;
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
public class EngineeringListFragment extends Fragment {

    HListView csNotebookListView;
    HListView ecNoteBookListView;
    HListView eeNoteBookListView;
    HListView meNoteBookListView;
    HListView ceNoteBookListView;
    HListView ieNoteBookListView;
    HListView miningeggNoteBookListView;
    HListView teNoteBookListView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference4;
    DatabaseReference databaseReference5;
    DatabaseReference databaseReference6;
    DatabaseReference databaseReference7;
    DatabaseReference databaseReference8;
    DatabaseReference databaseReference9;
    DatabaseReference databaseReference10;
    DatabaseReference databaseReference11;

    BestSellingAdapter csNoteBooksAdapter;
    BestSellingAdapter ecNoteBookAdapter;
    BestSellingAdapter eeNoteBookAdapter;
    BestSellingAdapter meNoteBookAdapter;
    BestSellingAdapter ceNoteBookAdapter;
    BestSellingAdapter ieNoteBookAdapter;
    BestSellingAdapter miningeggNoteBookAdapter;
    BestSellingAdapter teNoteBookAdapter;

    List<String> csBookId;
    List<String> ecBookId;
    List<String> eeBookId;
    List<String> meBookId;
    List<String> ceBookId;
    List<String> ieBookId;
    List<String> miningeggBookId;
    List<String> teBookId;



    public EngineeringListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_engineering_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Book ID's List
        csBookId = new ArrayList<>();
        ecBookId = new ArrayList<>();
        eeBookId = new ArrayList<>();
        meBookId = new ArrayList<>();
        ceBookId = new ArrayList<>();
        ieBookId = new ArrayList<>();
        miningeggBookId = new ArrayList<>();
        teBookId = new ArrayList<>();

        csNotebookListView = view.findViewById(R.id.csNoteBooksList);
        List<BooksModal> csNoteBooksModalsList = new ArrayList<>();
        csNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,csNoteBooksModalsList,csBookId);
        csNotebookListView.setAdapter(csNoteBooksAdapter);

        //Ec
        ecNoteBookListView = view.findViewById(R.id.ecNoteBookList);
        List<BooksModal> ecNoteBookModalList = new ArrayList<>();
        ecNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,ecNoteBookModalList, ecBookId);
        ecNoteBookListView.setAdapter(ecNoteBookAdapter);

        //EE
        eeNoteBookListView = view.findViewById(R.id.eeNoteBookList);
        List<BooksModal> eeNoteBookModalList = new ArrayList<>();
        eeNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,eeNoteBookModalList, eeBookId);
        eeNoteBookListView.setAdapter(eeNoteBookAdapter);

        //ME

        meNoteBookListView = view.findViewById(R.id.meNoteBookList);
        List<BooksModal> meNoteBookModalList = new ArrayList<>();
        meNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,meNoteBookModalList, meBookId);
        meNoteBookListView.setAdapter(meNoteBookAdapter);

        //Chemical Engineering
        ceNoteBookListView = view.findViewById(R.id.ceNoteBookList);
        List<BooksModal> ceNoteBookModalList = new ArrayList<>();
        ceNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,ceNoteBookModalList,ceBookId);
        ceNoteBookListView.setAdapter(ceNoteBookAdapter);

        //Instrumentaion Engineering Note Books
        ieNoteBookListView = view.findViewById(R.id.ieNoteBookList);
        List<BooksModal> ieNoteBookModalList =  new ArrayList<>();
        ieNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,ieNoteBookModalList,ieBookId);
        ieNoteBookListView.setAdapter(ieNoteBookAdapter);

        //Mining Engineering Note books
        miningeggNoteBookListView = view.findViewById(R.id.miningeggNoteBookList);
        List<BooksModal> miningNoteBookModalList = new ArrayList<>();
        miningeggNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,miningNoteBookModalList,miningeggBookId);
        miningeggNoteBookListView.setAdapter(miningeggNoteBookAdapter);

        //textile Engineering Note Books
        teNoteBookListView = view.findViewById(R.id.teNoteBookList);
        List<BooksModal> teNoteBookModalList = new ArrayList<>();
        teNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,teNoteBookModalList,teBookId);
        teNoteBookListView.setAdapter(teNoteBookAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference4 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("CS");
        databaseReference4.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal csNoteBooksModal = dataSnapshot.getValue(BooksModal.class);
                csNoteBooksAdapter.add(csNoteBooksModal);
                csBookId.add(dataSnapshot.getKey());
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

        databaseReference5 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("EC");
        databaseReference5.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal ecNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                ecNoteBookAdapter.add(ecNoteBookModal);
                ecBookId.add(dataSnapshot.getKey());

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

        databaseReference6 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("EE");
        databaseReference6.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal eeNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                eeNoteBookAdapter.add(eeNoteBookModal);
                eeBookId.add(dataSnapshot.getKey());
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

        databaseReference7 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("ME");
        databaseReference7.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal meNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                meNoteBookAdapter.add(meNoteBookModal);
                meBookId.add(dataSnapshot.getKey());
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

        databaseReference8= firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("Chemical Engineering");
        databaseReference8.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal ceNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                ceNoteBookAdapter.add(ceNoteBookModal);

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

        databaseReference9 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("Instrumentation Engineering");
        databaseReference9.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal ieNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                ieNoteBookAdapter.add(ieNoteBookModal);
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

        databaseReference10 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("Mining Engineering");
        databaseReference10.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal miningEggNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                miningeggNoteBookAdapter.add(miningEggNoteBookModal);
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

        databaseReference11 = firebaseDatabase.getReference().child("BookDetails").child("Engineering").child("Textile Engineering");
        databaseReference11.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal teNoteBookModal = dataSnapshot.getValue(BooksModal.class);
                teNoteBookAdapter.add(teNoteBookModal);
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
