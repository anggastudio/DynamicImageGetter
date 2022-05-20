package com.anggastudio.dynamicimagegetter;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

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

    public DynamicImageGetter setImageError(int imageError) {
        this.imageError = imageError;
        this.isImageErrorAvailable = true;
        return dynamicImageGetter;
    }

    public DynamicImageGetter setImagePlaceholder(int imagePlaceholder) {
        this.imagePlaceholder = imagePlaceholder;
        this.isPlaceholderAvailable = true;
        return dynamicImageGetter;
    }

    @Override
    public Drawable getDrawable(String source) {
        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder(mContext, textView, imageMode);
        RequestCreator requestCreator = getRequestCreator(source);
        if (requestCreator != null) {
            if (isImageErrorAvailable) {
                requestCreator.error(imageError);
            }
            if (isPlaceholderAvailable) {
                requestCreator.placeholder(imagePlaceholder);
            }
            requestCreator.into(drawable);
        }
        return drawable;
    }

    private RequestCreator getRequestCreator(String source) {
        RequestCreator requestCreator = null;
        if (source.toLowerCase(Locale.ROOT).contains("data:image")) {
            // source is base64
            try {
                source = source.split(",")[1];
                File imageFile = getFileFromImageBase64(source);
                requestCreator = picasso.load(imageFile);
            } catch (Exception e) {
                // get exception
                Log.e("FAILED LOAD IMAGE", e.getLocalizedMessage());
            }
        } else {
            // source is url
            requestCreator = picasso.load(source);
        }
        return requestCreator;
    }

    private File getFileFromImageBase64(String imageBase64) {
        byte[] decodedString = Base64.decode(imageBase64.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        File file = new File(mContext.getCacheDir(), "filename");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(decodedString);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
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
