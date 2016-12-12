package com.tfygg.unwraping.ui;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tfygg.unwraping.R;
import com.tfygg.unwraping.entity.StockNow;
import com.tfygg.unwraping.persenter.StockDbThread;


/**
 * Created by tfygg on 2016/11/15.
 */

public class StockActivity extends FragmentActivity {
    private String stockId;
    private String retHttp;

    private StockNow stockNow;

    Handler stockHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            retHttp = b.getString("stockNow");
            Log.d("StockActivity", retHttp);
            stockNow = new StockNow(stockId, retHttp);


            StockPageFragment stockPageFragment = StockPageFragment.newInstance(stockNow);

            if (findViewById(R.id.fragment_container) != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, stockPageFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, stockPageFragment).commit();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Intent intent = getIntent();
        stockId = intent.getStringExtra("stockId");

        new StockDbThread(stockId, stockHandler).start();

    }


}
