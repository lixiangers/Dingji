package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.model.Goodses;
import com.lixiangers.dingji.protocol.domain.QueryOrderDeTailRequest;
import com.lixiangers.dingji.protocol.domain.QueryOrderDetailResponse;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.view.OrderRouteView;
import com.lixiangers.dingji.view.ProductItemView;

import java.lang.reflect.Type;
import java.util.List;

import static com.lixiangers.dingji.protocol.domain.QueryOrderDetailResponse.OrderRoute;
import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class OrderDetailActivity extends NeolixNaviagationBaseActivity {

    private TextView contactTextView;
    private TextView phoneTextView;
    private TextView detailTextView;
    private String orderId;
    private LinearLayout routeLinerLayout;
    private LinearLayout linerGoodsView;

    public OrderDetailActivity() {
        super(R.layout.activity_order_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        String orderNumber = getIntent().getStringExtra(Constant.ORDER_NUMBER);
        setTitle(getString(R.string.order) + orderNumber);

        contactTextView = (TextView) findViewById(R.id.tv_contact);
        phoneTextView = (TextView) findViewById(R.id.tv_phone);
        detailTextView = (TextView) findViewById(R.id.tv_detail_area);
        routeLinerLayout = (LinearLayout) findViewById(R.id.liner_order_status);

        linerGoodsView = (LinearLayout) findViewById(R.id.liner_goods);

        queryOrderDetail();
    }

    private void queryOrderDetail() {
        showRequestDialog(OrderDetailActivity.this, getString(R.string.is_query_order_detail));
        QueryOrderDeTailRequest request = new QueryOrderDeTailRequest(orderId);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.get_order, request);

        Type type = new TypeToken<HttpResponse<QueryOrderDetailResponse>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<QueryOrderDetailResponse>> task =
                new RequestServerAsyncTask<HttpResponse<QueryOrderDetailResponse>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<QueryOrderDetailResponse> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.getError() == null) {
                            resetOrderInfo(httpResponse);
                        } else {
                            showText(httpResponse.getError().getMessage());
                        }
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    private void resetOrderInfo(HttpResponse<QueryOrderDetailResponse> httpResponse) {
        contactTextView.setText(httpResponse.getResponseParams().getReceiver_name());
        phoneTextView.setText(httpResponse.getResponseParams().getReceiver_mobile());
        detailTextView.setText(httpResponse.getResponseParams().getWholeDetailAddress());

        List<OrderRoute> orderRoutes = httpResponse.getResponseParams().getRoute();
        routeLinerLayout.removeAllViews();
        for (OrderRoute route : orderRoutes) {
            OrderRouteView routeView = new OrderRouteView(getApplicationContext());
            routeView.setModel(route);
            routeLinerLayout.addView(routeView);
        }

        List<Goodses> products = httpResponse.getResponseParams().getProducts();
        linerGoodsView.removeAllViews();
        for (Goodses goodses : products) {
            ProductItemView view = new ProductItemView(getApplicationContext());
            view.setModel(goodses);
            linerGoodsView.addView(view);
        }
    }
}
