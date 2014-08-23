package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.GoodsExpandeAdapter;
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.model.OrderItem;
import com.lixiangers.dingji.protocol.domain.GoodsCategory;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.StringUtil;
import com.lixiangers.dingji.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class BrowseGoodsFragment extends Fragment {

    private ExpandableListView goodsListView;
    private List<GoodsCategory> goodsCategories;
    private GoodsExpandeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_goods, container, false);
        goodsListView = (ExpandableListView) view.findViewById(R.id.lv_goods);
        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigation_bar);
        navigationBar.setTitle(R.string.ding_ji_goods);

        goodsCategories = new ArrayList<GoodsCategory>();

        initAdapter();
        initGoodsListView();

        return view;
    }

    private void initAdapter() {
        adapter = new GoodsExpandeAdapter(MyApplication.getInstance(), goodsCategories);
        adapter.setOnByGoodsListener(new GoodsExpandeAdapter.onByGoodsListener() {
            @Override
            public void OnByGoods(Goods goods) {
                StringUtil.showText(goods.getName());
                OrderItem orderItem = new OrderItem();
                orderItem.setGoods(goods);
                orderItem.setQuantity(1);
                ShoppingCartFragment.addGoods(orderItem);
            }
        });
    }

    private void initGoodsListView() {
        goodsListView.setAdapter(adapter);
        goodsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Goods child = adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(MyApplication.getInstance(), GoodsDetailActivity.class);
                intent.putExtra(Constant.GOODS, child);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        addTestData("羊肉");
        addTestData("牛肉");
        addTestData("鸡肉");
        addTestData("兔肉");
        adapter.setData(goodsCategories);
        goodsListView.expandGroup(0, true);
    }

    private void addTestData(String categoryName) {
        Goods goodsl = getGoods("001", "鼎记" + categoryName + "1", "美味无穷", 5698, "500g");
        Goods goods2 = getGoods("002", "鼎记" + categoryName + "2", "美味无穷", 1234, "500g");
        Goods goods3 = getGoods("003", "鼎记" + categoryName + "3", "美味无穷", 7823, "500g");
        Goods goods4 = getGoods("004", "鼎记" + categoryName + "4", "美味无穷", 1342, "500g");

        GoodsCategory category = new GoodsCategory();
        category.setCategoryName(categoryName);
        category.setGoodsList(asList(goodsl, goods2, goods3, goods4));

        goodsCategories.add(category);
    }

    private Goods getGoods(String id, String name, String des, int price, String unit) {
        Goods goods = new Goods(id, name, des, unit, price, "");
        return goods;
    }
}
