package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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

public class ShoppingCartFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private static List<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
    private ModelListAdapter<ShoppingItem> adapter;
    private ListView goodsListView;
    private TextView totalAmountTextView;
    private Button commitButton;
    private int totalAmount;
    private PopupWindow popupWindow;
    private View mPopupWindowView;
    private int currentItemIndex = -1;
    private ShoppingItem currentShoppingItem;

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
//        goodsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                currentItemIndex = position;
//                initPopupWindow(adapter.getItem(position).getGoods().getName());
//                showPopupWindow();
//                return false;
//            }
//        });

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
        for (final ShoppingItem shoppingItem : shoppingItemList) {
            shoppingItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    currentShoppingItem = shoppingItem;
                    initPopupWindow(shoppingItem.getGoods().getName());
                    showPopupWindow();
                    return false;
                }
            });
        }

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
//                        shoppingItemList.remove(shoppingItem);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_menu:
                if (currentShoppingItem != null) {
                    shoppingItemList.remove(currentShoppingItem);
                    loadData();
                }
                popupWindow.dismiss();
                break;
        }
    }

    private void initPopupWindow(String title) {
        initPopupWindowView(title);
        popupWindow = new PopupWindow(mPopupWindowView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        popupWindow.setAnimationStyle(R.style.my_popwindow_anim_style);
        popupWindow.update();
    }

    private void initPopupWindowView(String title) {
        mPopupWindowView = LayoutInflater.from(getActivity()).inflate(R.layout.menu_frgment_pickup, null);
        LinearLayout layout = (LinearLayout) mPopupWindowView.findViewById(R.id.lv_menu);
        layout.setOnClickListener(this);
        TextView titleTextView = (TextView) mPopupWindowView.findViewById(R.id.tv_title);
        titleTextView.setText(title);
    }

    private void showPopupWindow() {
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(ShoppingCartFragment.this.getView(), Gravity.CENTER, 0, 0);
        } else {
            popupWindow.dismiss();
        }
    }
}
