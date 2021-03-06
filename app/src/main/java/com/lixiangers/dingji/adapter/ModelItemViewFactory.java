package com.lixiangers.dingji.adapter;

import android.content.Context;

import com.lixiangers.dingji.model.Goodses;
import com.lixiangers.dingji.model.ShoppingItem;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.protocol.domain.OrderRequestAndResponse;
import com.lixiangers.dingji.view.AddImageItemView;
import com.lixiangers.dingji.view.AddressItemView;
import com.lixiangers.dingji.view.GoodsItemView;
import com.lixiangers.dingji.view.OrderItemView;
import com.lixiangers.dingji.view.ProductItemView;
import com.lixiangers.dingji.view.ShoppingItemView;
import com.lixiangers.dingji.viewmodel.AddPictureItemViewModel;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

public class ModelItemViewFactory {
    public static <T> BaseItemView GetCurrentModelItemViewInstance(Context context, T model) {
        if (model instanceof AddPictureItemViewModel) return new AddImageItemView(context);
        else if (model instanceof GoodsItemViewModel) return new GoodsItemView(context);
        else if (model instanceof ShoppingItem) return new ShoppingItemView(context);
        else if (model instanceof Address) return new AddressItemView(context);
        else if (model instanceof OrderRequestAndResponse) return new OrderItemView(context);
        else if (model instanceof Goodses) return new ProductItemView(context);
        return new BaseItemView(context);
    }
}
