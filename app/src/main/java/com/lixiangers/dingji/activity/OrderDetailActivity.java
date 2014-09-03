package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.model.Goodses;
import com.lixiangers.dingji.model.OrderStatus;
import com.lixiangers.dingji.protocol.domain.ChangeOrderStatusRequest;
import com.lixiangers.dingji.protocol.domain.QueryOrderDeTailRequest;
import com.lixiangers.dingji.protocol.domain.QueryOrderDetailResponse;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.view.OrderRouteView;
import com.lixiangers.dingji.view.OrderStatusListDialogFragment;
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
    private TextView orderStatusEditView;
    public static final String COMPANY_DIALOG = "company_dialog";


    private OrderStatusListDialogFragment statusListDialogFragment;
    private OrderStatus currentStatus;
    private Button changeOrderStatusButton;
    private View changeOrderStausView;
    private boolean isNeedChangeOrder;


    public OrderDetailActivity() {
        super(R.layout.activity_order_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        isNeedChangeOrder = getIntent().getBooleanExtra(Constant.IS_NEED_CHANGE_ORDER, false);
        String orderNumber = getIntent().getStringExtra(Constant.ORDER_NUMBER);
        setTitle(getString(R.string.order) + orderNumber);
        setLeftImage(R.drawable.selector_bg_back);


        contactTextView = (TextView) findViewById(R.id.tv_contact);
        phoneTextView = (TextView) findViewById(R.id.tv_phone);
        detailTextView = (TextView) findViewById(R.id.tv_detail_area);
        routeLinerLayout = (LinearLayout) findViewById(R.id.liner_order_status);

        linerGoodsView = (LinearLayout) findViewById(R.id.liner_goods);
        orderStatusEditView = (TextView) findViewById(R.id.et_order_status);
        changeOrderStatusButton = (Button) findViewById(R.id.bt_change_status);
        changeOrderStausView = findViewById(R.id.view_change_status);

        if (isNeedChangeOrder) {
            changeOrderStausView.setVisibility(View.VISIBLE);
            routeLinerLayout.setVisibility(View.GONE);
        }

        statusListDialogFragment = new OrderStatusListDialogFragment();

        orderStatusEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusListDialogFragment.setCompany(currentStatus);
                showDialog(statusListDialogFragment, COMPANY_DIALOG);
            }
        });

        statusListDialogFragment.setOnSelectedListener(new OrderStatusListDialogFragment.OnSelectedListener() {
            @Override
            public void onSelected(OrderStatus orderOption) {
                currentStatus = orderOption;
                orderStatusEditView.setText(currentStatus.getDes());
            }
        });

        changeOrderStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrderStatus();
            }
        });

        queryOrderDetail();
    }

    private void showDialog(DialogFragment dialogFragment, String dialogTag) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(dialogTag);
        if (fragmentByTag != null && fragmentByTag.isAdded())
            dialogFragment.dismiss();

        dialogFragment.show(getSupportFragmentManager(), dialogTag);
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

    private void changeOrderStatus() {
        showRequestDialog(OrderDetailActivity.this, getString(R.string.is_change_order_status));
        ChangeOrderStatusRequest request = new ChangeOrderStatusRequest(orderId, currentStatus.getIndex());
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.set_order_status, request);

        Type type = new TypeToken<HttpResponse<String>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<String>> task =
                new RequestServerAsyncTask<HttpResponse<String>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<String> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.getError() == null) {
                            showText(getString(R.string.change_order_status_success));
                            finish();
                        } else {
                            showText(httpResponse.getError().getMessage());
                        }
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    private void resetOrderInfo(HttpResponse<QueryOrderDetailResponse> httpResponse) {
        currentStatus = OrderStatus.findOrderStatusByIndex(httpResponse.getResponseParams().getStatus());
        orderStatusEditView.setText(currentStatus.getDes());

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
