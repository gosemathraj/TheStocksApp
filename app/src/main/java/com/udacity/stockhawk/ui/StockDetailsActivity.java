package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.Utils.Parser;
import com.udacity.stockhawk.Utils.XAxisDateFormatter;
import com.udacity.stockhawk.Utils.YAxisPriceFormatter;
import com.udacity.stockhawk.data.Contract;

import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by iamsparsh on 26/1/17.
 */

public class StockDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static int LOADER_ID = 0;
    private Uri stockUri = null;
    private String historyQuote = null;
    private LineChart linechart;
    public String dateFormat = "MMM";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        stockUri = getIntent().getData();
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        linechart = (LineChart) findViewById(R.id.linechart);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (stockUri != null) {
            return new CursorLoader(
                    this,
                    stockUri,
                    Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {
            try {
                historyQuote = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
                setUpLineChart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setUpLineChart() {
        Pair<Float, List<Entry>> result = Parser.getFormattedStockHistory(historyQuote);
        List<Entry> dataPairs = result.second;
        Float referenceTime = result.first;
        LineDataSet dataSet = new LineDataSet(dataPairs, "");
        dataSet.setColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setLineWidth(2f);
        dataSet.setDrawHighlightIndicators(false);
        dataSet.setCircleColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setHighLightColor(getResources().getColor(R.color.colorPrimary));
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        linechart.setData(lineData);

        XAxis xAxis = linechart.getXAxis();
        xAxis.setValueFormatter(new XAxisDateFormatter(dateFormat, referenceTime));
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(getResources().getColor(R.color.colorPrimary));
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
        xAxis.setTextSize(12f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxisRight = linechart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxis = linechart.getAxisLeft();
        yAxis.setValueFormatter(new YAxisPriceFormatter());
        yAxis.setDrawGridLines(false);
        yAxis.setAxisLineColor(getResources().getColor(R.color.colorPrimary));
        yAxis.setAxisLineWidth(1.5f);
        yAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
        yAxis.setTextSize(12f);


        Legend legend = linechart.getLegend();
        legend.setEnabled(false);


        //disable all interactions with the graph
        linechart.setDragEnabled(false);
        linechart.setScaleEnabled(false);
        linechart.setDragDecelerationEnabled(false);
        linechart.setPinchZoom(false);
        linechart.setDoubleTapToZoomEnabled(false);
        Description description = new Description();
        description.setText(" ");
        linechart.setDescription(description);
        linechart.setExtraOffsets(10, 0, 0, 10);
        linechart.animateX(1500, Easing.EasingOption.Linear);
    }
}
