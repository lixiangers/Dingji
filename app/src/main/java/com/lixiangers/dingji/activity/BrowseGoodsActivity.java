package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.GoodsExpandeAdapter;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.protocol.domain.GoodsCategory;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.StringUtil;

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
        final GoodsExpandeAdapter adapter = new GoodsExpandeAdapter(getApplication(), goodsCategories);
        adapter.setOnByGoodsListener(new GoodsExpandeAdapter.onByGoodsListener() {
            @Override
            public void OnByGoods(Goods goods) {
                //TODO 添加商品
                StringUtil.showText(goods.getName());
            }
        });
        goodsListView.setAdapter(adapter);

        goodsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Goods child = adapter.getChild(groupPosition, childPosition);
                goToNextWithBundle(child, GoodsDetailActivity.class, Constant.GOODS);
                return false;
            }
        });
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
