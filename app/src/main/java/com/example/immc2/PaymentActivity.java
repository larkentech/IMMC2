package com.example.immc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immc2.AdapterClasses.CartAdapter;
import com.example.immc2.AdapterClasses.PaymentOrdersListAdapter;
import com.example.immc2.Fragments.AdrsFragment;
import com.example.immc2.Fragments.CartFragment;
import com.example.immc2.ModalClasses.BooksModal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    //TextViews
    public TextView userName;
    public TextView userAddress;
    public TextView userPhone;
    Button changeAddress;
    ListView ordersList;
    PaymentOrdersListAdapter adapter;
    public TextView finalPrice;

    Button proceedToBuy;
    FirebaseAuth mAuth;

    //Database for UserDetails
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceBooks;

    //Book Details
    List<String> bookId;
    List<String> bookCategoryId;
    List<String> bookSubCategoryId;
    List<String> itemsCount;
    List<String> tempKeys;
    int count1;
    int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mAuth = FirebaseAuth.getInstance();



        userName = findViewById(R.id.orderusername);
        userAddress = findViewById(R.id.orderuseraddress);
        userPhone = findViewById(R.id.orderuserphone);
        finalPrice  = findViewById(R.id.finalPriceTV);
        changeAddress = findViewById(R.id.btnaddress);

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdrsFragment adrsFragment = new AdrsFragment();
                adrsFragment.show(getSupportFragmentManager(),"Showing Dialog");
            }
        });

        userPhone.setText(mAuth.getCurrentUser().getPhoneNumber());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getUid());
        databaseReferenceBooks = firebaseDatabase.getReference().child("BookDetails");


        bookId = new ArrayList<>();
        bookCategoryId = new ArrayList<>();
        bookSubCategoryId = new ArrayList<>();
        itemsCount = new ArrayList<>();
        tempKeys = new ArrayList<>();

        ordersList = findViewById(R.id.ordersListPayment);
        List<BooksModal> cartItems = new ArrayList<>();
        adapter = new PaymentOrdersListAdapter(this, R.layout.single_cart_item, cartItems,itemsCount,tempKeys);
        ordersList.setAdapter(adapter);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.child("Name").getValue(String.class));
                userPhone.setText(dataSnapshot.child("PhoneNumber").getValue(String.class));
                String Address = dataSnapshot.child("Address").child("Flatno").getValue(String.class) +
                                ", " + dataSnapshot.child("Address").child("Area").getValue(String.class) +
                        ", " + dataSnapshot.child("Address").child("City").getValue(String.class) + "Zipcode:" +
                        dataSnapshot.child("Address").child("Zipcode").getValue(String.class) + "\nLandmark: "
                        + dataSnapshot.child("Address").child("Landmark").getValue(String.class);
                userAddress.setText(Address);


                if (dataSnapshot.hasChild("Cart")) {
                    count1 = (int) dataSnapshot.child("Cart").getChildrenCount();
                    for (DataSnapshot ds : dataSnapshot.child("Cart").getChildren()) {
                        String key = ds.getKey();
                        tempKeys.add(key);
                        databaseReference.child("Cart").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                count2++;
                                bookId.add(dataSnapshot.child("BookId").getValue(String.class));
                                bookCategoryId.add(dataSnapshot.child("BookCategory").getValue(String.class));
                                bookSubCategoryId.add(dataSnapshot.child("BookSubCategory").getValue(String.class));
                                itemsCount.add(dataSnapshot.child("Count").getValue(String.class));
                                if (count1 == count2){
                                    Log.v("TAG", "BookID:" + bookId);
                                    displayCart(bookId,bookCategoryId,bookSubCategoryId,itemsCount);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                    //getBookDetails(bookId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        proceedToBuy = findViewById(R.id.proceedToBuy);
        proceedToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                razorPayment(100);
            }
        });

    }

    private void razorPayment(int i) {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "FitVib");
            options.put("description", "Hello World");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", i*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", mAuth.getCurrentUser().getEmail());
            preFill.put("contact", mAuth.getCurrentUser().getPhoneNumber());
            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    public int randomTxnID() {
        Random rnd = new Random();
        int txnID = rnd.nextInt(99999999) + 1;
        return txnID;
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toasty.success(getApplicationContext(),"Payment Successfull").show();
    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    private void displayCart(List<String> bookId, List<String> bookCategoryId, List<String> bookSubCategoryId, List<String> itemsCount) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        for (int i=0;i<bookId.size();i++)
        {
            DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("BookDetails").child(bookCategoryId.get(i))
                    .child(bookSubCategoryId.get(i)).child(bookId.get(i));
            databaseReference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BooksModal modal = dataSnapshot.getValue(BooksModal.class);
                    adapter.add(modal);
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void reloadData(){
        getData();
    }

    private void getData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Cart")) {
                    count1 = (int) dataSnapshot.child("Cart").getChildrenCount();
                    for (DataSnapshot ds : dataSnapshot.child("Cart").getChildren()) {
                        String key = ds.getKey();
                        tempKeys.add(key);
                        databaseReference.child("Cart").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                count2++;
                                bookId.add(dataSnapshot.child("BookId").getValue(String.class));
                                bookCategoryId.add(dataSnapshot.child("BookCategory").getValue(String.class));
                                bookSubCategoryId.add(dataSnapshot.child("BookSubCategory").getValue(String.class));
                                itemsCount.add(dataSnapshot.child("Count").getValue(String.class));
                                if (count1 == count2){
                                    Log.v("TAG", "BookID:" + bookId);
                                    displayCart(bookId,bookCategoryId,bookSubCategoryId,itemsCount);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                    //getBookDetails(bookId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
