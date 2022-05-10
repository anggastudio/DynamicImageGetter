package com.anggastudio.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anggastudio.dynamicimagegetter.DynamicImageGetter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String htmlString = DummyTextHtml.htmlString1;
        TextView textView = findViewById(R.id.textView);
        DynamicImageGetter.with(this)
                .load(htmlString)
                .mode(DynamicImageGetter.FULL_WIDTH)
                .into(textView);
    }
}