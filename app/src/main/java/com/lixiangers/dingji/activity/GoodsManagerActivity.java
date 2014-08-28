package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.model.Goods;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.OnClickListener;
import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class GoodsManagerActivity extends NeolixNaviagationBaseActivity {

    public static final int REQUEST_CODE_EDIT = 2;
    public static final int REQUEST_CODE_ADD = 1;
    private View addGoodsView;
    private ListView goodsListView;
    private ModelListAdapter<GoodsItemViewModel> listAdapter;
    private List<GoodsItemViewModel> models = new ArrayList<GoodsItemViewModel>();
    private int currentIndex;

    public GoodsManagerActivity() {
        super(R.layout.activity_goods_manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.goods_manager);
        setLeftImage(R.drawable.selector_bg_back);

        initView();
        initListView();
        initListener();
        loadGoods();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (REQUEST_CODE_ADD == requestCode) {
            Goods goods = (Goods) data.getSerializableExtra(Constant.GOODS_ITEM_VIEW_MODEL);
            GoodsItemViewModel viewModel = new GoodsItemViewModel();
            viewModel.setGoods(goods);
            setViewModelListener(viewModel);
            models.add(viewModel);
            listAdapter.setData(models);
        } else if (REQUEST_CODE_EDIT == requestCode) {
            boolean isDelete = data.getBooleanExtra(Constant.IS_DELETE, false);
            Goods goods = (Goods) data.getSerializableExtra(Constant.GOODS_ITEM_VIEW_MODEL);
            if (isDelete) {
                if (currentIndex > -1)
                    models.remove(currentIndex);
            } else {
                if (currentIndex > -1) {
                    GoodsItemViewModel viewModel = new GoodsItemViewModel();
                    viewModel.setGoods(goods);
                    setViewModelListener(viewModel);
                    models.set(currentIndex, viewModel);
                }
            }
            listAdapter.setData(models);
        }
    }

    private void setViewModelListener(final GoodsItemViewModel viewModel) {
        viewModel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditGoodsActivity.class);
                intent.putExtra(Constant.GOODS_ITEM_VIEW_MODEL, viewModel.getGoods());
                currentIndex = models.indexOf(viewModel);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    private void initView() {
        addGoodsView = findViewById(R.id.iv_add_goods);
        goodsListView = (ListView) findViewById(R.id.lv_goods);
    }

    private void initListView() {
        listAdapter = new ModelListAdapter<GoodsItemViewModel>(getApplicationContext());
        goodsListView.setAdapter(listAdapter);
    }

    private void initListener() {
        addGoodsView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditGoodsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
    }

    private void loadGoods() {
        showRequestDialog(this, getString(R.string.is_query_goods));
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.list_product, null);

        Type type = new TypeToken<HttpResponse<List<Goods>>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<List<Goods>>> task =
                new RequestServerAsyncTask<HttpResponse<List<Goods>>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<List<Goods>> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            List<Goods> goodsList = httpResponse.getResponseParams();
                            for (Goods goods1 : goodsList) {
                                GoodsItemViewModel viewModel = new GoodsItemViewModel();
                                viewModel.setGoods(goods1);
                                setViewModelListener(viewModel);
                                models.add(viewModel);
                            }
                            listAdapter.setData(models);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }
}
