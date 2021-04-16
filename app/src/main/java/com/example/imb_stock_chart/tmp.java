//package com.example.imb_stock_chart;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import Utility.Apialpvantage;
//
//public class MainActivity extends AppCompatActivity {
//
//    private LineChart chart;
//    private Thread c_thread;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Request Data
//        Thread thread = new Thread() {
//            public void run() {
//                Apialpvantage api = new Apialpvantage();
//                api.getStockPricesOnline("IBM", "BX2BR1DMOV59WPAS");
//            }
//        };
//        thread.start();
//
//        chart = (LineChart) findViewById(R.id.chart);
//        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        chart.getAxisRight().setEnabled(false);
//        chart.getLegend().setTextColor(Color.WHITE);
//        chart.animateXY(2000, 2000);
//        chart.invalidate();
//
//        LineData data = new LineData();
//        chart.setData(data);
//
//        feedMultiple();
//    }
//
//    private void feedMultiple() {
//        if(c_thread != null)
//            c_thread.interrupt();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                addEntry();
//            }
//        };
//
//        c_thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    runOnUiThread(runnable);
//                    try {
//                        Thread.sleep(10000);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        c_thread.start();
//    }
//
//    private void addEntry() {
//        LineData data = chart.getData();
//        if(data != null){
//            ILineDataSet set = data.getDataSetByIndex(0);
//
//            if(set == null){
//                set = createSet();
//                data.addDataSet(set);
//            }
//
//            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
//
//            data.notifyDataChanged();
//
//            chart.notifyDataSetChanged();
//            chart.setVisibleXRangeMaximum(10); // 최대 데이터 표시 수
//            chart.moveViewToX(data.getEntryCount());
//        }
//    }
//
//    private LineDataSet createSet(){
//
//        LineDataSet set = new LineDataSet(null, "Dynamic Data");
//        set.setFillAlpha(110);
//        set.setFillColor(Color.parseColor("#d7e7fa"));
//        set.setColor(Color.parseColor("#0B80C9"));
//        set.setCircleColor(Color.parseColor("#FFA1B4DC"));
//        set.setCircleHoleColor(Color.BLUE);
//        set.setValueTextColor(Color.WHITE);
//        set.setDrawValues(false);
//        set.setLineWidth(2);
//        set.setCircleRadius(6);
//        set.setDrawCircleHole(false);
//        set.setDrawCircles(false);
//        set.setValueTextSize(9f);
//        set.setDrawFilled(true);
//
//        set.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set.setHighLightColor(Color.rgb(244, 117, 117));
//
//        return set;
//    }
//}