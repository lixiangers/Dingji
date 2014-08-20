package com.lixiangers.dingji.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.DensityUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailActivity extends NeolixNaviagationBaseActivity {

    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ViewPager viewPager;
    private Goods goods;
    private Button byButton;
    private TextView goodsPriceTextView;
    private TextView goodsDesTextView;
    private LinearLayout pointsView;
    private List<ImageView> pointsImageList;
    private int currentIndex = 0;

    public GoodsDetailActivity() {
        super(R.layout.activity_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goods = (Goods) getIntent().getSerializableExtra(Constant.GOODS);
        setTitle(goods.getName());
        pointsImageList = new ArrayList<ImageView>();

        initView();
        initViewpager();
        initListener();
        viewPager.setCurrentItem(0);
        setCurrentDot(currentIndex);
    }

    private void initView() {
        goodsDesTextView = (TextView) findViewById(R.id.tv_goods_des);
        goodsPriceTextView = (TextView) findViewById(R.id.tv_goods_price);
        byButton = (Button) findViewById(R.id.bt_by);
        pointsView = (LinearLayout) findViewById(R.id.points_view);

        int images = goods.getImageArrayList().isEmpty() ? 1 : goods.getImageArrayList().size();
        for (int i = 0; i < images; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.selector_point);
            imageView.setEnabled(false);
            int padding = DensityUtil.dip2px(getApplicationContext(), 15f);
            imageView.setPadding(padding, padding, padding, padding);
            pointsView.addView(imageView);
            pointsImageList.add(imageView);
        }
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

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void setCurrentDot(int position) {
        pointsImageList.get(currentIndex).setEnabled(false);
        pointsImageList.get(position).setEnabled(true);

        currentIndex = position;
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
