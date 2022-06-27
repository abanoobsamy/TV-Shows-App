package com.abanoob_samy.tvshowsapp.adapaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.databinding.ItemContainerSliderImageBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderHolder> {

    private String[] sliderImage;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {

            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerSliderImageBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_slider_image, parent, false);

        return new ImageSliderHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderHolder holder, int position) {

        holder.setBindingSlider(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    public class ImageSliderHolder extends RecyclerView.ViewHolder {

        ItemContainerSliderImageBinding binding;

        public ImageSliderHolder(@NonNull ItemContainerSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setBindingSlider(String imageUrl) {

            binding.setImageURL(imageUrl);

        }
    }
}
