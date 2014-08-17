package com.lixiangers.dingji.adapter;

import android.content.Context;

import com.lixiangers.dingji.model.AddPictureItemViewModel;
import com.lixiangers.dingji.view.AddImageItemView;
import com.lixiangers.dingji.view.GoodsItemView;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

public class ModelItemViewFactory {
    public static <T> BaseItemView GetCurrentModelItemViewInstance(Context context, T model) {
        if (model instanceof AddPictureItemViewModel) return new AddImageItemView(context);
        else if (model instanceof GoodsItemViewModel) return new GoodsItemView(context);
        return new BaseItemView(context);
    }
}
