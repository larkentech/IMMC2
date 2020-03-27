package com.larken.immc2.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.larken.immc2.Fragments.AccountFragment;
import com.larken.immc2.Fragments.MyOrdersFragment;
import com.larken.immc2.ModalClasses.OrderModal;
import com.larken.immc2.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<OrderModal> {
    List<String> tnxID;
    Context context;
    Fragment fragment;

    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<OrderModal> objects) {
        super(context, resource, objects);

}

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_order, parent, false);

        }

        OrderModal orderModal = getItem(position);

        CardView selected = convertView.findViewById(R.id.orderCard);
        TextView BookName = convertView.findViewById(R.id.bookName);
        TextView Address = convertView.findViewById(R.id.address);
        TextView FinalPrice = convertView.findViewById(R.id.bookPrice);
        TextView PhoneNumber = convertView.findViewById(R.id.phno);
        TextView Count = convertView.findViewById(R.id.quantity);
        TextView OrderDate = convertView.findViewById(R.id.orderDate);
        TextView TxnID = convertView.findViewById(R.id.txnNum);


        BookName.setText(orderModal.getBookName());
        Address.setText(orderModal.getAddress());
        FinalPrice.setText("Rs." + orderModal.getFinalPrice() + "/- Paid");
        Count.setText("Qty: " + orderModal.getCount());
        PhoneNumber.setText("Ph: "+ orderModal.getPhoneNumber());
        OrderDate.setText(orderModal.getOrderDate());
        TxnID.setText(orderModal.getTxnID().toString());

        return convertView;
    }



}
