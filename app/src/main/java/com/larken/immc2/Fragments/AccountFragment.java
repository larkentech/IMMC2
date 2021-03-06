package com.larken.immc2.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.Query;
import com.larken.immc2.AdapterClasses.OrderAdapter;
import com.larken.immc2.AdapterClasses.PaymentOrdersListAdapter;
import com.larken.immc2.ModalClasses.BooksModal;
import com.larken.immc2.ModalClasses.OrderModal;
import com.larken.immc2.R;
import com.larken.immc2.SplashActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView userName;
    TextView userEmail;
    TextView userphone;
    CircleImageView userImage;
    EditText userFlat;
    EditText userlandmark;
    EditText userArea;
    EditText userCity;
    TextView contactNumber;
    TextView contactWeb;
    TextView myOrders;

    FirebaseAuth mAuth;

    TextView signout;
    TextView feedback;
    String feeling = " ";
    EditText feedbackEt;

    Button updateAddress;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("OrderDeatails").child(mAuth.getUid());
        databaseReference = firebaseDatabase.getReference().child("BookDetails");


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setVisibility(View.VISIBLE);




        userName = view.findViewById(R.id.prsnName);
        userEmail = view.findViewById(R.id.prsnMail);
        userphone = view.findViewById(R.id.prsnNum);
        userImage = view.findViewById(R.id.prsnImage);
        userFlat = view.findViewById(R.id.addFlatnum);
        userlandmark = view.findViewById(R.id.addLandmark);
        userArea = view.findViewById(R.id.addArea);
        userCity = view.findViewById(R.id.addCity);
        signout = view.findViewById(R.id.signOut);
        contactNumber=view.findViewById(R.id.contactNumber);
        contactWeb=view.findViewById(R.id.contactWeb);
        myOrders=view.findViewById(R.id.txtMyOrders);
        mAuth = FirebaseAuth.getInstance();
        updateAddress = view.findViewById(R.id.updateAddress);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getCurrentUser().getUid());

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrdersFragment dialogFragment = new MyOrdersFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialogFragment.show(ft, "dialog");


            }
        });






        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child("Name").getValue(String.class));
                userEmail.setText(dataSnapshot.child("Email").getValue(String.class));
                userphone.setText(dataSnapshot.child("PhoneNumber").getValue(String.class));
                userFlat.setText(dataSnapshot.child("Address").child("Flatno").getValue(String.class));
                userlandmark.setText(dataSnapshot.child("Address").child("Landmark").getValue(String.class));
                userArea.setText(dataSnapshot.child("Address").child("Area").getValue(String.class));
                userCity.setText(dataSnapshot.child("Address").child("City").getValue(String.class));


                Glide
                        .with(getContext())
                        .load(dataSnapshot.child("ProfilePhoto").getValue(String.class))
                        .centerCrop()
                        .into(userImage);



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


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> userMap = new HashMap<>();
                userMap.put("Flatno",userFlat.getText().toString());
                userMap.put("Landmark",userlandmark.getText().toString().trim());
                userMap.put("City",userCity.getText().toString().trim());
                userMap.put("Area",userArea.getText().toString().trim());

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails")
                        .child(mAuth.getUid()).child("Address");
                databaseReference.updateChildren(userMap);

                Toasty.success(getContext(),"Address changed successfully").show();
            }
        });



            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("OrderDetails");



        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to Sign Out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }

            private void finish() {
                mAuth.signOut();
                Intent i = new Intent(getActivity(), SplashActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        contactNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:7019527490"));
                startActivity(i);
                getActivity();
            }
        });
        contactWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","abc@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@iammc2.com"}); // String[] addresses
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }

        });


    }

}
