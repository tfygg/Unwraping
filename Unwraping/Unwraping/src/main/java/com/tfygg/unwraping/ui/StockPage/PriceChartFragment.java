package com.tfygg.unwraping.ui.StockPage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.tfygg.unwraping.R;
import com.tfygg.unwraping.entity.StockNow;
import com.tfygg.unwraping.http.HttpConnection;


/**
 * This fragment retrieves and displays the price chart for the current stock from Yahoo
 */

public class PriceChartFragment extends Fragment {

    RelativeLayout rlPriceChartContainer;
    LinearLayout llChartProgress;
    ImageView ivPriceChart;

    RadioButton rbTime1d;
    RadioButton rbTime5d;
    RadioButton rbTime3m;
    RadioButton rbTime6m;
    RadioButton rbTime1y;
    RadioButton rbTime5y;
    RadioButton rbTimeMax;

    RadioButton rbPlotTypeLine;
    RadioButton rbPlotTypeBar;
    RadioButton rbPlotTypeCandle;

    StockNow stock;
    String stockId;

    private HttpConnection httpConnection;


    public PriceChartFragment() {
    }


    public static PriceChartFragment newInstance(StockNow stock) {

        PriceChartFragment fragment = new PriceChartFragment();
        fragment.stock = stock;
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        httpConnection = new HttpConnection();

        View rootView = inflater.inflate(R.layout.fragment_pricechart, container, false);

        rlPriceChartContainer = (RelativeLayout) rootView.findViewById(R.id.rlPriceChartContainer);
        ivPriceChart = (ImageView) rootView.findViewById(R.id.ivPriceChart);

        llChartProgress = (LinearLayout) rootView.findViewById(R.id.llChartProgress);

        rbTime1d = (RadioButton) rootView.findViewById(R.id.rbTime1d);
        rbTime5d = (RadioButton) rootView.findViewById(R.id.rbTime5d);
        rbTime3m = (RadioButton) rootView.findViewById(R.id.rbTime3m);
        rbTime6m = (RadioButton) rootView.findViewById(R.id.rbTime6m);
        rbTime1y = (RadioButton) rootView.findViewById(R.id.rbTime1y);
        rbTime5y = (RadioButton) rootView.findViewById(R.id.rbTime5y);
        rbTimeMax = (RadioButton) rootView.findViewById(R.id.rbTimeMax);
        rbPlotTypeLine = (RadioButton) rootView.findViewById(R.id.rbPlotTypeLine);
        rbPlotTypeBar = (RadioButton) rootView.findViewById(R.id.rbPlotTypeBar);
        rbPlotTypeCandle = (RadioButton) rootView.findViewById(R.id.rbPlotTypeCandle);

        stockId = stock.getId();
        if (stockId.equals("000001")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("6")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        }

        rbTime1d.setOnClickListener(new OnCandleRadioButtonListener("daily"));
        rbTime5d.setOnClickListener(new OnCandleRadioButtonListener("weekly"));
        rbTime3m.setOnClickListener(new OnCandleRadioButtonListener("monthly"));

        rbPlotTypeLine.setOnClickListener(new OnCandleRadioButtonListener("min"));
        rbPlotTypeCandle.setOnClickListener(new OnCandleRadioButtonListener("daily"));

        String url = "http://image.sinajs.cn/newchart/min/n/" + stockId + ".gif";
        Log.d("url candle", url);
        new GetPriceChartAsyncTask().execute(url);

        return rootView;
    }

    private class OnCandleRadioButtonListener implements View.OnClickListener {

        private String time;

        public OnCandleRadioButtonListener(String time) {
            this.time = time;
        }

        @Override
        public void onClick(View v) {
            String url = "http://image.sinajs.cn/newchart/" + time + "/n/" + stockId + ".gif";
            Log.d("url candle", url);
            new GetPriceChartAsyncTask().execute(url);
        }
    }

    class GetPriceChartAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            llChartProgress.setVisibility(View.VISIBLE);
            ivPriceChart.setVisibility(View.INVISIBLE);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Bitmap b = httpConnection.getImageBitmap(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            llChartProgress.setVisibility(View.INVISIBLE);
            ivPriceChart.setVisibility(View.VISIBLE);

            if (result != null) {
                ivPriceChart.setImageBitmap(result);
            }
        }
    }

}
