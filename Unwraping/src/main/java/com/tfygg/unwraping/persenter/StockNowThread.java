package com.tfygg.unwraping.persenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tfygg.unwraping.StockApplication;
import com.tfygg.unwraping.entity.StockNow;
import com.tfygg.unwraping.http.HttpConnection;
import com.tfygg.unwraping.ui.StockActivity;

/**
 * Created by Tong FY on 2016/11/15.
 */
public class StockNowThread extends Thread {
    private static final String TAG = "StockNowThread";

    private String str;
    private StockNow stockNow;

    private String stockId;
    private Handler handler;
    private TableLayout tableLayout;
    private Context mContext;

    private HttpConnection httpConnection;

    public StockNowThread(String id, Handler handler, TableLayout tableLayout) {
        stockId = id;
        this.handler = handler;
        this.tableLayout = tableLayout;
        mContext = StockApplication.getContent();
    }

    @Override
    public void run() {
        httpConnection = new HttpConnection();
        str = httpConnection.queryStockNow(stockId);
        Log.d("StockNowThread", str);
        stockNow = new StockNow(stockId, str);


        handler.post(new Runnable() {
            @Override
            public void run() {

                Double dNow = Double.parseDouble(stockNow.getNow());
                Double dYesterday = Double.parseDouble(stockNow.getCloseYesterday());
                Double dIncrease = dNow - dYesterday;
                Double dPercent = dIncrease / dYesterday * 100;
                int color = Color.BLACK;
                if (dIncrease > 0) {
                    color = Color.RED;
                } else if (dIncrease < 0) {
                    color = Color.rgb(0, 201, 87);
                }

                TableRow row = new TableRow(mContext);
//                row.setBackgroundColor(Color.rgb(248, 248, 255));
                TextView name = new TextView(mContext);
                name.setGravity(Gravity.CENTER);
                name.setTextColor(color);
                name.setTextSize(18);
                name.setText(stockId);
                row.addView(name);

                TextView close = new TextView(mContext);
                close.setGravity(Gravity.CENTER);
                close.setTextColor(color);
                close.setText(stockNow.getNow());
                row.addView(close);

                TextView percent = new TextView(mContext);
                percent.setGravity(Gravity.CENTER);
                percent.setTextColor(color);
                percent.setText(String.format("%.2f", dPercent) + "%");
                row.addView(percent);

                TextView increaseValue = new TextView(mContext);
                increaseValue.setGravity(Gravity.CENTER);
                increaseValue.setTextColor(color);
                increaseValue.setText(String.format("%.2f", dIncrease));

                row.addView(increaseValue);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, StockActivity.class);
                        intent.putExtra("stockId", stockId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                        Toast.makeText(mContext, "onclick", Toast.LENGTH_SHORT).show();
                    }
                });
                tableLayout.addView(row);
            }
        });
    }


}
