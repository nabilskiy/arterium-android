package com.maritech.arterium.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class BaseAdapter<E> extends RecyclerView.Adapter<BaseAdapter.Viewholder> {

    ArrayList<E> dataList;
    Class bindingClassType;
    Class modelClassType;
    static Context context;

    public BaseAdapter(Class bindingClassType, Class modelClassType) {
        dataList = new ArrayList<>();
        this.bindingClassType = bindingClassType;
        this.modelClassType = modelClassType;
    }

    public void setDataList(ArrayList<E> data) {
        this.dataList = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        Class parameterTypes[] = new Class[]{LayoutInflater.class, ViewGroup.class, boolean.class};
        Object bindingView = null;
        try {
            Method method = bindingClassType.getMethod("inflate", parameterTypes);
            Object args[] = new Object[]{inflater, parent, false};
            bindingView = method.invoke(bindingClassType, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Viewholder(bindingView, modelClassType);
    }

    @Override
    public void onBindViewHolder(BaseAdapter.Viewholder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        Object objectBinding;
        Class modelClass;

        public Viewholder(Object ob, Class modelClass) {
            super(getRootView(ob));
            this.objectBinding = ob;
            this.modelClass = modelClass;
        }

        public void bindView(Object object) {
            Class[] farms = new Class[]{modelClass};
            try {
                Method method = objectBinding.getClass().getMethod("setData", farms);
                Object[] args = new Object[]{object};
                method.invoke(objectBinding, args);
                method = objectBinding.getClass().getMethod("executePendingBindings", (Class<?>[]) null);
                method.invoke(objectBinding, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static View getRootView(Object ob) {
            View view = new View(context);
            try {
                Method method = ob.getClass().getMethod("getRoot", null);
                Object objView = method.invoke(ob, (Object[]) null);
                return (View) objView;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}