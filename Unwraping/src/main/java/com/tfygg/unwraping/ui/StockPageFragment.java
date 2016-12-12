package com.tfygg.unwraping.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfygg.unwraping.R;
import com.tfygg.unwraping.entity.StockNow;
import com.tfygg.unwraping.ui.StockPage.PriceChartFragment;
import com.tfygg.unwraping.ui.StockPage.PriceInfoFragment;


public class StockPageFragment extends Fragment {

    TextView tvCompany;
    TextView tvPrice;
    TextView tvTradeDate;
    TextView tvTradeTime;

    ViewPager mViewPager;
    StockInfoPagerAdapter mStockInfoPagerAdapter;

    StockNow stock;

    public StockPageFragment() {
    }

    public static StockPageFragment newInstance(StockNow stock) {

        StockPageFragment fragment = new StockPageFragment();
        fragment.stock = stock;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stockpage, container, false);

        tvCompany = (TextView) rootView.findViewById(R.id.tvCompany);
        tvPrice = (TextView) rootView.findViewById(R.id.tvPrice);
        tvTradeDate = (TextView) rootView.findViewById(R.id.tvTradeDate);
        tvTradeTime = (TextView) rootView.findViewById(R.id.tvTradeTime);

        // Sets company name, price, etc
        tvCompany.setText(stock.getName());
        tvPrice.setText(stock.getNow());
        tvTradeDate.setText(stock.getDate());
        tvTradeTime.setText("Updated: " + stock.getTime());


        // Sets up the ViewPager
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        mStockInfoPagerAdapter =
                new StockInfoPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mStockInfoPagerAdapter);
        mViewPager.setCurrentItem(0);          // Set default starting page to the 1st item
        mViewPager.setOffscreenPageLimit(2);   // Keep all pages loaded

        return rootView;
    }


    public class StockInfoPagerAdapter extends FragmentStatePagerAdapter {

        public StockInfoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return PriceInfoFragment.newInstance(stock);
                case 1:
                    return PriceChartFragment.newInstance(stock);

                default:
                    return PriceInfoFragment.newInstance(stock);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getResources().getString(R.string.price_info);
                case 1:
                    return getResources().getString(R.string.charts);

                default:
                    return "What";
            }
        }
    }

}