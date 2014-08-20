package com.lixiangers.dingji.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.util.Constant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodsDetailActivity extends NeolixNaviagationBaseActivity {

    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ViewPager viewPager;
    private Goods goods;
    private Button byButton;
    private TextView goodsPriceTextView;
    private TextView goodsDesTextView;

    public GoodsDetailActivity() {
        super(R.layout.activity_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goods = (Goods) getIntent().getSerializableExtra(Constant.GOODS);
        setTitle(goods.getName());

        initView();
        initViewpager();
        initListener();
    }

    private void initView() {
        goodsDesTextView = (TextView) findViewById(R.id.tv_goods_des);
        goodsPriceTextView = (TextView) findViewById(R.id.tv_goods_price);
        byButton = (Button) findViewById(R.id.bt_by);

        goodsDesTextView.setText(goods.getDes());
        goodsPriceTextView.setText(goods.getPriceAndUnit());
    }

    private void initListener() {
        byButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 添加到购物车
            }
        });
    }

    private void initViewpager() {
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

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new SamplePagerAdapter());
    }

    class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return goods.getImageArrayList().isEmpty() ? 1 : goods.getImageArrayList().size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            imageLoader.displayImage(goods.getImageArrayList().isEmpty() ?
                    "" : goods.getImageArrayList().get(position), photoView, options);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
