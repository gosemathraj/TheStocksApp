package com.udacity.stockhawk.Utility;

import android.support.v4.util.Pair;

import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by iamsparsh on 29/1/17.
 */

public class Utils {

    public static Utils utils;

    public static Utils getInstance(){

        if(utils == null){
            utils = new Utils();
            return utils;
        }
        return utils;
    }

    public static List<Entry> getFormattedHistory(String history) {
        List<Entry> entries = new ArrayList<>();
        List<Float> timeData = new ArrayList<>();
        List<Float> stockPrice = new ArrayList<>();
        String[] dataPairs = history.split("\\$");

        for (String pair : dataPairs) {
            String[] entry = pair.split(":");
            timeData.add(Float.valueOf(entry[0]));
            stockPrice.add(Float.valueOf(entry[1]));
        }
        Collections.reverse(timeData);
        Collections.reverse(stockPrice);
        for (int i = 0; i < timeData.size(); i++) {
            entries.add(new Entry(timeData.get(i),stockPrice.get(i)));
        }
        return entries;
    }

    public String dateFormatter(Float date){

        Date curDateTime = new Date();
        curDateTime.setTime(date.longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d");
        return formatter.format(curDateTime);

    }
}
