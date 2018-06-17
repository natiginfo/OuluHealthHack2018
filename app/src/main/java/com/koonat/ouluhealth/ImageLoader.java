package com.koonat.ouluhealth;

import android.widget.ImageView;

public interface ImageLoader {
    public static ImageLoader getLoader() {
        return new ImageLoaderImpl();
    }

    void loadImageInto(String url, ImageView view);
}
