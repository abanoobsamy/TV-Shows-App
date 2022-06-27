package com.abanoob_samy.tvshowsapp.utilities;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageView, String url) {

        try {
            imageView.setAlpha(0f);

            Picasso.get().load(url).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                    imageView.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (Exception e) {

        }
    }
}
