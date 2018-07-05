package com.anggastudio.dynamicimagegetter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

public class DynamicImageGetter implements Html.ImageGetter {
    public static final int FULL_WIDTH = 1;
    public static final int INLINE_TEXT = 2;
    private TextView textView;
    private Picasso picasso;
    private Context mContext;
    private int imageMode;
    private int imageError;
    private int imagePlaceholder;
    private boolean isImageErrorAvailable;
    private boolean isPlaceholderAvailable;

    public DynamicImageGetter(Context mContext, @NonNull TextView textView, int imageMode) {
        this.textView = textView;
        this.mContext = mContext;
        this.picasso = Picasso.get();
        this.imageMode = imageMode;
    }

    public DynamicImageGetter(Context mContext, @NonNull TextView textView) {
        this.textView = textView;
        this.mContext = mContext;
        this.picasso = Picasso.get();
        this.imageMode = 0;
    }

    public void setImageMode(int imageMode) {
        this.imageMode = imageMode;
    }

    public void setImageError(int imageError){
        this.imageError = imageError;
        this.isImageErrorAvailable = true;
    }

    public void setImagePlaceholder(int imagePlaceholder){
        this.imagePlaceholder = imagePlaceholder;
        this.isPlaceholderAvailable = true;
    }

    @Override
    public Drawable getDrawable(String source) {

        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder(mContext, textView, imageMode);
        RequestCreator requestCreator = picasso.load(source);

        if(isImageErrorAvailable){
            requestCreator.error(imageError);
        }

        if(isPlaceholderAvailable){
            requestCreator.placeholder(imagePlaceholder);
        }

        requestCreator.into(drawable);

        return drawable;
    }
}
