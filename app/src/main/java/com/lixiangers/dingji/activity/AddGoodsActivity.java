package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.model.Goods;
import com.lixiangers.dingji.protocol.domain.AddGoodsRequest;
import com.lixiangers.dingji.protocol.domain.UploadImageRequest;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.LocalTextWatcher;
import com.lixiangers.dingji.util.StringUtil;
import com.lixiangers.dingji.view.AddPictureView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isNotBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class AddGoodsActivity extends NeolixNaviagationBaseActivity {
    private EditText nameEditText;
    private EditText priceEditText;
    private Button deleteButton;
    private Button doneButton;
    private EditText unitEditText;
    public List<Goods> addGoodsList;
    private AddPictureView pictureView;
    private ArrayList<String> bitmapList;
    private EditText goodsDesEditText;
    private Goods goods;
    private EditText catetoryEditText;

    public AddGoodsActivity() {
        super(R.layout.activity_add_goods);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        goods = (Goods) intent.getSerializableExtra(Constant.GOODS_ITEM_VIEW_MODEL);
        setTitle(goods == null ? R.string.add_goods : R.string.edit_goods);
        setLeftImage(R.drawable.selector_bg_back);

        initView();
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case Constant.PHOTO_PICKED:
                if (data == null)
                    return;
                uploadImage();
                break;
            case Constant.TAKE_PHONE:
                cropImageUri(Uri.parse(AddPictureView.currentImageUrl),
                        Constant.GOODS_PICTURE_WIDTH, Constant.GOODS_PICTURE_HEIGHT, Constant.REQUEST_CROP_IMAGE);
                break;
            case Constant.REQUEST_CROP_IMAGE:
                uploadImage();
                break;
        }
    }

    private void initListener() {
        LocalTextWatcher textWatcher = new LocalTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                super.onTextChanged(charSequence, i, i2, i3);
                doneButton.setEnabled(isNotBlank(getTextFrom(nameEditText)) &&
                        isNotBlank(getTextFrom(priceEditText)) &&
                        isNotBlank(getTextFrom(catetoryEditText)) &&
                        isNotBlank(getTextFrom(unitEditText)));
            }
        };

        nameEditText.addTextChangedListener(textWatcher);
        goodsDesEditText.addTextChangedListener(textWatcher);
        unitEditText.addTextChangedListener(textWatcher);
        catetoryEditText.addTextChangedListener(textWatcher);
        priceEditText.addTextChangedListener(new LocalTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                String temp = editable.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    editable.delete(posDot + 3, posDot + 4);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                super.onTextChanged(charSequence, i, i2, i3);
                doneButton.setEnabled(isNotBlank(getTextFrom(nameEditText)) &&
                        isNotBlank(getTextFrom(priceEditText)) &&
                        isNotBlank(getTextFrom(unitEditText)));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoods();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGoods();
            }
        });

        pictureView.setDeleteBitmapListener(new AddPictureView.onDeleteBitmapListener() {
            @Override
            public void onDelete(String bitmap) {
                removeBitmap(bitmapList, bitmap);
                pictureView.setImageList(AddGoodsActivity.this, bitmapList);
            }
        });
    }

    private void initData() {
        addGoodsList = new ArrayList<Goods>();
        bitmapList = new ArrayList<String>();

        if (goods != null) {
            unitEditText.setText(goods.getUnit());
            nameEditText.setText(goods.getName());
            goodsDesEditText.setText(goods.getDetail());
            priceEditText.setText(String.valueOf(goods.getPriceOfYuan()));
            catetoryEditText.setText(goods.getCategory());
            bitmapList = goods.getImg();
        }
        pictureView.setImageList(AddGoodsActivity.this, bitmapList);
    }

    private void saveGoods() {
        String name = getTextFrom(nameEditText);
        String des = getTextFrom(goodsDesEditText);
        String price = getTextFrom(priceEditText);
        String unit = getTextFrom(unitEditText);
        String category = getTextFrom(catetoryEditText);
        float realPrice = Float.parseFloat(price);

        if (goods == null)
            goods = new Goods();

        goods.setPrice((int) (realPrice * 100));
        goods.setName(name);
        goods.setDetail(des);
        goods.setUnit(unit);
        goods.setCategory(category);

        AddGoods(goods);
    }

    private void initView() {
        nameEditText = (EditText) findViewById(R.id.et_good_name_input);
        priceEditText = (EditText) findViewById(R.id.et_goods_price);
        unitEditText = (EditText) findViewById(R.id.et_goods_unit);
        goodsDesEditText = (EditText) findViewById(R.id.et_goods_des);
        catetoryEditText = (EditText) findViewById(R.id.et_goods_category);

        deleteButton = (Button) findViewById(R.id.negative_button);
        doneButton = (Button) findViewById(R.id.positive_button);

        pictureView = (AddPictureView) findViewById(R.id.add_picture_view);
    }

    private void deleteGoods() {
        //TODO delete goods
        Intent intent = new Intent();
        intent.putExtra(Constant.IS_DELETE, true);
        intent.putExtra(Constant.GOODS_ITEM_VIEW_MODEL, goods);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", Constant.GOODS_PICTURE_SCALE_WIDTH);
        intent.putExtra("aspectY", Constant.GOODS_PICTURE_SCALE_HEIGHT);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, requestCode);
    }

    private void removeBitmap(List<String> list, String bitmap) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(bitmap)) {
                iterator.remove();
            }
        }
    }

    private void uploadImage() {
        showRequestDialog(AddGoodsActivity.this, getString(R.string.is_upload_image));
        Log.d(Constant.HttpConstant.TAG, "currentImagurl:" + AddPictureView.currentImageUrl);
        UploadImageRequest params = new UploadImageRequest(getBase64(AddPictureView.currentImageUrl));
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.upload_img, params);

        Type type = new TypeToken<HttpResponse<String>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<String>> task =
                new RequestServerAsyncTask<HttpResponse<String>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<String> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            deleteOriginImagePath(AddPictureView.currentImageUrl);
                            bitmapList.add(httpResponse.getResponseParams());
                            pictureView.setImageList(AddGoodsActivity.this, bitmapList);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    private void AddGoods(final Goods goods) {
        showRequestDialog(AddGoodsActivity.this, getString(R.string.is_add_goods));
        String[] toBeStored = new String[]{};
        AddGoodsRequest params = new AddGoodsRequest(goods.getName(), goods.getUnit(),
                goods.getPrice(), bitmapList.get(0), bitmapList.toArray(toBeStored),
                goods.getCategory(), goods.getDetail());
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.add_product, params);

        Type type = new TypeToken<HttpResponse<String>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<String>> task =
                new RequestServerAsyncTask<HttpResponse<String>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<String> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            Intent intent = new Intent();
                            intent.putExtra(Constant.GOODS_ITEM_VIEW_MODEL, goods);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    private String getBase64(String url) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
        Bitmap bitmap = imageLoader.loadImageSync(url, options);
        return StringUtil.bitmapToBase64(bitmap);
    }

    private void deleteOriginImagePath(String originHeadImagePath1) {
        if (isNotBlank(originHeadImagePath1)) {
            String path = getPath(originHeadImagePath1);
            File deleteFile = new File(path);
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }
    }

    private String getPath(String url) {
        //file:///sdcard/wepayGoods//20140504170125.jpg
        if (url.length() < 7)
            return "";
        return url.substring(7, url.length());
    }
}
