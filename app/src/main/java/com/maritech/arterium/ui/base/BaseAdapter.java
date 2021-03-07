package com.maritech.arterium.ui.base;

import android.content.Context;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<T> items;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    public BaseAdapter(Context context, ArrayList<T> items){
        this.context = context;
        this.items = items;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return setViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        onBindData(holder,items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems( ArrayList<T> savedCardItems){
        items = savedCardItems;
        this.notifyDataSetChanged();
    }

    public T getItem(int position){
        return items.get(position);
    }
}