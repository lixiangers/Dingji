package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.model.OrderItem;
import com.lixiangers.dingji.util.CommonHelper;
import com.lixiangers.dingji.view.NavigationBar;
import com.lixiangers.dingji.view.SelectQuantityView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends android.support.v4.app.Fragment {
    public static List<OrderItem> orderItemList = new ArrayList<OrderItem>();
    private ModelListAdapter<OrderItem> adapter;
    private ListView goodsListView;
    private TextView totalAmountTextView;
    private Button commitButton;

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
        totalAmountTextView = (TextView) view.findViewById(R.id.tv_amount);
        commitButton = (Button) view.findViewById(R.id.bt_commit);

        adapter = new ModelListAdapter<OrderItem>(MyApplication.getInstance());
        goodsListView.setAdapter(adapter);

        addNumberChangeListener();
        loadData();
        return view;
    }

    private void loadData() {
        adapter.setData(orderItemList);
        goodsListView.setVisibility(orderItemList.isEmpty() ? View.GONE : View.VISIBLE);
        calcTotalAmount();
        CommonHelper.setListViewHeightBasedOnChildren(goodsListView);
    }

    private void addNumberChangeListener() {
        for (final OrderItem orderItem : orderItemList) {
            orderItem.setOnNumberChangeListener(new SelectQuantityView.onNumberChangeListener() {
                @Override
                public void onNumberChange(int number) {
                    orderItem.setQuantity(number);
//                    if (number == 0) {
//                        orderItemList.remove(orderItem);
//                        loadData();
//                    }

                    calcTotalAmount();
                }
            });
        }
    }

    private void calcTotalAmount() {
        int totalAmount = 0;
        for (OrderItem orderItem : orderItemList) {
            totalAmount += orderItem.getTotalAmount();
        }
        totalAmountTextView.setText(String.format("%.2f", totalAmount / 100f));
    }
}
