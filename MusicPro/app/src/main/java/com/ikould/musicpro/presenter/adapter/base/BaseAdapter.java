package com.ikould.musicpro.presenter.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecycleView.Adapter基类
 * <p>
 * Created by liudong on 2016/9/1.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public View getView(View view, int id) {
            return mView.findViewById(id);
        }
    }
}
