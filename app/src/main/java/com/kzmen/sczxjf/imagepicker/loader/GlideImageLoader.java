package com.kzmen.sczxjf.imagepicker.loader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;

import java.io.File;

/**
 * Created by Administrator on 2017/8/12.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(Uri.fromFile(new File(path)))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
