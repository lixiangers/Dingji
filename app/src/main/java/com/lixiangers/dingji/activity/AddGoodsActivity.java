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
import com.lixiangers.dingji.view.AddPictureView;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class AddGoodsActivity extends NeolixNaviagationBaseActivity {
    private EditText nameInput;
    private EditText priceInput;
    private Button backButton;
    private Button doneButton;
    private EditText barcodeInput;
    private Intent serviceIntent;
//    public List<Goods> addGoodsList;
    private AddPictureView pictureView;
    private ArrayList<String> bitmapList;
//    private RuntimeDatabaseHelper databaseHelper;

    public AddGoodsActivity() {
        super(R.layout.activity_add_goods);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setTitle(getString(R.string.add_goods));
//        initView();
//        initListener();
//
////        databaseHelper = RuntimeDatabaseHelper.runtimeDatabaseHelper(getApplicationContext());
//        addGoodsList = new ArrayList<Goods>();
//        bitmapList = new ArrayList<String>();
//        pictureView.setImageList(AddGoodsActivity.this, bitmapList);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != RESULT_OK)
//            return;
//
//        switch (requestCode) {
//            case Constant.PHOTO_PICKED:
//                if (data == null)
//                    return;
//
//                bitmapList.add(AddPictureView.currentImageUrl);
//                pictureView.setImageList(AddGoodsActivity.this, bitmapList);
//                break;
//            case Constant.TAKE_PHONE:
//                cropImageUri(Uri.parse(AddPictureView.currentImageUrl), GOODS_PICTURE_WIDTH, GOODS_PICTURE_HEIGHT, REQUEST_CROP_IMAGE);
//                break;
//            case REQUEST_CROP_IMAGE:
//                bitmapList.add(AddPictureView.currentImageUrl);
//                pictureView.setImageList(AddGoodsActivity.this, bitmapList);
//                break;
//        }
//    }
//
//    private void initListener() {
//        LocalTextWatcher textWatcher = new LocalTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                super.onTextChanged(charSequence, i, i2, i3);
//                doneButton.setEnabled(isNotBlank(getTextFrom(nameInput)) &&
//                        isNotBlank(getTextFrom(priceInput)) &&
//                        isNotBlank(getTextFrom(barcodeInput)));
//            }
//        };
//
//        nameInput.addTextChangedListener(textWatcher);
//        barcodeInput.addTextChangedListener(textWatcher);
//        priceInput.addTextChangedListener(new LocalTextWatcher() {
//            @Override
//            public void afterTextChanged(Editable editable) {
//                super.afterTextChanged(editable);
//                String temp = editable.toString();
//                int posDot = temp.indexOf(".");
//                if (posDot <= 0) return;
//                if (temp.length() - posDot - 1 > 2) {
//                    editable.delete(posDot + 3, posDot + 4);
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                super.onTextChanged(charSequence, i, i2, i3);
//                doneButton.setEnabled(isNotBlank(getTextFrom(nameInput)) &&
//                        isNotBlank(getTextFrom(priceInput)) &&
//                        isNotBlank(getTextFrom(barcodeInput)));
//            }
//        });
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancelAdd();
//            }
//        });
//
//        doneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addGoods();
//            }
//        });
//
//        pictureView.setDeleteBitmapListener(new AddPictureView.onDeleteBitmapListener() {
//            @Override
//            public void onDelete(String bitmap) {
//                removeBitmap(bitmapList, bitmap);
//                pictureView.setImageList(AddGoodsActivity.this, bitmapList);
//            }
//        });
//    }
//
//    private void addGoods() {
//        String name = getTextFrom(nameInput);
//        String price = getTextFrom(priceInput);
//        String barcode = getTextFrom(barcodeInput);
//
//        float realPrice = Float.parseFloat(price);
//        final Goods goods = new Goods(getUserID(), name, realPrice, barcode);
//
//        EditGoodsReqHttpEntity params = new EditGoodsReqHttpEntity(getUserID(), goods);
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
//                                    nameInput.getText().toString(),
//                                    Float.parseFloat(priceInput.getText().toString()),
//                                    barcodeInput.getText().toString());
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
//                            nameInput.setText(EMPTY);
//                            priceInput.setText(EMPTY);
//                            barcodeInput.setText(EMPTY);
//                            nameInput.requestFocus();
//                            addGoodsList.add(resultGoods);
//                            bitmapList.clear();
//                            pictureView.setImageList(AddGoodsActivity.this, bitmapList);
//                            showText(getString(R.string.add_goods_success));
//                        } else
//                            showText(httpResponse.getError().getMessage());
//                    }
//                };
//        task.sendRequest(httpRequest);
//    }
//
//    private void initView() {
//
//        nameInput = (EditText) findViewById(R.id.et_good_name_input);
//        priceInput = (EditText) findViewById(R.id.et_good_price_input);
//        barcodeInput = (EditText) findViewById(R.id.et_good_barcode_input);
//        backButton = (Button) findViewById(R.id.negative_button);
//        doneButton = (Button) findViewById(R.id.positive_button);
//        pictureView = (AddPictureView) findViewById(R.id.add_picture_view);
//
//        Intent intent = getIntent();
//        Goods goods = (Goods) intent.getSerializableExtra(GOODS);
//        if (goods != null) {
//            barcodeInput.setText(goods.getBarcode());
//            nameInput.setText(goods.getName());
//            isFromScanBarcode = true;
//        }
//    }
//
//    private void cancelAdd() {
//            Intent intent = new Intent();
//            intent.putExtra(Constant.GOODS_LIST, (Serializable) addGoodsList);
//            setResult(RESULT_OK, intent);
//            finish();
//    }
//
//    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", GOODS_PICTURE_SCALE_WIDTH);
//        intent.putExtra("aspectY", GOODS_PICTURE_SCALE_HEIGHT);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        intent.putExtra("return-data", false);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent, requestCode);
//    }
//
//    private String getImageUrls() {
//        String urls = "";
//        if (bitmapList != null) {
//            for (int i = 0; i < bitmapList.size(); i++) {
//                urls += bitmapList.get(i);
//
//                if (i < bitmapList.size() - 1)
//                    urls += Constant.PICTURE_IMAGE_URL_SPLIT;
//            }
//        }
//        return urls;
//    }
//
//    private void removeBitmap(List<String> list, String bitmap) {
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            if (iterator.next().equals(bitmap)) {
//                iterator.remove();
//            }
//        }
//    }
}
