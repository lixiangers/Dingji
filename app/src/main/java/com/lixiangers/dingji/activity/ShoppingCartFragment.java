package com.lixiangers.dingji.activity;

import android.content.Intent;
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
import com.lixiangers.dingji.model.ShoppingItem;
import com.lixiangers.dingji.util.CommonHelper;
import com.lixiangers.dingji.view.NavigationBar;
import com.lixiangers.dingji.view.SelectQuantityView;

import java.util.ArrayList;
import java.util.List;

import static com.lixiangers.dingji.util.StringUtil.showText;

public class ShoppingCartFragment extends android.support.v4.app.Fragment {
    private static List<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
    private ModelListAdapter<ShoppingItem> adapter;
    private ListView goodsListView;
    private TextView totalAmountTextView;
    private Button commitButton;
    private int totalAmount;

    public static void addGoods(ShoppingItem item) {
        boolean isExits = false;
        for (ShoppingItem shoppingItem : shoppingItemList) {
            if (shoppingItem.getGoods().getid().equals(item.getGoods().getid())) {
                isExits = true;
                shoppingItem.setQuantity(shoppingItem.getQuantity() + item.getQuantity());
                break;
            }
        }

        if (!isExits)
            shoppingItemList.add(item);
    }

    public static List<ShoppingItem> getShoppingItemList() {
        return shoppingItemList;
    }

    public static void clearShoppingCat() {
        shoppingItemList.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigation_bar);
        navigationBar.setTitle(R.string.my_shopping_cart);

        goodsListView = (ListView) view.findViewById(R.id.goods_list_view);
        totalAmountTextView = (TextView) view.findViewById(R.id.tv_amount);
        commitButton = (Button) view.findViewById(R.id.bt_commit);

        adapter = new ModelListAdapter<ShoppingItem>(MyApplication.getInstance());
        goodsListView.setAdapter(adapter);

        addNumberChangeListener();
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalAmount < 0) {
                    showText("您没有购买任何商品");
                    return;
                }

                startActivity(new Intent(MyApplication.getInstance(), ConfirmAddressActivity.class));
            }
        });
        loadData();
        return view;
    }

    private void loadData() {
        adapter.setData(shoppingItemList);
        goodsListView.setVisibility(shoppingItemList.isEmpty() ? View.GONE : View.VISIBLE);
        goodsListView.setItemsCanFocus(true);
        calcTotalAmount();
        CommonHelper.setListViewHeightBasedOnChildren(goodsListView);
    }

    private void addNumberChangeListener() {
        for (final ShoppingItem shoppingItem : shoppingItemList) {
            shoppingItem.setOnNumberChangeListener(new SelectQuantityView.onNumberChangeListener() {
                @Override
                public void onNumberChange(int number) {
                    shoppingItem.setQuantity(number);
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
        totalAmount = 0;
        for (ShoppingItem shoppingItem : shoppingItemList) {
            totalAmount += shoppingItem.getTotalAmount();
        }
        totalAmountTextView.setText(String.format("%.2f", totalAmount / 100f));
    }
}
