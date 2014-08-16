package com.lixiangers.dingji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.lixiangers.dingji.adapter.ModelItemViewFactory.GetCurrentModelItemViewInstance;

public class ModelListAdapter<T> extends BaseAdapter {

    private List<T> models = new ArrayList<T>();
    private final Context context;
    private int selectedPosition = -1;

    public ModelListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public T getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        T location = getItem(position);
        BaseItemView itemView = getModelItemView(convertView, location);
        itemView.setModel(location);

        itemView.setIsSelected(position == selectedPosition);

        return itemView;
    }

    public void setData(List<T> locations) {
        this.models = locations;
        update();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public List<T> getData() {
        return models;
    }

    private void update() {
        notifyDataSetChanged();
    }

    private BaseItemView getModelItemView(View convertView, T model) {
        return null != convertView ? (BaseItemView) convertView : GetCurrentModelItemViewInstance(context, model);
    }
}