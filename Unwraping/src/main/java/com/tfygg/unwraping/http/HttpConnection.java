package com.tfygg.unwraping.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.tfygg.unwraping.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tfygg on 2016/11/16.
 */

public class HttpConnection {
    private static final String TAG = "IndexThread";

    private URL ur;
    private BufferedReader reader;
    private HttpURLConnection urlConnection;

    public BufferedReader queryStock(String stockId) {
        if (stockId.length() != 6) {
            return null;
        }
        if (stockId.equals("000001")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("6")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        } else {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("http://biz.finance.sina.com.cn/stock/flash_hq/kline_data.php?&symbol=");
        sb.append(stockId);
        sb.append("&begin_date=");
        sb.append(Utils.getDay(-100));
        sb.append("&end_date=");
        sb.append(Utils.getDay(-1));
        sb.append("&type=plain");
        String url = sb.toString();
        Log.i("url", url);
        try {
            ur = new URL(url);
            urlConnection = (HttpURLConnection) ur.openConnection();
            reader = new BufferedReader(new InputStreamReader(ur.openStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }

    public BufferedReader queryStock(String stockId, String startDate, String endDate) {
        if (stockId.length() != 6) {
            return null;
        }
        if (stockId.startsWith("6")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        } else {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("http://biz.finance.sina.com.cn/stock/flash_hq/kline_data.php?&symbol=");
        sb.append(stockId);
        sb.append("&begin_date=");
        sb.append(startDate);
        sb.append("&end_date=");
        sb.append(endDate);
        sb.append("&type=plain");
        String url = sb.toString();
        Log.i("url", url);

        try {
            ur = new URL(url);
            urlConnection = (HttpURLConnection) ur.openConnection();
            reader = new BufferedReader(new InputStreamReader(ur.openStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }

    public String queryStockNow(String stockId) {
        if (stockId.length() != 6) {
            return null;
        }
        if (stockId.startsWith("6")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        } else {
            return null;
        }
        String url = "http://hq.sinajs.cn/list=" + stockId;
        try {
            ur = new URL(url);
            urlConnection = (HttpURLConnection) ur.openConnection();

            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "GBK");
            reader = new BufferedReader(isr);
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            Log.d("sb ", sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String queryStockIndex(String stockId) {
        if (stockId.length() != 6) {
            return null;
        }
        if (stockId.endsWith("000001")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        } else {
            return null;
        }
        String url = "http://hq.sinajs.cn/list=" + stockId;
        try {
            ur = new URL(url);
            urlConnection = (HttpURLConnection) ur.openConnection();

            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "GBK");
            reader = new BufferedReader(isr);
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            Log.d("sb ", sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从指定URL获取图片
     *
     * @param url
     * @return
     */
    public Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);
            is.close();

//            HttpGet httpRequest = new HttpGet(url);
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
//            HttpEntity entity = response.getEntity();
////BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);//可能会产生内存溢出
////InputStream is = bufferedHttpEntity.getContent();
//            InputStream is = entity.getContent();
//            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Drawable getPriceChart(String ticker, String timeInterval, String plotType) {
        Drawable d = null;
        try {

            URL url = new URL("http://chart.finance.yahoo.com/z?"
                    + "s=" + ticker
                    + "&t=" + timeInterval  // Time interval, {1d, 5d, 3m, 6m, 1y, 2y, 5y, my}
                    + "&q=" + plotType      // Chart type {l, b, c}
                    + "&l=" + "on"  // Logarithmic scaling {on, off}
                    + "&z=" + "m"   // Size {m, l}
                    + "&a=v" // Volume
            );
            InputStream content = (InputStream) url.getContent();
            d = Drawable.createFromStream(content, "src");
        } catch (Exception e) {
            Log.d("ERROR_GETTINGCHARTS", e.toString());
        }
        return d;
    }

}
