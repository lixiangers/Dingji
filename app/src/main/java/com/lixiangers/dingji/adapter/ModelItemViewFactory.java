package com.lixiangers.dingji.adapter;

import android.content.Context;

import com.lixiangers.dingji.model.OrderItem;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.view.AddImageItemView;
import com.lixiangers.dingji.view.AddressItemView;
import com.lixiangers.dingji.view.GoodsItemView;
import com.lixiangers.dingji.view.OrderItemView;
import com.lixiangers.dingji.viewmodel.AddPictureItemViewModel;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

public class ModelItemViewFactory {
    public static <T> BaseItemView GetCurrentModelItemViewInstance(Context context, T model) {
        if (model instanceof AddPictureItemViewModel) return new AddImageItemView(context);
        else if (model instanceof GoodsItemViewModel) return new GoodsItemView(context);
        else if (model instanceof OrderItem) return new OrderItemView(context);
        else if (model instanceof Address) return new AddressItemView(context);
        return new BaseItemView(context);
    }
}
