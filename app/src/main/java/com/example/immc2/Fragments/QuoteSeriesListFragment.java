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
public class QuoteSeriesListFragment extends Fragment {

    HListView vivekanandaNoteBooksListView;
    HListView apjNoteBookListView;
    HListView scbNoteBookListView;
    HListView einstenNoteBookListView;
    HListView elonMuskNoteBookListView;
    HListView billGatesNoteBookListView;
    HListView steveJobsNoteBookListView;
    HListView ramanujanNoteBookListView;
    HListView buddhaNoteBookListView;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReference4;
    DatabaseReference databaseReference5;
    DatabaseReference databaseReference6;
    DatabaseReference databaseReference7;
    DatabaseReference databaseReference8;

    BestSellingAdapter vivekandaNoteBooksAdapter;
    BestSellingAdapter apjNoteBooksAdapter;
    BestSellingAdapter scbNoteBooksAdapter;
    BestSellingAdapter einstenNoteBooksAdapter;
    BestSellingAdapter elonMuskNoteBookAdapter;
    BestSellingAdapter billGatesNoteBookAdapter;
    BestSellingAdapter steveJobsNoteBookAdapter;
    BestSellingAdapter ramanujanNoteBookAdapter;
    BestSellingAdapter buddhaNoteBookAdapter;

    List<String> vivID;
    List<String> apjID;
    List<String> scbID;
    List<String> einID;
    List<String> emID;
    List<String> bgID;
    List<String> stjID;
    List<String> rjID;
    List<String> bdID;

    public QuoteSeriesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote_series_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vivID = new ArrayList<>();
        apjID = new ArrayList<>();
        scbID = new ArrayList<>();
        einID = new ArrayList<>();
        emID = new ArrayList<>();
        bgID = new ArrayList<>();
        stjID = new ArrayList<>();
        rjID = new ArrayList<>();
        bdID = new ArrayList<>();

        //Vivekananda Notebook
        vivekanandaNoteBooksListView = view.findViewById(R.id.vivekanandaNoteBooksList);
        List<BooksModal> vivekanandaBooksModalsList = new ArrayList<>();
        vivekandaNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,vivekanandaBooksModalsList, vivID);
        vivekanandaNoteBooksListView.setAdapter(vivekandaNoteBooksAdapter);

        //apj NoteBook
        apjNoteBookListView = view.findViewById(R.id.apjNoteBookList);
        List<BooksModal> apjBooksModalList = new ArrayList<>();
        apjNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,apjBooksModalList, apjID);
        apjNoteBookListView.setAdapter(apjNoteBooksAdapter);

        //Subhash Chandra Bose Notebooks
        scbNoteBookListView = view.findViewById(R.id.scbNoteBookList);
        List<BooksModal> scbBooksModal = new ArrayList<>();
        scbNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,scbBooksModal, scbID);
        scbNoteBookListView.setAdapter(scbNoteBooksAdapter);

        //Einstein NoteBooks
        einstenNoteBookListView = view.findViewById(R.id.einstenNoteBookList);
        List<BooksModal> einsteinBooksModal = new ArrayList<>();
        einstenNoteBooksAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,einsteinBooksModal, einID);
        einstenNoteBookListView.setAdapter(einstenNoteBooksAdapter);

        //Elon Musk NoteBooks
        elonMuskNoteBookListView = view.findViewById(R.id.elonMuskNoteBookList);
        List<BooksModal> elonMuskBooksModal = new ArrayList<>();
        elonMuskNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,elonMuskBooksModal, emID);
        elonMuskNoteBookListView.setAdapter(elonMuskNoteBookAdapter);

        //Bill Gates NoteBooks
        billGatesNoteBookListView = view.findViewById(R.id.billGatesNoteBookList);
        List<BooksModal> billGatesBooksModal = new ArrayList<>();
        billGatesNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,billGatesBooksModal, bgID);
        billGatesNoteBookListView.setAdapter(billGatesNoteBookAdapter);

        //Steve Jobs NoteBooks
        steveJobsNoteBookListView = view.findViewById(R.id.steveJobsNoteBookList);
        List<BooksModal> stevejobsBookModal = new ArrayList<>();
        steveJobsNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,stevejobsBookModal, stjID);
        steveJobsNoteBookListView.setAdapter(steveJobsNoteBookAdapter);

        //Ramanujan Notebooks
        ramanujanNoteBookListView = view.findViewById(R.id.ramanujanNoteBookList);
        List<BooksModal> ramanujanBooksModal = new ArrayList<>();
        ramanujanNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,ramanujanBooksModal, rjID);
        ramanujanNoteBookListView.setAdapter(ramanujanNoteBookAdapter);

        //Buddha NoteBooks
        buddhaNoteBookListView = view.findViewById(R.id.buddhaNoteBookList);
        List<BooksModal> buddhaBooksModal = new ArrayList<>();
        buddhaNoteBookAdapter = new BestSellingAdapter(getContext(),R.layout.featured_single,buddhaBooksModal, bdID);
        buddhaNoteBookListView.setAdapter(buddhaNoteBookAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("Vivekananda NoteBooks");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal vivekanandaNoteBooksModal = dataSnapshot.getValue(BooksModal.class);
                vivekandaNoteBooksAdapter.add(vivekanandaNoteBooksModal);
                vivID.add(dataSnapshot.getKey());
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


        databaseReference1 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("APJ NoteBooks");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal apjNoteBooksModal = dataSnapshot.getValue(BooksModal.class);
                apjNoteBooksAdapter.add(apjNoteBooksModal);
                apjID.add(dataSnapshot.getKey());
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

        databaseReference2 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("Subhash Chandra bose NoteBooks");
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal scbBooksModal = dataSnapshot.getValue(BooksModal.class);
                scbNoteBooksAdapter.add(scbBooksModal);
                scbID.add(dataSnapshot.getKey());
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

        databaseReference3 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("AlbertEinsteinQuoteSeries");
        databaseReference3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal einsteinBookModal = dataSnapshot.getValue(BooksModal.class);
                einstenNoteBooksAdapter.add(einsteinBookModal);
                einID.add(dataSnapshot.getKey());
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


        databaseReference4 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("ElonMuskQuoteSeries");
        databaseReference4.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal elonMuskBookModal = dataSnapshot.getValue(BooksModal.class);
                elonMuskNoteBookAdapter.add(elonMuskBookModal);
                emID.add(dataSnapshot.getKey());
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

        databaseReference5 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("BillGatesQuoteSeries");
        databaseReference5.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal billGatesBooksModal = dataSnapshot.getValue(BooksModal.class);
                billGatesNoteBookAdapter.add(billGatesBooksModal);
                bgID.add(dataSnapshot.getKey());
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

        databaseReference6 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("SteveJobsQuoteSeries");
        databaseReference6.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal steveJobsBookModal = dataSnapshot.getValue(BooksModal.class);
                steveJobsNoteBookAdapter.add(steveJobsBookModal);
                stjID.add(dataSnapshot.getKey());
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

        databaseReference7 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("RamanujanQuoteSeries");
        databaseReference7.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal ramanujanBooksModal = dataSnapshot.getValue(BooksModal.class);
                ramanujanNoteBookAdapter.add(ramanujanBooksModal);
                rjID.add(dataSnapshot.getKey());
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

        databaseReference8 = firebaseDatabase.getReference().child("BookDetails").child("QuoteTheme").child("BuddhaQuoteSeries");
        databaseReference8.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BooksModal buddhaBooksModal = dataSnapshot.getValue(BooksModal.class);
                buddhaNoteBookAdapter.add(buddhaBooksModal);
                bdID.add(dataSnapshot.getKey());
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
