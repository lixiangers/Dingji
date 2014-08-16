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

    public static void saveUserName(String name) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(USER_NAME, name);
        edit.commit();
    }

    public static String getUserName() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(USER_NAME, "");
    }

    public static String getCompany() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(COMPANY, "SF");
    }

    public static void saveIsWork(boolean isWork) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean(WORK_STATUS, isWork);
        edit.commit();
    }

    public static boolean isWorking() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getBoolean(WORK_STATUS, false);
    }

    public static void saveHeadImagePath(String path) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString("head_image_path", path);
        edit.commit();
    }

    public static String getHeadImagePath() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString("head_image_path", "");
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

    public static void saveIsShowCountdown(boolean isShowCountdown) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean(IS_SHOW_COUNTDOWN, isShowCountdown);
        edit.commit();
    }

    public static boolean isShowCountdown() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getBoolean(IS_SHOW_COUNTDOWN, true);
    }

    public static void saveUserId(String userId) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(USER_ID, userId);
        edit.commit();
    }

    public static String getUserId() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(USER_ID, "");
    }

    public static void savePassword(String password) {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString(PASSWORD, password);
        edit.commit();
    }

    public static String getPassword() {
        SharedPreferences preference = getInstance().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return preference.getString(PASSWORD, "");
    }
}