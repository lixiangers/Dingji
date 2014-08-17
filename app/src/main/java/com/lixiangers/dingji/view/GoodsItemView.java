package com.lixiangers.dingji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.BaseItemView;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodsItemView extends BaseItemView<GoodsItemViewModel> {
    private final Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ImageView goodsImage;
    private TextView goodsNameTextView;
    private TextView goodsPriceTextView;
    private View rightView;

    public GoodsItemView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public GoodsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    @Override
    public void setModel(GoodsItemViewModel model) {
        super.setModel(model);
        goodsNameTextView.setText(model.getGoods().getName());
        goodsPriceTextView.setText(String.valueOf(model.getGoods().getPrice()));
        rightView.setOnClickListener(model.getOnClickListener());

        String uri;
        uri = model.getGoods().getArrayList() == null || model.getGoods().getArrayList().isEmpty() ?
                "" : model.getGoods().getArrayList().get(0);

        imageLoader.displayImage(uri, goodsImage, options);
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_goods_item, this);
        goodsImage = (ImageView) findViewById(R.id.iv_goods_image);
        goodsNameTextView = (TextView) findViewById(R.id.goods_name);
        goodsPriceTextView = (TextView) findViewById(R.id.goods_price);

        rightView = findViewById(R.id.right_view);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_default_head)
                .showImageForEmptyUri(R.drawable.ic_default_head)
                .showImageOnFail(R.drawable.ic_default_head)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader = ImageLoader.getInstance();
    }
}
