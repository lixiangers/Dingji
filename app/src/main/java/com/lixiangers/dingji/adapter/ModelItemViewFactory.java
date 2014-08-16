package com.lixiangers.dingji.adapter;

import android.content.Context;

import com.lixiangers.dingji.model.AddPictureItemViewModel;
import com.lixiangers.dingji.view.AddImageItemView;

public class ModelItemViewFactory {
    public static <T> BaseItemView GetCurrentModelItemViewInstance(Context context, T model) {
        if (model instanceof AddPictureItemViewModel) return new AddImageItemView(context);
        return new BaseItemView(context);
    }
}
