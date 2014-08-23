package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.model.OrderItem;
import com.lixiangers.dingji.util.CommonHelper;
import com.lixiangers.dingji.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends android.support.v4.app.Fragment {
    public static List<OrderItem> orderItemList = new ArrayList<OrderItem>();
    private ModelListAdapter<OrderItem> adapter;
    private ListView goodsListView;

    public static void addGoods(OrderItem item) {
        boolean isExits = false;
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getGoods().getId().equals(item.getGoods().getId())) {
                isExits = true;
                orderItem.setQuantity(orderItem.getQuantity() + item.getQuantity());
                break;
            }
        }

        if (!isExits)
            orderItemList.add(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigation_bar);
        navigationBar.setTitle(R.string.my_shopping_cart);

        goodsListView = (ListView) view.findViewById(R.id.goods_list_view);

        adapter = new ModelListAdapter<OrderItem>(MyApplication.getInstance());
        goodsListView.setAdapter(adapter);

        loadData();
        return view;
    }

    private void loadData() {
        adapter.setData(orderItemList);
        CommonHelper.setListViewHeightBasedOnChildren(goodsListView);
    }
}
