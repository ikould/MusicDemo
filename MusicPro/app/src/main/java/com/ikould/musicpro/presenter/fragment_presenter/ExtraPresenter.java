package com.ikould.musicpro.presenter.fragment_presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ikould.frame.net.HttpsHelper;
import com.ikould.frame.utils.FileUtils;
import com.ikould.frame.utils.ToastUtils;
import com.ikould.musicpro.data.bean.PluginBean;
import com.ikould.musicpro.data.local.config.AppConfig;
import com.ikould.musicpro.data.remote.DownNet;
import com.ikould.musicpro.presenter.adapter.ExtraAdapter;
import com.ikould.musicpro.presenter.fragment_presenter.impl.IExtraPresenter;
import com.ikould.musicpro.utils.Constants;
import com.ikould.musicpro.view.fragment.ExtraFragment;
import com.ikould.musicpro.view.fragment.impl.IExtraFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment类的Presenter --> ExtraFragment
 * <p>
 * Created by liudong on 2016/8/2.
 */
public class ExtraPresenter implements IExtraPresenter {
    private static final String TAG = "ExtraPresenter";
    private Context mContext;
    private PluginBean pluginBean;
    private List<PluginBean.DataBean> datas;
    private String updateDataUrl;
    private ExtraAdapter extraAdapter;
    private IExtraFragment extraFragment;

    private ProgressDialog progressDialog;

    @Override
    public ExtraAdapter getExtraAdapter() {
        return extraAdapter;
    }

    public ExtraPresenter(Context context, ExtraFragment extraFragment) {
        this.mContext = context;
        this.extraFragment = extraFragment;
        updateDataUrl = Constants.UPDATE_FILE_URL;
        initUpdateData();
    }

    /**
     * 解析更新数据
     */
    private void initUpdateData() {
        if (pluginBean == null) {
            pluginBean = new PluginBean();
        }
        if (extraAdapter == null) {
            datas = new ArrayList<>();
            pluginBean = AppConfig.getInstance().getPluginBean();
            if (pluginBean != null) {
                datas = pluginBean.getData();
                Log.d(TAG, pluginBean.toString());
            } else {
                Log.d(TAG, "pluginBean为空");
            }
            ExtraAdapter.ExtraItemClickListener extraItemClickListener = position -> {
                //判断文件是否存在
                if (datas != null) {
                    PluginBean.DataBean dataBean = datas.get(position);
                    if (dataBean != null) {
                        if (isExistApk(dataBean)) {
                            //下载文件
                            downLoadApk(dataBean);
                        } else {
                            //打开此插件
                            openApkPlugin(dataBean);
                        }
                    }
                }
            };
            extraAdapter = new ExtraAdapter(datas, mContext, extraItemClickListener);
        }
        extraFragment.setExtraAdapter(extraAdapter);
        getUpdataFromNet();
    }

    /**
     * 下载apk文件
     *
     * @param dataBean
     */
    private void downLoadApk(PluginBean.DataBean dataBean) {
        AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                DownNet.downApk(dataBean);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                openProgressDialoag();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                progressDialog.setProgress(Integer.parseInt(values[0]));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.cancel();
            }
        };
        asyncTask.execute();
    }

    /**
     * 打开进度条
     */
    private void openProgressDialoag() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("正在下载...");
        progressDialog.setMax(100);
        /* 水平风格进度条 */
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        /* 无限循环模式 */
        progressDialog.setIndeterminate(false);
        /* 可取消 */
        progressDialog.setCancelable(true);
        /* 显示对话框 */
        progressDialog.show();
    }

    /**
     * 打开APk插件
     */
    private void openApkPlugin(PluginBean.DataBean dataBean) {

    }

    /**
     * 判断Apk文件是否存在
     *
     * @param dataBean
     * @return
     */
    private boolean isExistApk(PluginBean.DataBean dataBean) {
        String apkPath = AppConfig.DOWNLOAD_FILE + File.separator + dataBean.getFile() + File.separator + dataBean.getApk();
        return FileUtils.isFileExist(apkPath);
    }

    public void getUpdataFromNet() {
        HttpsHelper.HttpCallBack callBack = new HttpsHelper.HttpCallBack<String>() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, e.toString());
                ToastUtils.show(mContext, "网络错误");
            }

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                if (!TextUtils.isEmpty(response)) {
                    Log.d(TAG, response);
                    response = response.replace("\n", "");
                    PluginBean pluginBeanTemp = gson.fromJson(response, PluginBean.class);
                    //判断版本号是否一致
                    if (pluginBean == null || pluginBean.getVersion() != pluginBeanTemp.getVersion()) {
                        Log.d(TAG, "没有保存或者有新版本");
                        pluginBean = pluginBeanTemp;
                        AppConfig.getInstance().setPluginBean(pluginBean);
                        datas = pluginBean.getData();
                        extraAdapter.notifyData(datas);
                    } else {
                        Log.d(TAG, "使用本地资源");
                    }
                }
            }
        };
        new Thread(() -> {
            DownNet.getStringByUrl(updateDataUrl, callBack);
        }).start();
    }
}
