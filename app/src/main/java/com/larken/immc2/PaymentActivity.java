package com.larken.immc2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.larken.immc2.AdapterClasses.PaymentOrdersListAdapter;
import com.larken.immc2.Fragments.AdrsFragment;
import com.larken.immc2.ModalClasses.BooksModal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.larken.immc2.ModalClasses.OrderModal;
import com.larken.immc2.ModalClasses.PaymentModal;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    //TextViews
    public TextView userName;
    public TextView userAddress;
    public TextView userPhone;
    Button changeAddress;
    public ListView ordersList;
    public PaymentOrdersListAdapter adapter;
    public TextView finalPrice;
    public String orderedBookNames = "Book Name:";

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
    List<String> bookName;
    List<String> tempKeys;
    List<String> bookPrice;

   String Date;



    int count1;
    int count2 = 0;
    int calulatedprice = 0;

    ShimmerFrameLayout paymentShimmer;
    RelativeLayout paymentRL;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Calendar calendar=Calendar.getInstance();
        Date=DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.orderusername);
        userAddress = findViewById(R.id.orderuseraddress);
        userPhone = findViewById(R.id.orderuserphone);
        finalPrice  = findViewById(R.id.finalPriceTV);
        changeAddress = findViewById(R.id.btnaddress);

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdrsFragment dialogFragment = new AdrsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialogFragment.show(ft, "dialog");
            }
        });

        userPhone.setText(mAuth.getCurrentUser().getPhoneNumber());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getUid());
        databaseReferenceBooks = firebaseDatabase.getReference().child("BookDetails");


        bookId = new ArrayList<>();
        itemsCount=new ArrayList<>();
        bookCategoryId = new ArrayList<>();
        bookSubCategoryId = new ArrayList<>();
        bookName=new ArrayList<>();
        bookPrice=new ArrayList<>();
        tempKeys = new ArrayList<>();

        ordersList = findViewById(R.id.ordersListPayment);
        List<PaymentModal> cartItems = new ArrayList<>();
        adapter = new PaymentOrdersListAdapter(this, R.layout.single_order_item, cartItems,itemsCount,tempKeys);
        ordersList.setAdapter(adapter);

        paymentRL=findViewById(R.id.paymentRL);
        paymentShimmer=findViewById(R.id.shimmer_view_payment);
        paymentShimmer.startShimmer();


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
                finalPrice.setText(dataSnapshot.child("FinalPrice").getValue(String.class));



                if (dataSnapshot.hasChild("Cart")) {
                    paymentShimmer.stopShimmer();
                    paymentShimmer.setVisibility(View.GONE);
                    paymentRL.setVisibility(View.VISIBLE);

                    databaseReference.child("Cart").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            PaymentModal modal = dataSnapshot.getValue(PaymentModal.class);
                            adapter.add(modal);
                            displayFinalPrice();
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
            options.put("name", "IamMC2");
            options.put("description", "Grateful to have you as our customer");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", calulatedprice*100);

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

        try {
            int txn = randomTxnID();
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference();
            databaseReference = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getUid());
            firebaseDatabase.getReference().child("UserDetails").child(mAuth.getCurrentUser().getUid()).child("Cart").removeValue();



            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("Name", userName.getText().toString());
            userMap.put("FinalPrice",finalPrice.getText().toString());
            userMap.put("Address",userAddress.getText().toString());
            userMap.put("TxnID",txn);
            userMap.put("OrderDate",Date.toString());
            userMap.put("ItemsCount",getItemCount());
            userMap.put("BookName",getBookNames());

            userMap.put("PhoneNumber", currentFirebaseUser.getPhoneNumber().toString());
            databaseReference.child("OrderDetails").push().setValue(userMap);

            Toasty.success(getApplicationContext(),"Payment Successfull").show();
            Intent intent=new Intent(this,PaymentSuccessActivity.class);
            startActivity(intent);
        }catch (Exception e)
        {
            Toasty.error(getApplicationContext(),"failed").show();
            Log.v("TAG","Exception:"+e);
        }





    }

    private String getBookNames() {

        View v;
        String BookNames = "Book Name:";
        for (int i=0;i<ordersList.getCount();i++)
        {
            v = ordersList.getAdapter().getView(i,null,null);
            TextView bookNames = (TextView) v.findViewById(R.id.bookNameOrder);
            BookNames = BookNames + bookNames.getText().toString()+",";
            Log.v("TAG","BookNames2=>"+bookNames.getText().toString()+",");
            Log.v("TAG","BookNames2=>"+BookNames);
        }

        return BookNames;



    }

    private String getItemCount() {

        View v;
        String BookNames = "";
        for (int i=0;i<itemsCount.size();i++)
        {
            BookNames = BookNames + itemsCount.get(i).toString()+",";
            Log.v("TAG","BookNames33=>"+BookNames);
        }

        return BookNames;



    }

    @Override
    public void onPaymentError(int i, String s) {

        Toasty.error(getApplicationContext(),"Payment Unsuccessfull").show();
        Intent intent=new Intent(this,PaymentFailedActivity.class);
        startActivity(intent);

    }

    public void displayCart(final List<String> bookId, final List<String> bookCategoryId, final List<String> bookSubCategoryId, final List<String> bookName, final List<String> bookPrice, final List<String> itemsCount) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        for (int i=0;i<bookId.size();i++)
        {
            orderedBookNames.concat(bookName.get(i)+",");
            DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("UserDetails").child(mAuth.getUid()).child("Cart");
            databaseReference3.addChildEventListener(new ChildEventListener() {
                private Object CartImage;

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    PaymentModal modal = dataSnapshot.getValue(PaymentModal.class);
                    adapter.add(modal);
                    //adapter.notifyDataSetChanged();


                }

                private Activity getContext() {
                    return null;
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

    //To Set the final price when loaded.
    public void displayFinalPrice() {

        String _finalPrice;
        View v;
        ArrayList<String> prices = new ArrayList<>();
        TextView priceTV;
        for (int i=0;i<ordersList.getCount();i++)
        {
            v = ordersList.getAdapter().getView(i,null,null);
            priceTV = (TextView) v.findViewById(R.id.bookPriceOrder);
            prices.add(priceTV.getText().toString());
            if (ordersList.getCount() == (i+1))
            {
                Log.v("TAG","FinalPrice:"+prices);
                String[] temp = new String[prices.size()];
                temp = prices.get(i).split("\\.");
                String onlyPrice = temp[1];
                String[] temp2 = new String[prices.size()];
                temp2 = onlyPrice.split("/");
                String exactPrice = temp2[0];
                calulatedprice = calulatedprice + Integer.parseInt(exactPrice);
                finalPrice.setText("Rs."+calulatedprice+"/-");
            }

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
                                bookId.add(dataSnapshot.child("BookID").getValue(String.class));
                                bookCategoryId.add(dataSnapshot.child("BookCategory").getValue(String.class));
                                bookSubCategoryId.add(dataSnapshot.child("BookSubCategory").getValue(String.class));
                                itemsCount.add(dataSnapshot.child("Count").getValue(String.class));
                                bookName.add(dataSnapshot.child("BookName").getValue(String.class));
                                bookPrice.add(dataSnapshot.child("Price").getValue(String.class));
                                if (count1 == count2){
                                    Log.v("TAG", "BookID:" + bookId);
                                    displayCart(bookId,bookCategoryId,bookSubCategoryId,itemsCount,bookName,bookPrice);
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
