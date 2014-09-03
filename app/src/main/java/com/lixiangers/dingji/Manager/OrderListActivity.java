package com.lixiangers.dingji.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.activity.NeolixNaviagationBaseActivity;
import com.lixiangers.dingji.activity.OrderDetailActivity;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.protocol.domain.OrderRequestAndResponse;
import com.lixiangers.dingji.protocol.domain.QueryOrderListRequest;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.StringUtil;
import com.lixiangers.dingji.view.PullRefreshListView.PullToRefreshBase;
import com.lixiangers.dingji.view.PullRefreshListView.PullToRefreshListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lixiangers.dingji.util.StringUtil.showText;

public abstract class OrderListActivity extends NeolixNaviagationBaseActivity {

    private PullToRefreshListView orderPullListView;
    private ModelListAdapter<OrderRequestAndResponse> adapter;
    private ArrayList<OrderRequestAndResponse> orderList;
    private int startIndex = 0;

    public OrderListActivity() {
        super(R.layout.activity_completed_order_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getTitleString());
        setLeftImage(R.drawable.selector_bg_back);


        orderPullListView = (PullToRefreshListView) findViewById(R.id.pull_lv_order);
        adapter = new ModelListAdapter<OrderRequestAndResponse>(getApplicationContext());
        orderList = new ArrayList<OrderRequestAndResponse>();

        initPullListView();
        orderPullListView.doPullRefreshing(true, 200);
    }

    private void initPullListView() {
        orderPullListView.setPullDownRefreshEnabled(false);
        orderPullListView.setPullUpRefreshEnabled(false);
        orderPullListView.setScrollRefreshEnabled(true);
        orderPullListView.getRefreshableView().setAdapter(adapter);


        orderPullListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                intent.putExtra(Constant.ORDER_ID, adapter.getItem(i).getId());
                intent.putExtra(Constant.ORDER_NUMBER, adapter.getItem(i).getOrder_no());
                intent.putExtra(Constant.ORDER_NUMBER, adapter.getItem(i).getOrder_no());
                intent.putExtra(Constant.IS_NEED_CHANGE_ORDER, isUnFinish());
                startActivity(intent);
            }
        });

        orderPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadOrder();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadOrder();
            }
        });
    }

    protected abstract boolean isUnFinish();

    private void loadOrder() {
        QueryOrderListRequest request = new QueryOrderListRequest();
        request.setStatus(getStatus());

        request.setStart(startIndex);
        request.setCount(Constant.QUERY_ORDER_COUNT);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.list_order, request);

        Type type = new TypeToken<HttpResponse<List<OrderRequestAndResponse>>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<List<OrderRequestAndResponse>>> task =
                new RequestServerAsyncTask<HttpResponse<List<OrderRequestAndResponse>>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<List<OrderRequestAndResponse>> httpResponse) {
                        orderPullListView.onPullDownRefreshComplete();
                        orderPullListView.setLastUpdatedLabel(StringUtil.getHmsOfDate(new Date()));
                        if (httpResponse.getError() == null) {
                            List<OrderRequestAndResponse> responseParams = httpResponse.getResponseParams();
                            if (responseParams != null) {
                                startIndex += responseParams.size();
                                if (responseParams.size() < Constant.QUERY_ORDER_COUNT)
                                    orderPullListView.setHasMoreData(false);
                                else
                                    orderPullListView.onPullUpRefreshComplete();

                                orderList.addAll(responseParams);
                                adapter.setData(orderList);
                            } else
                                orderPullListView.onPullUpRefreshComplete();
                        } else {
                            orderPullListView.onPullUpRefreshComplete();
                            showText(httpResponse.getError().getMessage());
                        }
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    protected abstract List<Integer> getStatus();

    public abstract String getTitleString();
}
