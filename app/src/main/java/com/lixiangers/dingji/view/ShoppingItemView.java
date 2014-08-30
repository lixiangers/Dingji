package com.lixiangers.dingji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.BaseItemView;
import com.lixiangers.dingji.model.ShoppingItem;
import com.lixiangers.dingji.util.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShoppingItemView extends BaseItemView<ShoppingItem> {
    private final Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ImageView goodsImage;
    private TextView goodsNameTextView;
    private TextView goodsPriceTextView;

    private SelectQuantityView selectQuantityView;
    private TextView amountTextView;
    private ShoppingItem shoppingItem;

    public ShoppingItemView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public ShoppingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    @Override
    public void setModel(ShoppingItem model) {
        this.shoppingItem = model;
        super.setModel(model);
        goodsNameTextView.setText(model.getGoods().getName());
        goodsPriceTextView.setText(model.getGoods().getPriceAndUnit());
        selectQuantityView.setNumber(model.getQuantity());

        String uri;
        uri = model.getGoods().getImg() == null || model.getGoods().getImg().isEmpty() ?
                "" : model.getGoods().getImg().get(0);

        imageLoader.displayImage(uri, goodsImage, options);
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_shopping_item, this);
        goodsImage = (ImageView) findViewById(R.id.iv_goods_image);
        goodsNameTextView = (TextView) findViewById(R.id.goods_name);
        goodsPriceTextView = (TextView) findViewById(R.id.goods_price);

        selectQuantityView = (SelectQuantityView) findViewById(R.id.select_quantity_view);
        amountTextView = (TextView) findViewById(R.id.tv_amount);

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

        selectQuantityView.setOnNumberChangeListener(new SelectQuantityView.onNumberChangeListener() {
            @Override
            public void onNumberChange(int number) {
                shoppingItem.setQuantity(number);
                amountTextView.setText(StringUtil.formatTemplateString(R.string.amount_yuan, shoppingItem == null ? 0 : shoppingItem.getTotalAmount() / 100f));
                shoppingItem.getOnNumberChangeListener().onNumberChange(number);
            }
        });
    }
}
