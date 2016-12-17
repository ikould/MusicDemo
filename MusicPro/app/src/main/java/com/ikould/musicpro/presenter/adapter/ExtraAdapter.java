package com.ikould.musicpro.presenter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ikould.frame.net.HttpsHelper;
import com.ikould.frame.utils.FileUtils;
import com.ikould.musicpro.R;
import com.ikould.musicpro.data.bean.PluginBean;
import com.ikould.musicpro.data.local.config.AppConfig;
import com.ikould.musicpro.data.remote.DownNet;
import com.ikould.musicpro.utils.Constants;
import com.ikould.musicpro.view.viewholder.ExtraRecyHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExtraFragment适配器
 * <p>
 * Created by liudong on 2016/8/17.
 */
public class ExtraAdapter extends RecyclerView.Adapter<ExtraRecyHolder> {
    //包含所有数据项（包括Name和地址）的List
    private List<PluginBean.DataBean> pluginBeens;
    //图片缓存Map
    private Map<Integer, LruCache<String, Bitmap>> lruCaches;

    private List<Integer> mDownBitmap; //下载的序列，对应Adapter的position
    private Thread mDownThreadQueue; //下载的线程队列
    private HttpsHelper.HttpCallBack<Bitmap> callBack; //回调

    private ExtraItemClickListener extraItemClickListener;

    private Context mContext;
    private Handler extraAdapterHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyDataSetChanged();
        }
    };

    public ExtraAdapter(List<PluginBean.DataBean> pluginBeens, Context mContext, ExtraItemClickListener extraItemClickListener) {
        this.pluginBeens = pluginBeens;
        this.mContext = mContext;
        this.extraItemClickListener = extraItemClickListener;
        lruCaches = new HashMap<>();
    }

    /**
     * 初始化图片
     */
    private void initImages() {
        for (int i = 0; i < pluginBeens.size(); i++) {
            FileUtils.initDirctory(AppConfig.DOWNLOAD_FILE + File.separator + pluginBeens.get(i).getFile());
            getImgFromMemory(i, pluginBeens.get(i).getIco());
        }
    }

    @Override
    public ExtraRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExtraRecyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_extra, parent, false));
    }

    @Override
    public void onBindViewHolder(ExtraRecyHolder holder, int position) {
        Log.d("liudong", "onBindViewHolder");
        Bitmap bitmap = getImgFromMemory(position, pluginBeens.get(position).getIco());
        if (bitmap != null) {
            holder.itemIcon.setImageBitmap(bitmap);
        } else {
            holder.itemIcon.setImageResource(R.mipmap.no_bitmap);
        }
        holder.itemText.setText(pluginBeens.get(position).getName());
        holder.itemPlugin.setOnClickListener(v -> extraItemClickListener.extraItemClickListener(position));
    }

    public interface ExtraItemClickListener {
        void extraItemClickListener(int position);
    }

    @Override
    public int getItemCount() {
        return pluginBeens.size();
    }

    /**
     * 提供修改数据
     */
    public void notifyData(List<PluginBean.DataBean> pluginBeens) {
        this.pluginBeens = pluginBeens;
        Log.d("liudong", "notifyData");
        initImages();
        extraAdapterHandler.sendEmptyMessage(0);
    }

    /**
     * 从内存中获取图片
     *
     * @param index
     * @param icon
     */
    private Bitmap getImgFromMemory(int index, String icon) {
        Bitmap bitmap = null;
        if (lruCaches.get(index) == null || lruCaches.get(index).get(icon) == null) {
            //从文件中获取
            String imgUrl = AppConfig.DOWNLOAD_FILE + File.separator + pluginBeens.get(index).getFile() + File.separator + icon;
            //网络下载
            if (!FileUtils.isFileExist(imgUrl)) {
                getImgFromNet(index);
            } else {
                LruCache<String, Bitmap> lruCache = new LruCache<>(1);
                bitmap = getImgFromFile(imgUrl);
                lruCache.put(icon, bitmap);
                lruCaches.put(index, lruCache);
                Log.d("liudong", "LruCache");
            }
        } else {
            bitmap = lruCaches.get(index).get(icon);
        }
        return bitmap;
    }

    /**
     * 从网络上下载图片
     *
     * @param index
     */
    private void getImgFromNet(int index) {
        if (mDownBitmap == null) {
            mDownBitmap = new ArrayList<>();
        }
        mDownBitmap.add(index);
        Log.d("liudong", "mDownBitmap的size:" + mDownBitmap.size());
        if (mDownThreadQueue == null) {
            callBack = new HttpsHelper.HttpCallBack<Bitmap>() {
                @Override
                public void onFailure(Exception e) {
                    Log.d("liudong", e.toString());
                }

                @Override
                public void onSuccess(Bitmap response) {
                    Log.d("liudong", "下载图片成功");
                    int ind = mDownBitmap.get(0);
                    LruCache<String, Bitmap> lruCache = new LruCache<>(1);
                    lruCache.put(pluginBeens.get(ind).getIco(), response);
                    lruCaches.put(ind, lruCache);
                    mDownBitmap.remove(0);//移除第0个位置元素
                    Log.d("liudong", "mDownBitmap的size:" + mDownBitmap.size());
                    extraAdapterHandler.sendEmptyMessage(ind);//刷新
                    //保存到文件中去
                    saveBitmapToFile(AppConfig.DOWNLOAD_FILE + File.separator + pluginBeens.get(ind).getFile() + File.separator + pluginBeens.get(ind).getIco(), response);
                }
            };
            startBitmapQueue();
        }
    }

    /**
     * 将Bitmap保存到文件中
     *
     * @param fileName
     * @param bitmap
     */
    private void saveBitmapToFile(String fileName, Bitmap bitmap) {
        try {
            //将Bitmap转换为byte[]
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            //创建文件
            File file = new File(fileName);
            file.createNewFile();
            //写入文件
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            bos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 从文件中获取图片
     *
     * @param imgUrl
     * @return
     */
    private Bitmap getImgFromFile(String imgUrl) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(imgUrl));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 开启图片下载队列
     */
    private void startBitmapQueue() {
        Log.d("liudong", "开始异步网络下载");
        //异步网络下载
        mDownThreadQueue = new Thread(() -> {
            while (!mDownThreadQueue.isInterrupted()) {
                Log.d("liudong", "开始异步网络下载-->线程");
                if (mDownBitmap.size() > 0) {
                    Integer index = mDownBitmap.get(0);
                    Log.d("liudong", "mDownBitmap的size:" + mDownBitmap.size());
                    PluginBean.DataBean dataBean = pluginBeens.get(index);
                    String downUrl = Constants.BASE_DOWN_URL + File.separator + pluginBeens.get(index).getFile() + File.separator + dataBean.getIco();
                    DownNet.getBitmapByUrl(downUrl, callBack);
                    Log.d("liudong", "开始异步网络下载-->线程 -->下载结束");
                } else {
                    Log.d("liudong", "mDownBitmap的size:" + mDownBitmap.size());
                    //销毁图片下载队列
                    cancelBitmapQueue();
                }
            }
        });
        mDownThreadQueue.start();
    }

    /**
     * 销毁图片下载队列
     */
    private void cancelBitmapQueue() {
        Log.d("liudong", "销毁线程");
        if (mDownThreadQueue != null) {
            mDownThreadQueue.isInterrupted();
        }
    }
}
