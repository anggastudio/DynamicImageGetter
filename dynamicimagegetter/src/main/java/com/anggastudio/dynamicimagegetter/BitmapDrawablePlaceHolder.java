package com.anggastudio.dynamicimagegetter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.anggastudio.dynamicimagegetter.DynamicImageGetter.FULL_WIDTH;
import static com.anggastudio.dynamicimagegetter.DynamicImageGetter.INLINE_TEXT;

public class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

    private final Context context;
    private final TextView textView;
    private final int imageMode;
    protected Drawable drawable;

    public BitmapDrawablePlaceHolder(Context context, TextView textView, int imageMode){
        this.context = context;
        this.textView = textView;
        this.imageMode = imageMode;
    }

    @Override
    public void draw(final Canvas canvas) {
        if (drawable != null) {
            checkBounds();
            drawable.draw(canvas);
        }
    }

    public void setDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            this.drawable = drawable;
            checkBounds();
        }
    }

    private void checkBounds() {
        switch (imageMode){
            case FULL_WIDTH:
                setFullTextViewWidth();
                break;
            case INLINE_TEXT:
                setInlineText();
                break;
            default:
                setDefaultBound();
                break;
        }
        textView.setText(textView.getText()); //refresh text
    }

    private void setFullTextViewWidth() {
        float defaultProportion = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
        int padding = textView.getCompoundPaddingEnd() + textView.getCompoundPaddingStart();
        int width = textView.getWidth() - padding;
        int height = (int) ((float) width / defaultProportion);
        setBounds(0, 0, textView.getWidth(), height); //set to full width
        // fit in an image
        drawable.setBounds(0,0, width, height);
    }

    private void setInlineText() {
        float defaultProportion = (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth();
        int height = textView.getLineHeight();
        int width = (int) ((float) height / defaultProportion);
        setBounds(0, 0, width, height);
        drawable.setBounds(0,0, width, height);
    }

    private void setDefaultBound() {
        setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    //------------------------------------------------------------------//

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        setDrawable(errorDrawable);
        e.printStackTrace();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        setDrawable(placeHolderDrawable);
    }

    //------------------------------------------------------------------//
}
