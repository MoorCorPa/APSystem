package com.linmo.apsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH>{
    protected static final int VIEW_TYPE_EMPETY = 0;
    protected static final int VIEW_TYPE_ITEM = 1;

    private LayoutInflater mInflater;
    private Context mContext;

    public BaseAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public abstract void notifyDataSetChanged(List<T> dataList);
}
