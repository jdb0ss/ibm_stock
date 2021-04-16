package com.example.imb_stock_chart;

import android.content.Context;
import androidx.annotation.Nullable; // check
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyItemView extends LinearLayout {
    TextView textView, textView2, textView3, textView4, textView5;
    public MyItemView(Context context) {
        super(context); init(context);
    }

    public MyItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs); init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_dailyitem, this, true);
        textView = findViewById(R.id.day);
        textView2 = findViewById(R.id.close);
        textView3 = findViewById(R.id.open);
        textView4 = findViewById(R.id.high);
        textView5 = findViewById(R.id.low);
    }

    public void setTextView(String day) {
        textView.setText(day);
    }

    public void setTextView2(float close) {
        textView2.setText(Float.toString(close));
    }

    public void setTextView3(float open) {
        textView3.setText(Float.toString(open));
    }

    public void setTextView4(float high) {
        textView4.setText(Float.toString(high));
    }

    public void setTextView5(float low) {
        textView5.setText(Float.toString(low));
    }

}