package com.udacity.stockhawk.Utility;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iamsparsh on 29/1/17.
 */

public class DateFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        Date curDateTime = new Date();
        curDateTime.setTime(Float.valueOf(value).longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d");
        return formatter.format(curDateTime);
    }
}
