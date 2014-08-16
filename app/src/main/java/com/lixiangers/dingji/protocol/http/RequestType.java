package com.lixiangers.dingji.protocol.http;

public enum RequestType {
    postman_login,                  //登录请求
    postman_getorder, postman_accept, postman_post, get_sms_verify, check_sms_verify, reset_passwd, register_get_sms_verify, postman_register, bind_to_wechat, change_passwd, postman_feedback, bind_to_alipay, postman_at_work, postman_logout, postman_band_push, postman_auth, expresscop_list, upload_img, check_update, chat_message,               //拉取订单
}
