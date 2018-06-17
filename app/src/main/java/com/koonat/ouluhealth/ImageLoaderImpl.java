package com.koonat.ouluhealth;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoaderImpl implements ImageLoader {

    @Override
    public void loadImageInto(String url, ImageView view) {
        Picasso.get().load(url).into(view);
    }
}
