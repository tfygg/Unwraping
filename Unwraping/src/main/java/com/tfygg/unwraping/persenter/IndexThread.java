package com.tfygg.unwraping.persenter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.tfygg.unwraping.entity.StockNow;
import com.tfygg.unwraping.http.HttpConnection;

/**
 * Created by Tong FY on 2016/11/15.
 */
public class IndexThread extends Thread {
    private static final String TAG = "IndexThread";

    private StockNow stockNow;
    private String str;

    private String stockId;
    private Handler handler;
    private TextView textView;
    private TextView textViewChange;


    private HttpConnection httpConnection;


    public IndexThread(String stockId, Handler handler, TextView textView, TextView textView2) {
        this.stockId = stockId;
        this.handler = handler;
        this.textView = textView;
        this.textViewChange = textView2;
    }


    @Override
    public void run() {
        httpConnection = new HttpConnection();
        str = httpConnection.queryStockIndex(stockId);
        stockNow = new StockNow(stockId, str);

        handler.post(new Runnable() {
                         @Override
                         public void run() {

                             stockNow = new StockNow(stockId, str);

                             Double dNow = Double.parseDouble(stockNow.getNow());
                             Double dYesterday = Double.parseDouble(stockNow.getCloseYesterday());
                             Double dIncrease = dNow - dYesterday;
                             Double dPercent = dIncrease / dYesterday * 100;
                             String change = String.format("%.2f", dPercent) + "% " + String.format("%.2f", dIncrease);
                             Log.d("change", change);

                             int color = Color.BLACK;
                             if (dIncrease > 0) {
                                 color = Color.RED;
                             } else if (dIncrease < 0) {
                                 color = Color.rgb(0, 201, 87);
                             }
                             textView.setText(stockNow.getNow());
                             textView.setTextColor(color);
                             textViewChange.setText(change);

                         }
                     }
        );
    }

}
