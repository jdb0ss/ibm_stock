package Stockdata;

import android.content.Context;

import java.util.Date;

public class IbmClass {

    private Date daytime;
    private float cur_time;
    private float open;
    private float high;
    private float low;
    private float close;
    private Integer volume;

    public Date getDaytime() {
        return daytime;
    }

    public void setDaytime(Date daytime) {
        this.daytime = daytime;
    }

    public float getCur_time() {
        return cur_time;
    }

    public void setCur_time(float cur_time) {
        this.cur_time = cur_time;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}
