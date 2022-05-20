package com.anggastudio.sample;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anggastudio.dynamicimagegetter.DynamicImageGetter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        String htmlString = DummyTextHtml.htmlString1;
//        String htmlString = getString(R.string.htmltextbase64);
        String htmlString = getRawResource(R.raw.html_string_test_data);
        TextView textView = findViewById(R.id.textView);
        DynamicImageGetter.with(this)
                .load(htmlString)
                .mode(DynamicImageGetter.FULL_WIDTH)
                .setImagePlaceholder(R.drawable.ic_launcher_background)
                .into(textView);

//        loadToImageView();

    }

    private void loadToImageView() {
        ImageView imageView = findViewById(R.id.imageView);
        String imageBase64 = getString(R.string.imagebase64);
        File imageFile = getFileFromImageBase64(imageBase64);
        Picasso.get().load(imageFile).into(imageView);
    }

    private File getFileFromImageBase64(String imageBase64) {
        byte[] decodedString = Base64.decode(imageBase64.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        File file = new File(getCacheDir(), "filename");
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

    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "image", null);
        return Uri.parse(path);
    }

    private String getRawResource(int resource) {
        String res = null;
        InputStream is = getResources().openRawResource(resource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1];
        try {
            while (is.read(b) != -1) {
                baos.write(b);
            }
            ;
            res = baos.toString();
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}