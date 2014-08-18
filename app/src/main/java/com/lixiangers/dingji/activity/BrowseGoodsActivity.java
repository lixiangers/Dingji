package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.GoodsExpandeAdapter;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.protocol.domain.GoodsCategory;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class BrowseGoodsActivity extends NeolixNaviagationBaseActivity {

    private ExpandableListView goodsListView;
    private List<GoodsCategory> goodsCategories;

    public BrowseGoodsActivity() {
        super(R.layout.activity_browse_goods);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.ding_ji_goods));

        goodsListView = (ExpandableListView) findViewById(R.id.lv_goods);

        goodsCategories = new ArrayList<GoodsCategory>();
        addTestData("羊肉");
        addTestData("牛肉");
        addTestData("鸡肉");
        addTestData("兔肉");
        GoodsExpandeAdapter adapter = new GoodsExpandeAdapter(getApplication(), goodsCategories);
        goodsListView.setAdapter(adapter);
    }

    private void addTestData(String categoryName) {
        Goods goodsl = getGoods("鼎记" + categoryName + "1", "美味无穷", 5698, "500g");
        Goods goods2 = getGoods("鼎记" + categoryName + "2", "美味无穷", 1234, "500g");
        Goods goods3 = getGoods("鼎记" + categoryName + "3", "美味无穷", 7823, "500g");
        Goods goods4 = getGoods("鼎记" + categoryName + "4", "美味无穷", 1342, "500g");

        GoodsCategory category = new GoodsCategory();
        category.setCategoryName(categoryName);
        category.setGoodsList(asList(goodsl, goods2, goods3, goods4));

        goodsCategories.add(category);
    }

    private Goods getGoods(String name, String des, int price, String unit) {
        Goods goods = new Goods(DateTime.now().getMillis() + "", name, des, unit, price, "");
        return goods;
    }
}
