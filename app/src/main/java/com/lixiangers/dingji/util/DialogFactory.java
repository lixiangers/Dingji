package com.lixiangers.dingji.util;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.lixiangers.dingji.R;


public class DialogFactory extends Activity {
    private static Dialog mDialog = null;
    private Dialog dialog;

    public Dialog createRequestDialog(final Context context, String tip) {

        initDialog(context);

        TextView titleText = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0) {
            titleText.setText(getString(R.string.is_processing));
        } else {
            titleText.setText(tip);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void initDialog(Context context) {
        dialog = new Dialog(context, R.style.my_dialog);
        dialog.setContentView(R.layout.dialog_layout);
    }


    public static void showRequestDialog(Context context, String content) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new DialogFactory().createRequestDialog(context, content);
        mDialog.show();
    }

    public static void showRequestDialog(Context context, int resId) {
        showRequestDialog(context, context.getString(resId));
    }

    public static void hideRequestDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}