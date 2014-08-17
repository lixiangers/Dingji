package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.database.RunTimeDatabaseHelper;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.LocalTextWatcher;
import com.lixiangers.dingji.view.AddPictureView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isNotBlank;

public class AddGoodsActivity extends NeolixNaviagationBaseActivity {
    private EditText nameEditText;
    private EditText priceEditText;
    private Button deleteButton;
    private Button doneButton;
    private EditText unitEditText;
    public List<Goods> addGoodsList;
    private AddPictureView pictureView;
    private ArrayList<String> bitmapList;
    private RunTimeDatabaseHelper databaseHelper;
    private EditText goodsDesEditText;
    private Goods goods;

    public AddGoodsActivity() {
        super(R.layout.activity_add_goods);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        goods = (Goods) intent.getSerializableExtra(Constant.GOODS_ITEM_VIEW_MODEL);
        setTitle(goods == null ? R.string.add_goods : R.string.edit_goods);
        setLeftButton(R.drawable.selector_bg_back);

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

                bitmapList.add(AddPictureView.currentImageUrl);
                pictureView.setImageList(AddGoodsActivity.this, bitmapList);
                break;
            case Constant.TAKE_PHONE:
                cropImageUri(Uri.parse(AddPictureView.currentImageUrl),
                        Constant.GOODS_PICTURE_WIDTH, Constant.GOODS_PICTURE_HEIGHT, Constant.REQUEST_CROP_IMAGE);
                break;
            case Constant.REQUEST_CROP_IMAGE:
                bitmapList.add(AddPictureView.currentImageUrl);
                pictureView.setImageList(AddGoodsActivity.this, bitmapList);
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
                        isNotBlank(getTextFrom(unitEditText)));
            }
        };

        nameEditText.addTextChangedListener(textWatcher);
        goodsDesEditText.addTextChangedListener(textWatcher);
        unitEditText.addTextChangedListener(textWatcher);
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
        databaseHelper = RunTimeDatabaseHelper.initial(getApplicationContext());
        addGoodsList = new ArrayList<Goods>();
        bitmapList = new ArrayList<String>();

        if (goods != null) {
            unitEditText.setText(goods.getUnit());
            nameEditText.setText(goods.getName());
            goodsDesEditText.setText(goods.getDes());
            priceEditText.setText(String.valueOf(goods.getPriceOfYuan()));
            bitmapList = goods.getImageArrayList();
        }
        pictureView.setImageList(AddGoodsActivity.this, bitmapList);
    }

    private void saveGoods() {
        //TODO add goods
        String name = getTextFrom(nameEditText);
        String des = getTextFrom(goodsDesEditText);
        String price = getTextFrom(priceEditText);
        String unit = getTextFrom(unitEditText);
        float realPrice = Float.parseFloat(price);

        if (goods == null)
            goods = new Goods();

        goods.setId(DateTime.now().getMillis() + "");
        goods.setPrice((int) (realPrice * 100));
        goods.setName(name);
        goods.setDes(des);
        goods.setUnit(unit);
        goods.setImageArrayList(bitmapList);

        Intent intent = new Intent();
        intent.putExtra(Constant.GOODS_ITEM_VIEW_MODEL, goods);
        setResult(RESULT_OK, intent);
        finish();

//                EditGoodsReqHttpEntity params = new EditGoodsReqHttpEntity(getUserID(), goods);
//        HttpRequest httpRequest = new HttpRequest(
//                RequestType.add_goods, params);
//
//        Type type = new TypeToken<HttpResponseNew<EditGoodsResponse>>() {
//        }.getType();
//
//        RequestServerAsyncTaskNew<HttpResponseNew<EditGoodsResponse>> task =
//                new RequestServerAsyncTaskNew<HttpResponseNew<EditGoodsResponse>>(type) {
//
//                    @Override
//                    public void OnResponse(HttpResponseNew<EditGoodsResponse> httpResponse) {
//                        if (httpResponse.noErrorMessage()) {
//                            Goods resultGoods = new Goods(getUserID(),
//                                    nameEditText.getText().toString(),
//                                    Float.parseFloat(priceEditText.getText().toString()),
//                                    unitEditText.getText().toString());
//
//                            ArrayList<String> tempList = new ArrayList<String>();
//                            if (bitmapList != null)
//                                tempList.addAll(bitmapList);
//                            resultGoods.setImages(tempList);
//
//                            //把原来没有上传的记录标记为已上传(因为要删除对应的文件，所以不能直接删除对应的记录)
//                            GoodsPictureRecord oldRecord = databaseHelper.loadNotUploadPictureRecordByUserIdBarcode(resultGoods.getBarcode(), getUserID());
//                            if (oldRecord != null) {
//                                oldRecord.setUpload(true);
//                                databaseHelper.updatePictureRecordState(oldRecord);
//                            }
//
//                            GoodsPictureRecord record = new GoodsPictureRecord(getUserID(), resultGoods.getBarcode(), getImageUrls(), DateTime.now(), null, false);
//                            databaseHelper.insertPictureRecords(asList(record));
//
//                            nameEditText.setText(EMPTY);
//                            priceEditText.setText(EMPTY);
//                            unitEditText.setText(EMPTY);
//                            nameEditText.requestFocus();
//                            addGoodsList.add(resultGoods);
//                            bitmapList.clear();
//                            pictureView.setImageList(AddGoodsActivity.this, bitmapList);
//                            showText(getString(R.string.add_goods_success));
//                        } else
//                            showText(httpResponse.getError().getMessage());
//                    }
//                };
//        task.sendRequest(httpRequest);
    }

    private void initView() {
        nameEditText = (EditText) findViewById(R.id.et_good_name_input);
        priceEditText = (EditText) findViewById(R.id.et_goods_price);
        unitEditText = (EditText) findViewById(R.id.et_goods_unit);
        goodsDesEditText = (EditText) findViewById(R.id.et_goods_des);

        deleteButton = (Button) findViewById(R.id.negative_button);
        doneButton = (Button) findViewById(R.id.positive_button);

        pictureView = (AddPictureView) findViewById(R.id.add_picture_view);
    }

    private void deleteGoods() {
        //TODO delete goods
        Intent intent = new Intent();
        intent.putExtra(Constant.IS_DELETE, true);
        intent.putExtra(Constant.GOODS_ITEM_VIEW_MODEL, goods);
        setResult(RESULT_OK,intent);
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

    private String getImageUrls() {
        String urls = "";
        if (bitmapList != null) {
            for (int i = 0; i < bitmapList.size(); i++) {
                urls += bitmapList.get(i);

                if (i < bitmapList.size() - 1)
                    urls += Constant.PICTURE_IMAGE_URL_SPLIT;
            }
        }
        return urls;
    }

    private void removeBitmap(List<String> list, String bitmap) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(bitmap)) {
                iterator.remove();
            }
        }
    }
}
