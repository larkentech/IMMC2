package com.larken.immc2.AdapterClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kodmap.app.library.PopopDialogBuilder;
import com.larken.immc2.BookDetailsActivity;
import com.larken.immc2.ModalClasses.SliderItem;
import com.larken.immc2.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends
        SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book_image, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem)
                .fitCenter()

                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new PopopDialogBuilder(context)
                        // Set list like as option1 or option2 or option3
                        .setList(mSliderItems,0)
                        // or setList with initial position that like .setList(list,position)
                        // Set dialog header color
                        .setHeaderBackgroundColor(android.R.color.white)
                        // Set dialog background color
                        .setDialogBackgroundColor(R.color.color_dialog_bg)
                        // Set close icon drawable
                        .setCloseDrawable(R.drawable.ic_close_black_24dp)
                        // Set loading view for pager image and preview image
                        .setLoadingView(R.layout.loading_view)
                        // Set dialog style
                        .setDialogStyle(R.style.DialogStyle)
                        // Choose selector type, indicator or thumbnail
                        .showThumbSlider(true)
                        // Set image scale type for slider image
                        .setSliderImageScaleType(ImageView.ScaleType.FIT_XY)
                        // Set indicator drawable
                        .setSelectorIndicator(R.drawable.sample_indicator_selector)
                        // Enable or disable zoomable
                        .setIsZoomable(true)
                        // Build Km Slider Popup Dialog
                        .build();
                dialog.show();
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
