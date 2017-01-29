package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by iamsparsh on 25/1/17.
 */
public class StockWidgetService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new StockRemoteViewsFactory(this.getApplicationContext(),intent));
    }
}
