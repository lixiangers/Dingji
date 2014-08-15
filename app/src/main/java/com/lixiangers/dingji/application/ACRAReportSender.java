package com.lixiangers.dingji.application;

import android.content.Context;
import android.os.Build;
import android.util.Log;


import com.lixiangers.dingji.DeviceUtil;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.StringUtil;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;


public class ACRAReportSender implements ReportSender {

    private static final String TAG = "ACRAReportSender";

    private Context context = null;

    public ACRAReportSender(Context context) {
        this.context = context;
    }

    @Override
    public void send(CrashReportData errorContent) throws ReportSenderException {
        sendMailReport(errorContent);
    }

    private void sendMailReport(CrashReportData errorContent) throws ReportSenderException {
        sendMailByJavaMail(errorContent);
    }

    public int sendMailByJavaMail(CrashReportData errorContent) {
        String fromMailAddress = "lixiang1214@163.com";
        MailUtil mail = new MailUtil(fromMailAddress, "LG955100", "smtp.163.com");
        mail.set_debuggable(false);
        String[] toArr = {ACRA.getConfig().mailTo()};
        mail.set_to(toArr);
        mail.set_from(fromMailAddress);
        mail.set_subject(StringUtil.formatTemplateString(R.string.acra_mail_subject, context.getString(R.string.app_name)));

        String emailBody = getBody(errorContent);
        mail.setBody(emailBody);

        try {
            boolean result = mail.send();
        } catch (Exception e) {
            Log.e("MailApp", "Could not send email", e);
        }
        return 1;
    }

    private String getBody(CrashReportData errorContent) {
        StringBuilder sb = new StringBuilder();
        sb.append(errorContent.getProperty(ReportField.APP_VERSION_CODE));
        sb.append(errorContent.getProperty(ReportField.APP_VERSION_NAME));
        sb.append(errorContent.getProperty(ReportField.PHONE_MODEL));
        sb.append(errorContent.getProperty(ReportField.DISPLAY));
        sb.append(errorContent.getProperty(ReportField.SHARED_PREFERENCES));
        sb.append(errorContent.getProperty(ReportField.CUSTOM_DATA));
        sb.append(errorContent.getProperty(ReportField.STACK_TRACE));
        sb.append(errorContent.getProperty(ReportField.LOGCAT));
        sb.append("DevieID:").append(DeviceUtil.getDeviceId(MyApplication.getInstance())).append("\r\n");
        sb.append("VersionName:").append(DeviceUtil.getPackageVersionName(MyApplication.getInstance())).append("\r\n");
        sb.append("MODEL:").append(Build.MODEL).append("\r\n");
        sb.append("Product MANUFACTURER: ").append(Build.MANUFACTURER).append("\r\n");
        sb.append("SDK:").append(Build.VERSION.SDK).append("\r\n");
        sb.append("RELEASE:").append(Build.VERSION.RELEASE).append("\r\n");
        sb.append("RELEASE:").append(Build.VERSION.RELEASE).append("\r\n");
        sb.append("UserPhone:").append("").append("\r\n");
        return sb.toString();
    }
}
