package com.lixiangers.dingji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.BaseItemView;
import com.lixiangers.dingji.model.AddPictureItemViewModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AddImageItemView extends BaseItemView<AddPictureItemViewModel> {
    private final Context context;
    private ImageView imageView;
    private ImageButton deleteButton;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public AddImageItemView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public AddImageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.add_picture_item, this);
        imageView = (ImageView) findViewById(R.id.picture);
        deleteButton = (ImageButton) findViewById(R.id.delete);

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

    @Override
    public void setModel(AddPictureItemViewModel model) {
        super.setModel(model);
        if (model.isCanDelete()) {
            imageView.setBackgroundDrawable(null);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageLoader.displayImage(model.getBitmapURL(), imageView, options);
        } else {
            imageView.setBackgroundResource(R.drawable.selector_bg_add_picture);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageLoader.displayImage(model.getBitmapURL(), imageView, options);
//            imageView.setImageResource(R.drawable.ico_add_picture);
        }

        deleteButton.setVisibility(model.isCanDelete() ? VISIBLE : GONE);
        deleteButton.setOnClickListener(model.getOnDeleteListener());
        imageView.setOnClickListener(model.getOnAddListener());
    }
}
