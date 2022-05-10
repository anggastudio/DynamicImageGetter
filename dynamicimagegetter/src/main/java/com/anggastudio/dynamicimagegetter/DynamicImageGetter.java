package com.anggastudio.dynamicimagegetter;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DynamicImageGetter implements Html.ImageGetter {
    public static final int FULL_WIDTH = 1;
    public static final int INLINE_TEXT = 2;
    private static DynamicImageGetter dynamicImageGetter;
    private TextView textView;
    private Picasso picasso;
    private Context mContext;
    private int imageMode;
    private int imageError;
    private int imagePlaceholder;
    private boolean isImageErrorAvailable;
    private boolean isPlaceholderAvailable;
    private String htmlString;

    public DynamicImageGetter(Context context) {
        this.mContext = context;
        this.picasso = Picasso.get();
        this.imageMode = 0;
    }

    public static DynamicImageGetter with(Context context) {
        dynamicImageGetter = new DynamicImageGetter(context);
        return dynamicImageGetter;
    }

    public void setImageMode(int imageMode) {
        this.imageMode = imageMode;
    }

    public void setImageError(int imageError) {
        this.imageError = imageError;
        this.isImageErrorAvailable = true;
    }

    public void setImagePlaceholder(int imagePlaceholder) {
        this.imagePlaceholder = imagePlaceholder;
        this.isPlaceholderAvailable = true;
    }

    @Override
    public Drawable getDrawable(String source) {

        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder(mContext, textView, imageMode);
        RequestCreator requestCreator = picasso.load(source);

        if (isImageErrorAvailable) {
            requestCreator.error(imageError);
        }

        if (isPlaceholderAvailable) {
            requestCreator.placeholder(imagePlaceholder);
        }

        requestCreator.into(drawable);

        return drawable;
    }

    public DynamicImageGetter load(String htmlString) {
        this.htmlString = htmlString;
        return dynamicImageGetter;
    }

    public void into(TextView textView) {
        this.textView = textView;
        Spanned text;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text = Html.fromHtml(htmlString, FROM_HTML_MODE_LEGACY, dynamicImageGetter, null);
        } else {
            text = Html.fromHtml(htmlString, dynamicImageGetter, null);
        }
        textView.setText(text);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public DynamicImageGetter mode(int imageMode) {
        this.imageMode = imageMode;
        return dynamicImageGetter;
    }
}
