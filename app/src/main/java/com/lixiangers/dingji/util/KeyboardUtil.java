package com.lixiangers.dingji.util;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import com.lixiangers.dingji.application.MyApplication;

public class KeyboardUtil {
    public static void hideKeyboard(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }
}
