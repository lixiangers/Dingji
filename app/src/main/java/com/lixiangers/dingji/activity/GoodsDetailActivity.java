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
import com.lixiangers.dingji.model.Goods;
import com.lixiangers.dingji.model.OrderItem;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.DensityUtil;
import com.lixiangers.dingji.view.SelectQuantityView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.lixiangers.dingji.util.StringUtil.showText;

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
    private SelectQuantityView selectQuantityView;

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
        selectQuantityView = (SelectQuantityView) findViewById(R.id.select_quantity_view);

        int images = goods.getImg().isEmpty() ? 1 : goods.getImg().size();
        for (int i = 0; i < images; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.selector_point);
            imageView.setEnabled(false);
            int padding = DensityUtil.dip2px(getApplicationContext(), 15f);
            imageView.setPadding(padding, padding, padding, padding);
            pointsView.addView(imageView);
            pointsImageList.add(imageView);
        }
        goodsDesTextView.setText(goods.getDetail());
        goodsPriceTextView.setText(goods.getPriceAndUnit());
    }

    private void initListener() {
        byButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectQuantityView.getNumber() < 1)
                    showText(getString(R.string.quantity_must_greater_than_0));

                OrderItem orderItem = new OrderItem();
                orderItem.setGoods(goods);
                orderItem.setQuantity(selectQuantityView.getNumber());
                ShoppingCartFragment.addGoods(orderItem);

                goToNextWithBundle(Constant.TAB_SHOPPING_CART, MainActivity.class, Constant.TAB_TAG);
                finish();
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
            return goods.getImg().isEmpty() ? 1 : goods.getImg().size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            imageLoader.displayImage(goods.getImg().isEmpty() ?
                    "" : goods.getImg().get(position), photoView, options);
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
