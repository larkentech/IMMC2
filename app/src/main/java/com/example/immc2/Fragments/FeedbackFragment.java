package com.example.immc2.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.immc2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    String feeling = " ";
    EditText feedbackEt;


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);


    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        feedbackEt = (EditText) view.findViewById(R.id.userFeedbackET);
        final HashMap<String, Object> feedbackMap = new HashMap<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Feedback");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.sendFeedback);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackEt.getText().toString().trim();
                if (feeling.compareToIgnoreCase(" ") == 0) {
                    Toasty.error(getContext(), "Please tell us how you feel", Toast.LENGTH_SHORT, true).show();
                } else {
                    feedbackMap.put("Feedback", feedback);
                    feedbackMap.put("Feeling", feeling);
                    databaseReference.push().setValue(feedbackMap);
                    Toasty.success(getContext(), "Thanks For Your Feedback!", Toast.LENGTH_SHORT, true).show();
                    feedbackEt.setText("");
                    feedbackEt.setHint("(Optional)");

                }

            }
        });
        SmileRating smileRating = (SmileRating) view.findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {
                    case SmileRating.BAD:
                        feeling = "Bad";
                        break;
                    case SmileRating.GOOD:
                        feeling = "Good";
                        break;
                    case SmileRating.GREAT:
                        feeling = "Great";
                        break;
                    case SmileRating.OKAY:
                        feeling = "Okay";
                        break;
                    case SmileRating.TERRIBLE:
                        feeling = "Terrible";
                        break;
                }
            }
        });


    }

}
