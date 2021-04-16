package Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Stockdata.DailyClass;
import Stockdata.IbmClass;

public class Apialpvantage {

    public static Context alp_context;
    public static ArrayList<IbmClass> list = new ArrayList<>();
    public static ArrayList<DailyClass> daily_list = new ArrayList<>();

    public void getDailyPricesOnline(final String companyCode, final String apiKey) {
        String inputLine;
        String result;

        boolean successful = false;
        while(!successful){
            try{
                String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
                        +companyCode+
                        "&apikey="
                        +apiKey;

                URL api = new URL(url);
                HttpURLConnection connection =(HttpURLConnection) api.openConnection();
                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                result = stringBuilder.toString();

                JSONObject jsonObject = new JSONObject(result);
                JSONObject time_s = jsonObject.getJSONObject("Time Series (Daily)");

                // Json Object 순회
                Iterator<?> keys =  time_s.keys();
                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    if(time_s.get(key) instanceof JSONObject) {
                        JSONObject value = (JSONObject) time_s.get(key);

                        DailyClass info = new DailyClass();

                        String TAG = "@ test @";
                        Log.d(TAG, key);

                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date to = transFormat.parse(key);
                        info.setDay(key);
                        Log.d(TAG, to.toString());

                        float tmp = Float.parseFloat(value.getString("1. open"));
                        info.setOpen(tmp);
//                        Log.d(TAG, tmp.toString());

                        tmp = Float.parseFloat(value.getString("2. high"));
                        info.setHigh(tmp);
//                        Log.d(TAG, tmp.toString());

                        tmp = Float.parseFloat(value.getString("3. low"));
                        info.setLow(tmp);
//                        Log.d(TAG, tmp.toString());

                        tmp = Float.parseFloat(value.getString("4. close"));
                        info.setClose(tmp);
//                        Log.d(TAG, tmp.toString());

                        Integer tmp2 = Integer.parseInt(value.getString("5. volume"));
                        info.setVolume(tmp2);
//                        Log.d(TAG, tmp2.toString());

                        daily_list.add(info);
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
            successful = true; // 일단 한번만 실행
        } // while successful
    }

    public void getStockPricesOnline(final String companyCode, final String apiKey){

        String inputLine;
        String result;

        boolean successful = false;
        while(!successful){
            try{
                String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="
                        +companyCode+
                        "&interval=5min&apikey="
                        +apiKey+
                        "&outputsize=compact";

                URL api = new URL(url);
                HttpURLConnection connection =(HttpURLConnection) api.openConnection();
                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                result = stringBuilder.toString();

                JSONObject jsonObject = new JSONObject(result);
                JSONObject time_s = jsonObject.getJSONObject("Time Series (5min)");


                // Json Object 순회
                Iterator<?> keys =  time_s.keys();
                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    if(time_s.get(key) instanceof JSONObject) {
                        JSONObject value = (JSONObject) time_s.get(key);

                        IbmClass info = new IbmClass();

                        String TAG = "@ test @";
                        Log.d(TAG, key);

                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date to = transFormat.parse(key);
                        info.setDaytime(to);
                        Log.d(TAG, to.toString());

                        float tmp = Float.parseFloat(value.getString("1. open"));
                        info.setOpen(tmp);
//                        Log.d(TAG, tmp.toString());

                        tmp = Float.parseFloat(value.getString("2. high"));
                        info.setHigh(tmp);
//                        Log.d(TAG, tmp.toString());

                        tmp = Float.parseFloat(value.getString("3. low"));
                        info.setLow(tmp);
//                        Log.d(TAG, tmp.toString());

                        tmp = Float.parseFloat(value.getString("4. close"));
                        info.setClose(tmp);
//                        Log.d(TAG, tmp.toString());

                        Integer tmp2 = Integer.parseInt(value.getString("5. volume"));
                        info.setVolume(tmp2);
//                        Log.d(TAG, tmp2.toString());

                        list.add(info);
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
            successful = true; // 일단 한번만 실행
        } // while successful
    }
}
