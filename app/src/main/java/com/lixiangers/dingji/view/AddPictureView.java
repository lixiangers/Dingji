package com.lixiangers.dingji.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.activity.PhotoViewActivity;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.model.AddPictureItemViewModel;
import com.lixiangers.dingji.util.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.lixiangers.dingji.application.MyApplication.getInstance;
import static com.lixiangers.dingji.util.StringUtil.getGoodsImageName;

public class AddPictureView extends LinearLayout {
    private final Context context;
    private GridView gridView;
    private ModelListAdapter<AddPictureItemViewModel> adapter;
    private int[] menu_image_array;
    private String[] menu_name_array;
    public static String currentImageUrl;

    private Activity activity;
    private List<AddPictureItemViewModel> models;
    private Dialog menuDialog;
    private onDeleteBitmapListener deleteBitmapListener;
    private ArrayList<String> imageUrlList;

    public AddPictureView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public AddPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    public void setDeleteBitmapListener(onDeleteBitmapListener deleteBitmapListener) {
        this.deleteBitmapListener = deleteBitmapListener;
    }

    public void setImageList(Activity activity, ArrayList<String> bitmaps) {
        this.imageUrlList = bitmaps;
        this.activity = activity;
        models = new ArrayList<AddPictureItemViewModel>();
        if (bitmaps == null || bitmaps.size() == 0) {
            models.add(getAddPictureViewModel());
        } else {
            for (int i = 0; i < bitmaps.size(); i++) {
                models.add(getPictureItemViewModel(bitmaps.get(i), i));
            }
            if (bitmaps.size() < 3 && !haveAddPictureView())
                models.add(getAddPictureViewModel());
        }
        adapter.setData(models);
    }

    private AddPictureItemViewModel getPictureItemViewModel(final String bitmapUrl, final int index) {
        final AddPictureItemViewModel model = new AddPictureItemViewModel();
        model.setBitmapURL(bitmapUrl);
        model.setCanDelete(true);
        model.setOnDeleteListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteBitmapListener != null)
                    deleteBitmapListener.onDelete(model.getBitmapURL());
            }
        });
        model.setOnAddListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PhotoViewActivity.class);
                intent.putStringArrayListExtra("imageUrl", imageUrlList);
                intent.putExtra("imageIndex", index);
                activity.startActivity(intent);
            }
        });
        return model;
    }

    private boolean haveAddPictureView() {
        if (models == null && models.size() == 0)
            return false;
        for (AddPictureItemViewModel model : models) {
            if (!model.isCanDelete())
                return true;
        }

        return false;
    }

    private AddPictureItemViewModel getAddPictureViewModel() {
        AddPictureItemViewModel model = new AddPictureItemViewModel();
        model.setCanDelete(false);
        model.setBitmapURL("drawable://" + R.drawable.ico_add_picture);
        model.setOnAddListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuDialog == null)
                    initShareDialog();
                menuDialog.show();
            }
        });
        return model;
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_add_picture, this);
        gridView = (GridView) findViewById(R.id.gridView);

        models = new ArrayList<AddPictureItemViewModel>();
        adapter = new ModelListAdapter<AddPictureItemViewModel>(getInstance());
        gridView.setAdapter(adapter);
    }

    private void initShareDialog() {
        menuDialog = new Dialog(activity, R.style.my_dialog);
        menuDialog.setContentView(R.layout.share_dialog_view);
        setDialog();

        Button takePictureButton = (Button) menuDialog.findViewById(R.id.take_picture);
        Button fromPictureButton = (Button) menuDialog.findViewById(R.id.from_picture);
        Button cancelButton = (Button) menuDialog.findViewById(R.id.cancel_button);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog.dismiss();
                doTakePhoto();
            }
        });

        fromPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog.dismiss();
                doSelectImageFromLocal();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog.dismiss();
            }
        });
    }

    private void setDialog() {
        Window window = menuDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle);

        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        window.setAttributes(lp);
    }

    protected void doTakePhoto() {
        try {
            currentImageUrl = getGoodsImageName();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(currentImageUrl));
            activity.startActivityForResult(cameraIntent, Constant.TAKE_PHONE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doSelectImageFromLocal() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

        currentImageUrl = getGoodsImageName();
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", Constant.GOODS_PICTURE_SCALE_WIDTH);
        intent.putExtra("aspectY", Constant.GOODS_PICTURE_SCALE_HEIGHT);
        intent.putExtra("outputX", Constant.GOODS_PICTURE_WIDTH);
        intent.putExtra("outputY", Constant.GOODS_PICTURE_HEIGHT);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(currentImageUrl));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        activity.startActivityForResult(intent, Constant.PHOTO_PICKED);
    }

    public interface onDeleteBitmapListener {
        void onDelete(String bitmap);
    }
}
