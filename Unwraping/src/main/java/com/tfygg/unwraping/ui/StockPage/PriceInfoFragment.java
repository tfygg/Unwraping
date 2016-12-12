package com.tfygg.unwraping.ui.StockPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfygg.unwraping.R;
import com.tfygg.unwraping.entity.StockNow;

public class PriceInfoFragment extends Fragment {

    TextView tvPrevClose;
    TextView tvOpen;
    TextView tvVolume;
    TextView tvAvgDailyVolume;
    TextView tvDaysRange;
    TextView tvYearRange;
    TextView tvMarketCap;
    TextView tvOneYearTarget;


    StockNow stock;

    public PriceInfoFragment() {
    }

    public static PriceInfoFragment newInstance(StockNow stock) {

        PriceInfoFragment fragment = new PriceInfoFragment();
        fragment.stock = stock;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_priceinfo, container, false);

        tvPrevClose = (TextView) rootView.findViewById(R.id.tvPrevClose);
        tvOpen = (TextView) rootView.findViewById(R.id.tvOpen);
        tvAvgDailyVolume = (TextView) rootView.findViewById(R.id.tvAvgDailyVolume);
        tvVolume = (TextView) rootView.findViewById(R.id.tvVolume);
        tvDaysRange = (TextView) rootView.findViewById(R.id.tvDaysRange);
        tvYearRange = (TextView) rootView.findViewById(R.id.tvYearRange);
        tvMarketCap = (TextView) rootView.findViewById(R.id.tvMarketCap);
        tvOneYearTarget = (TextView) rootView.findViewById(R.id.tvOneYearTarget);

        tvPrevClose.setText(stock.getCloseYesterday());
        tvOpen.setText(stock.getOpen());
        String vol = stock.getVolume();
        Log.d("volume", vol);
        tvVolume.setText(vol);

        return rootView;
    }

}
