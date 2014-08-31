package com.lixiangers.dingji.util;


import com.lixiangers.dingji.protocol.http.HttpClient;

import java.nio.charset.Charset;

public class Constant {
    public static final String EXPRESS_NEW_ACCEPT_ORDER = "EXPRESS_NEW_ACCEPT_ORDER";
    public static final String EXPRESS_NEW_PENDING_ORDER = "express_new_pending_order";

    public static final String ORDER_ID = "order_id";
    public static final String NETWORK_CONNECT_ERROR_CODE = "500";
    public static final String BARCODE = "barcode";
    public static final String FAILURE_TYPE = "failure_type";
    public static final String ACCEPT_ORDER_OVERDUE = "accept_order_overdue";
    public static final String VIEW_MODELS = "view_models";
    public static final String ACTION_REGISTER_CODE = "action_register_code";
    public static final String REGISTER_CODE = "register_code";
    public static final String ACTION_CANCEL_ORDER = "action_cancel_order";
    public static final int TOKEN_ERROR_CODE = 400;
    public static final String IS_SHOW_DIALOG = "is_show_dialog";
    public static final String SHOW_MESSAGE = "show_message";
    public static final String DOWN_LOAD_URL = "down_load_url";
    public static final String UPGRADE_DIR_NAME = "upgrade";
    public static final String MESSAGE_VIEW_MODEL = "message_view_model";
    public static final int WEEK_DAY_NUMBER = 7;
    public static final int LIMIT_TEXT_NUMBER = 200;
    public static final String RUNTIME_DB_NAME = "DingJi.db";

    /*用来标识请求照相功能的activity*/
    public static final int TAKE_PHONE = 1001;
    /*用来标识请求gallery的activity*/
    public static final int PHOTO_PICKED = 1002;
    public static final int REQUEST_CROP_IMAGE = 1003;
    public static final int GOODS_PICTURE_WIDTH = 200;
    public static final int GOODS_PICTURE_HEIGHT = 200;
    public static final int GOODS_PICTURE_SCALE_WIDTH = 1;
    public static final int GOODS_PICTURE_SCALE_HEIGHT = 1;
    public static final int REQUEST_ENABLE_BT = 3;
    public static final String EXPRESS_ORDER_UPDATE_LOCATION = "express_order_update_location";
    public static final int SEND_SMS_INTERVAL = 60;
    public static final int SMS_VERIFY_CODE_EFFECTIVE_SECOND = 5 * 60;
    public static final String PHONE_NUMBER = "phone_number";
    public static final String COMPANY_INFO = "company_info";
    public static final boolean BUILT_IN_TEST = true;
    public static final String TEST_VERIFICATION_CODE = "1234";
    public static final String GOODS = "goods";
    public static final String GOODS_ITEM_VIEW_MODEL = "goods_item_view_model";
    public static final String IS_DELETE = "IS_DELETE";
    public static final String TAB_TAG = "tab_tag";
    public static final String TAB_SHOPPING_CART = "tab_shopping_cart";
    public static final String TAB_GOODS = "tab_goods";
    public static final String TAG_SETTING = "tag_setting";
    public static final String ADDRESS = "address";
    public static final String IS_CHANGE_DEFAULT_ADDRESS = "is_change_default_address";
    public static final String IS_SELECT_ADDRESS = "is_select_address";
    public static final int QUERY_ORDER_COUNT = 20;
    public static final String ORDER_NUMBER = "order_number";
    public static String Latitude = "Latitude";
    public static String Longitude = "Longitude";
    public static final int MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;
    public static String ACTION_NEW_MESSAGE = "ACTION_NEW_MESSAGE";
    public static final String PICTURE_IMAGE_URL_SPLIT = ";";


    public static class HttpConstant {
        public static final String HEADER_CONTENT_TYPE = "Content-Type";
        public static final String VALUE_APPLICATION_JSON = "application/json";
        public static final String POST = "POST";
        public static final String SERVER_ADDRESS = "http://112.126.66.121/appserver/gateway";
        public static final int TIME_OUT = 8 * 1000;
        public static final Charset UTF_8 = Charset.forName("UTF8");
        public static final Charset DEFAULT_CHARSET = UTF_8;
        public static final String JSON_RPC_VERSION = "2.0";
        public static final String TAG = HttpClient.class.getSimpleName();
        public static final int NETWORK_CONNECT_ERROR_CODE = 500;
        public static final int TOKEN_ERROR_CODE = 400;
    }

    public static class GeTuiConstant {
        public static final String CANCEL_ORDER = "CANCEL_ORDER";
        public static final String NEW_ORDER = "NEW_ORDER";
    }
}