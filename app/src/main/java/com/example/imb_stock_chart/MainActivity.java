package com.example.imb_stock_chart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

// 라인 차트 사용
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
// 봉 차트 사용
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Stockdata.DailyClass;
import Stockdata.IbmClass;
import Utility.Apialpvantage;

public class MainActivity extends AppCompatActivity {

    private CandleStickChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 상단 그래프
        // Request Data
        String apikey = "BX2BR1DMOV59WPAS"; // 임시 키
        Thread thread = new Thread() {
            public void run() {
                Apialpvantage api = new Apialpvantage();
                api.getStockPricesOnline("IBM", apikey);
                api.getDailyPricesOnline("IBM", apikey);
            }
        };
        thread.start();

        CandleStickChart candleStickChart = findViewById(R.id.chart);

        candleStickChart.setHighlightPerDragEnabled(true);

        candleStickChart.setDrawBorders(true);
        candleStickChart.setBorderColor(getResources().getColor(R.color.black));

        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        candleStickChart.requestDisallowInterceptTouchEvent(true);

        XAxis xAxis = candleStickChart.getXAxis();

        xAxis.setDrawGridLines(false); // disable x axis grid lines
        xAxis.setDrawLabels(false);
        rightAxis.setTextColor(Color.WHITE);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = candleStickChart.getLegend();
        l.setEnabled(false);


        ArrayList<CandleEntry> yValsCandleStick = new ArrayList<CandleEntry>();

        // 동기화
        try {
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.d("@ Thread @ ", "join");

        // 데이터 삽입
        int i = 0;
        for(IbmClass ibmdata : Apialpvantage.list) {
            yValsCandleStick.add(new CandleEntry(i, ibmdata.getHigh(), ibmdata.getLow(), ibmdata.getOpen(), ibmdata.getClose()));
            i += 1;
        }
        CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet 1");
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(R.color.gray));
        set1.setShadowWidth(0.8f);
        set1.setDecreasingColor(getResources().getColor(R.color.red));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(getResources().getColor(R.color.green));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.LTGRAY);
        set1.setDrawValues(false);

        CandleData data = new CandleData(set1);

        // set data
        candleStickChart.setData(data);
        candleStickChart.invalidate();

        // 하단 스크롤
        ListView listView = findViewById(R.id.list_item);

        MyAdapter adapter = new MyAdapter();
        // data 추가
        for(DailyClass daydata : Apialpvantage.daily_list){
            adapter.addItem(daydata);
            Log.d("@ Daily Class @ ", daydata.getDay());
        }
        // listView Adapter 연결
        listView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {
        private ArrayList<DailyClass> items = new ArrayList<>();

        public void addItem(DailyClass item){
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public DailyClass getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            MyItemView view = new MyItemView(getApplicationContext());
            DailyClass item = items.get(position);
            view.setTextView(item.getDay());
            view.setTextView2(item.getClose());
            view.setTextView3(item.getOpen());
            view.setTextView4(item.getHigh());
            view.setTextView5(item.getLow());
            return view;
        }
    }
}