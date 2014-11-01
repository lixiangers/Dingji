package com.lixiangers.dingji.activity;

import android.app.Activity;
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
import com.lixiangers.dingji.view.PullRefreshListView.PullToRefreshBase;
import com.lixiangers.dingji.view.PullRefreshListView.PullToRefreshExaplandListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lixiangers.dingji.util.StringUtil.showText;

public class BrowseGoodsFragment extends Fragment {

    private PullToRefreshExaplandListView goodsListView;
    private List<GoodsCategory> goodsCategories;
    private GoodsExpandeAdapter adapter;
    private boolean isFirstCreate = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        goodsCategories = new ArrayList<GoodsCategory>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_goods, container, false);
        goodsListView = (PullToRefreshExaplandListView) view.findViewById(R.id.lv_goods);
        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigation_bar);
        navigationBar.setTitle(R.string.ding_ji_goods);


        initAdapter();
        initGoodsListView();

        if (isFirstCreate) {
            goodsListView.doPullRefreshing(true, 200);
            isFirstCreate = false;
        } else {
            adapter.setData(goodsCategories);
            goodsListView.getRefreshableView().expandGroup(0, true);
        }
        return view;
    }

    private void initAdapter() {
        adapter = new GoodsExpandeAdapter(MyApplication.getInstance(), goodsCategories);
        adapter.setOnByGoodsListener(new GoodsExpandeAdapter.onByGoodsListener() {
            @Override
            public void OnByGoods(Goods goods) {
                StringUtil.showText(getString(R.string.add_to_shopping_cart_success));
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setGoods(goods);
                shoppingItem.setQuantity(1);
                ShoppingCartFragment.addGoods(shoppingItem);
            }
        });
    }

    private void initGoodsListView() {
        goodsListView.setPullDownRefreshEnabled(true);
        goodsListView.setPullUpRefreshEnabled(false);
        goodsListView.setScrollRefreshEnabled(false);
        goodsListView.getRefreshableView().setChildDivider(getResources().getDrawable(R.color.split_color));
        goodsListView.getRefreshableView().setDividerHeight(getResources().getDimensionPixelOffset(R.dimen.split_width));
        goodsListView.getRefreshableView().setGroupIndicator(null);
        goodsListView.getRefreshableView().setAdapter(adapter);

        goodsListView.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Goods child = adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(MyApplication.getInstance(), GoodsDetailActivity.class);
                intent.putExtra(Constant.GOODS, child);
                startActivity(intent);
                return false;
            }
        });

        goodsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
            }
        });

        goodsListView.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectedGroupPosition(groupPosition);
                return false;
            }
        });
    }

    private void loadData() {
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.list_product_in_group, null);

        Type type = new TypeToken<HttpResponse<List<GoodsCategory>>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<List<GoodsCategory>>> task =
                new RequestServerAsyncTask<HttpResponse<List<GoodsCategory>>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<List<GoodsCategory>> httpResponse) {
                        goodsListView.onPullDownRefreshComplete();
                        goodsListView.setLastUpdatedLabel(StringUtil.getHmsOfDate(new Date()));
                        if (httpResponse.noErrorMessage()) {
                            goodsCategories = httpResponse.getResponseParams();
                            adapter.setData(goodsCategories);
                            goodsListView.getRefreshableView().expandGroup(0, true);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }

}
