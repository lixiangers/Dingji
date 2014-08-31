package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.model.OrderStatus;
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

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.showText;
import static java.util.Arrays.asList;

public class QueryOrderActivity extends NeolixNaviagationBaseActivity {

    private PullToRefreshListView orderPullListView;
    private ModelListAdapter<OrderRequestAndResponse> adapter;
    private List<OrderRequestAndResponse> orderList;
    private int startIndex = 0;
    private ImageView queryImageView;
    private EditText keywordEditView;

    public QueryOrderActivity() {
        super(R.layout.activity_query_order);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.my_order);

        queryImageView = (ImageView) findViewById(R.id.iv_query);
        keywordEditView = (EditText) findViewById(R.id.et_key_word);
        orderPullListView = (PullToRefreshListView) findViewById(R.id.pull_lv_order);
        adapter = new ModelListAdapter<OrderRequestAndResponse>(getApplicationContext());
        orderList = new ArrayList<OrderRequestAndResponse>();

        queryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = getTextFrom(keywordEditView);
                startIndex = 0;
                orderList.clear();
                queryOrder(keyword);
            }
        });

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

    private void loadOrder() {
        QueryOrderListRequest request = new QueryOrderListRequest();
        request.setStatus(asList(OrderStatus.SUBMIT_ORDER.getIndex(),
                OrderStatus.START_TRANSPORT.getIndex(),
                OrderStatus.SUCCESS.getIndex()));

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

    private void queryOrder(String keywords) {
        showRequestDialog(QueryOrderActivity.this, getString(R.string.is_query));
        QueryOrderListRequest request = new QueryOrderListRequest();
        request.setKeyword(keywords);
        request.setStart(startIndex);
        request.setCount(Constant.QUERY_ORDER_COUNT);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.search_order, request);

        Type type = new TypeToken<HttpResponse<List<OrderRequestAndResponse>>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<List<OrderRequestAndResponse>>> task =
                new RequestServerAsyncTask<HttpResponse<List<OrderRequestAndResponse>>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<List<OrderRequestAndResponse>> httpResponse) {
                        hideRequestDialog();
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
}
