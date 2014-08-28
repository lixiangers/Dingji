package com.lixiangers.dingji.util;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.lixiangers.dingji.application.MyApplication.getInstance;

public class SharedPreferencesUtil {

    private static final String SHARED_PREF_NAME = "user_info";
    public static final String TOKEN = "token";
    public static final String COMPANY = "company";
    public static final String USER_NAME = "user_name";
    public static final String WORK_STATUS = "work_status";
    public static final String USER_PHONE = "user_phone";
    public static final String IS_SHOW_COUNTDOWN = "is_show_countdown";
    public static final String USER_ID = "user_id";
    public static final String PASSWORD = "password";
    public static final String HAS_BEEN_VERIFIED = "has_been_verified";

    public static void saveToken(String token) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(TOKEN, token);
        edit.commit();
    }

    public static String getToken() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(TOKEN, "");
    }

    public static void saveFisrtAppinfo(boolean isFirst) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean("first_app", isFirst);
        edit.commit();
    }

    public static boolean isFirstLoginApp() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getBoolean("first_app", true);
    }


    public static void saveUserPhone(String phone) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(USER_PHONE, phone);
        edit.commit();
    }

    public static String getUserPhone() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(USER_PHONE, "");
    }

    public static void savePwd(String password) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(PASSWORD, password);
        edit.commit();
    }

    public static String getPwd() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(PASSWORD, "");
    }
}