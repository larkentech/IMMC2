package com.larken.immc2.AdapterClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kodmap.app.library.PopopDialogBuilder;
import com.larken.immc2.DetailsContainerActivity;
import com.larken.immc2.Fragments.HomeFragment;
import com.larken.immc2.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class OfferSliderAdapter extends
        SliderViewAdapter<OfferSliderAdapter.SliderAdapterVH>{

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();
    private List<String> mCategory= new ArrayList<>();
    private List<String> mSubCategory= new ArrayList<>();

    public OfferSliderAdapter(Context context) {
        this.context = context;
    }


    public void renewItems(List<String> sliderItems,List<String> category, List<String> subCategory) {
        this.mSliderItems = sliderItems;
        this.mCategory=category;
        this.mSubCategory=subCategory;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem,String category,String subCategory) {
        this.mSliderItems.add(sliderItem);
        this.mCategory.add(category);
        this.mSubCategory.add(subCategory);
        notifyDataSetChanged();
    }

    @Override
    public OfferSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_single, null);
        return new OfferSliderAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(OfferSliderAdapter.SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem)
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailsContainerActivity.class);
                intent.putExtra("Category",mCategory.get(position));
                intent.putExtra("SubCategory",mSubCategory.get(position));
                context.startActivity(intent);
                

            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.offer_image_single);
            this.itemView = itemView;
        }
    }

}
