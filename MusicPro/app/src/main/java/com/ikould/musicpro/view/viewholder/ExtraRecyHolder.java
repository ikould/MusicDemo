package com.ikould.musicpro.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikould.musicpro.R;

/**
 * ExtraFragment RecyclerView.ViewHolder
 * <p>
 * Created by liudong on 2016/8/17.
 */
public class ExtraRecyHolder extends RecyclerView.ViewHolder {
    public ImageView itemIcon;
    public TextView itemText;
    public LinearLayout itemPlugin;

    public ExtraRecyHolder(View itemView) {
        super(itemView);
        itemIcon = (ImageView) itemView.findViewById(R.id.item_plugin_icon);
        itemText = (TextView) itemView.findViewById(R.id.item_plugin_title);
        itemPlugin = (LinearLayout) itemView.findViewById(R.id.item_plugin);
    }
}
