package com.tfygg.unwraping.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tfygg.unwraping.R;
import com.tfygg.unwraping.StockApplication;
import com.tfygg.unwraping.persenter.IndexThread;
import com.tfygg.unwraping.persenter.StockNowThread;
import com.tfygg.unwraping.sharedperfences.PersistProvider;

public class MainActivity extends Activity {
    private MenuItem searchItem;

    private PersistProvider persistProvider;
    private String spStockIds = "StockIds";
    private StringBuffer spStringBuffer;

    private final static String ShIndex = "000001";
    private final static String SzIndex = "399001";
    private final static String ChuangIndex = "399006";

    private TableLayout tableLayout;


    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        persistProvider = PersistProvider.getInstance(this);
        spStringBuffer = new StringBuffer();

        initIndex(ShIndex);
        initIndex(SzIndex);
        initIndex(ChuangIndex);
        initSharePreferences();
        initStockTable();

    }

    private void initSharePreferences() {
        String str = persistProvider.getStringPersist(spStockIds, null);
        if (str != null) {
            String[] strArray = str.split("\\,");
            for (String id : strArray) {
                String stockId = id;
                Log.i("queryStock ", id);

                tableLayout = (TableLayout) findViewById(R.id.stock_table);
                new StockNowThread(id, myHandler, tableLayout).start();
            }
        }
    }

    private void saveStocksToPreferences(String id) {
        spStringBuffer.append(id);
        spStringBuffer.append(",");
        String str = spStringBuffer.toString();
        persistProvider.persist(spStockIds, str);
    }

    private boolean isExisted(String stockId) {
        String str = persistProvider.getStringPersist(spStockIds, "");
        if (str != "") {
            String[] strArray = str.split("\\,");
            for (String id : strArray) {
                if (stockId.endsWith(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initStockTable() {
        tableLayout = (TableLayout) findViewById(R.id.stock_table);

        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        // Title
        TableRow rowTitle = new TableRow(this);
        TextView nameTitle = new TextView(this);
        nameTitle.setGravity(Gravity.CENTER);
        nameTitle.setText(getResources().getString(R.string.stock_name_title));
        rowTitle.addView(nameTitle);
        TextView nowTitle = new TextView(this);
        nowTitle.setGravity(Gravity.CENTER);
        nowTitle.setText(getResources().getString(R.string.stock_now_title));
        rowTitle.addView(nowTitle);
        TextView percentTitle = new TextView(this);
        percentTitle.setGravity(Gravity.CENTER);
        percentTitle.setText(getResources().getString(R.string.stock_increase_percent_title));
        rowTitle.addView(percentTitle);
        TextView increaseTitle = new TextView(this);
        increaseTitle.setGravity(Gravity.CENTER);
        increaseTitle.setText(getResources().getString(R.string.stock_increase_title));
        rowTitle.addView(increaseTitle);
        tableLayout.addView(rowTitle);
    }

    private void initIndex(String index) {
        int indexId;
        int changeId;
        if (index.equals(ShIndex)) {
            indexId = R.id.stock_sh_index;
            changeId = R.id.stock_sh_change;
        } else if (index.equals(SzIndex)) {
            indexId = R.id.stock_sz_index;
            changeId = R.id.stock_sz_change;
        } else {
            indexId = R.id.stock_chuang_index;
            changeId = R.id.stock_chuang_change;
        }
        TextView indexText = (TextView) findViewById(indexId);
        TextView changeText = (TextView) findViewById(changeId);
        new IndexThread(index, myHandler, indexText, changeText).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        actionBarSearch(menu, R.id.search);
        return true;
    }

    private void actionBarSearch(Menu menu, final int id) {
        searchItem = menu.findItem(id);
        SearchView searchView = (SearchView) searchItem.getActionView();
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("002284");
        //searchView.setSubmitButtonEnabled(true); // For a submit button

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "queryStock" which is entered in the search box.
                Log.i("onQueryTextSubmit ", query);
                if (query.length() != 6) {
                    Toast.makeText(StockApplication.getContent(), "ID error", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (isExisted(query)) {
                    saveStocksToPreferences(query);

                    tableLayout = (TableLayout) findViewById(R.id.stock_table);
                    new StockNowThread(query, myHandler, tableLayout).start();

                    searchItem.collapseActionView();
                } else {
                    Toast.makeText(StockApplication.getContent(), "exist", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {

        }
        return super.onOptionsItemSelected(item);
    }

}
