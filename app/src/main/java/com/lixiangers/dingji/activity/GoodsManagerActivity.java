package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.model.Goods;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OnClickListener;

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
                Intent intent = new Intent(getApplicationContext(), AddGoodsActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), AddGoodsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
    }

    private void loadGoods() {
        //TODO get goods from server
        listAdapter.setData(models);
    }
}
