package com.ikould.frame.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 各种网络请求
 * <p>
 * Created by liudong on 2016/8/11.
 */
public class HttpsHelper {
    private static HttpsHelper instance;

    public static HttpsHelper getInstance() {
        if (instance == null) {
            synchronized (HttpsHelper.class) {
                instance = new HttpsHelper();
            }
        }
        return instance;
    }

    private HttpsHelper() {
    }

    /**
     * 异步get请求
     */
    public void get(final String urlStr, final HttpCallBack<String> callBack) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                InputStreamReader reader = new InputStreamReader(httpURLConnection.getInputStream(), "utf-8");
                //char[] chars = new char[1024];
                StringBuilder stringBuffer = new StringBuilder();
                int i;
                while ((i = reader.read()) != -1) {
                    stringBuffer.append((char) i);
                }
                reader.close();
                callBack.onSuccess(stringBuffer.toString().trim());
            } else {
                callBack.onFailure(new IOException("异常：" + code));
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            callBack.onFailure(e);
        }
    }

    /**
     * 异步post请求
     */
    public void post(String urlStr, Map<String, Object> maps, final HttpCallBack<String> callBack) {
        urlStr += "?";
        if (maps != null) {
            for (Map.Entry entry : maps.entrySet()) {
                urlStr += entry.getKey().toString() + "=" + entry.getValue().toString() + "&";
            }
        }
        final String uri = urlStr.substring(0, urlStr.length() - 1);
        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            // http正文内，因此需要设为true, 默认情况下是false;
            httpURLConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                Log.d("liudong", "success");
                InputStreamReader reader = new InputStreamReader(httpURLConnection.getInputStream(), "utf-8");
                char[] chars = new char[1024];
                StringBuilder builder = new StringBuilder();
                while (reader.read(chars) != -1) {
                    builder.append(chars);
                }
                reader.close();
                callBack.onSuccess(builder.toString().trim());
            } else {
                callBack.onFailure(new IOException("异常：" + code));
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            callBack.onFailure(e);
        }
    }

    /**
     * 异步远程图片下载
     *
     * @return
     */
    public void getBitmap(String bitmapUrl, HttpCallBack<Bitmap> callBack) {
        try {
            Log.d("liudong", "getBitmap 开始下载:url:" + bitmapUrl);
            URL url = new URL(bitmapUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                Log.d("liudong", "code == 200 下载成功");
                callBack.onSuccess(BitmapFactory.decodeStream(urlConnection.getInputStream()));
            } else {
                callBack.onFailure(new IOException("异常：" + code));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param savePath 本地文件保存地址
     * @param downPath 下载地址
     * @param fileName 文件名
     */
    public void downLoadFile(String savePath, String downPath, String fileName) {
        try {
            URL url = new URL(downPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                Log.d("liudong", "code == 200 下载成功");
                File file = new File(savePath, fileName);
                InputStream is = urlConnection.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                while (is.read(bytes) != -1) {
                    fos.write(bytes);
                }
                fos.flush();
                fos.close();
                is.close();
            } else {
                Log.d("liudong", "异常：" + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface HttpCallBack<T> {
        void onFailure(Exception e);

        void onSuccess(T response);
    }
}

