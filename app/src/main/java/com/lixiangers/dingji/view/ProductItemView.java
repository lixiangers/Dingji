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
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.model.Goodses;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductItemView extends BaseItemView<Goodses> {
    private final Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ImageView goodsImage;
    private TextView goodsNameTextView;
    private TextView goodsPriceTextView;
    private View rightView;
    private View byGoodsView;
    private TextView countTextView;

    public ProductItemView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public ProductItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    @Override
    public void setModel(Goodses model) {
        super.setModel(model);
        goodsNameTextView.setText(model.getName());
        goodsPriceTextView.setText(model.getPriceAndUnit());

        countTextView.setText(String.valueOf(model.getCount()));

        String uri;
        uri = model.getImg() == null || model.getImg().isEmpty() ?
                "" : model.getImg().get(0);

        imageLoader.displayImage(uri, goodsImage, options);
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_product_item, this);
        goodsImage = (ImageView) findViewById(R.id.iv_goods_image);
        goodsNameTextView = (TextView) findViewById(R.id.goods_name);
        goodsPriceTextView = (TextView) findViewById(R.id.goods_price);

        countTextView = (TextView) findViewById(R.id.tv_count);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_default_head)
                .showImageForEmptyUri(R.drawable.ic_default_head)
                .showImageOnFail(R.drawable.ic_default_head)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader = MyApplication.getInstance().getImageLoader();
    }
}
