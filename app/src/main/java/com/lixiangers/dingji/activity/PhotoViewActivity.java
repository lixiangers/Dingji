package com.lixiangers.dingji.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lixiangers.dingji.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.view.Window.FEATURE_NO_TITLE;


public class PhotoViewActivity extends Activity {

    private static ImageLoader imageLoader;
    private static DisplayImageOptions options;
    private ViewPager viewPager;
    private List<String> imageUrl = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_photoview);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        imageUrl = getIntent().getStringArrayListExtra("imageUrl");
        int imageIndex = getIntent().getIntExtra("imageIndex", 0);

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

        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(imageIndex);
    }

    class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageUrl.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            imageLoader.displayImage(imageUrl.get(position), photoView, options);
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

