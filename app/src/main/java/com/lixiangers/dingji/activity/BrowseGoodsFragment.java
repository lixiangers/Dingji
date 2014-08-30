package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.GoodsExpandeAdapter;
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.model.Goods;
import com.lixiangers.dingji.model.ShoppingItem;
import com.lixiangers.dingji.protocol.domain.GoodsCategory;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.StringUtil;
import com.lixiangers.dingji.view.NavigationBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

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
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setGoods(goods);
                shoppingItem.setQuantity(1);
                ShoppingCartFragment.addGoods(shoppingItem);
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
        showRequestDialog(getActivity(), getString(R.string.is_query_goods));
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.list_product_in_group, null);

        Type type = new TypeToken<HttpResponse<List<GoodsCategory>>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<List<GoodsCategory>>> task =
                new RequestServerAsyncTask<HttpResponse<List<GoodsCategory>>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<List<GoodsCategory>> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            goodsCategories = httpResponse.getResponseParams();
                            adapter.setData(goodsCategories);
                            goodsListView.expandGroup(0, true);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }

}
