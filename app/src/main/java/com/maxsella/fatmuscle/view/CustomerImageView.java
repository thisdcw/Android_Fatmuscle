package com.maxsella.fatmuscle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.MyApplication;

public class CustomerImageView extends ShapeableImageView {

    private static final RequestOptions OPTIONS_LOCAL = new RequestOptions()
            .placeholder(R.drawable.ic_default_avatar)
            .fallback(R.drawable.ic_default_avatar)
            .error(R.drawable.ic_default_avatar)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true);

    private static final RequestOptions OPTIONS = new RequestOptions()
            .placeholder(R.drawable.ic_default_avatar)
            .fallback(R.drawable.ic_default_avatar)
            .error(R.drawable.ic_default_avatar);

    public CustomerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @BindingAdapter("url")
    public static void setUrl(ImageView imageView, String url) {
        Glide.with(MyApplication.getInstance()).load(url).apply(OPTIONS_LOCAL).into(imageView);
    }
    @BindingAdapter("localUrl")
    public static void setLocalUrl(ImageView imageView, String url) {
        Glide.with(MyApplication.getInstance()).load(url).apply(OPTIONS_LOCAL).into(imageView);
    }

    @BindingAdapter("networkUrl")
    public static void setNetworkUrl(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(MyApplication.getInstance()).load(url).apply(OPTIONS).into(imageView);
        }
    }
}
