package com.lixiangers.dingji.application;

import android.app.Application;
import android.content.pm.PackageManager;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.util.StringUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.io.File;

import static android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE;

@ReportsCrashes(formKey = "", // will not be used
        mailTo = "neolix@163.com",
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.DISPLAY,
                ReportField.SHARED_PREFERENCES,
                ReportField.CUSTOM_DATA,
                ReportField.STACK_TRACE,
                ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        sendReportsInDevMode = true,
        resToastText = R.string.crash_text)
public class MyApplication extends Application {
    private static MyApplication application;
    private ImageLoaderConfiguration config;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        application = this;
        ACRA.init(this);
        if (!couldDebug()) {
            ACRA.getErrorReporter().setReportSender(new ACRAReportSender(this));
        }

        String sDir = StringUtil.getGoodsImagePath();
        File desDir = new File(sDir);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }

        config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        super.onCreate();
    }

    private boolean couldDebug() {
        PackageManager pm = getPackageManager();
        try {
            int flags = pm.getApplicationInfo(getPackageName(), 0).flags;
            return ((flags & FLAG_DEBUGGABLE) > 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void initImageLoader() {
        ImageLoader.getInstance().init(config);
    }

    public void loginApp() {
        initImageLoader();
    }

    public void logoutApp() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearMemoryCache();
        imageLoader.destroy();
    }

    public ImageLoader getImageLoader() {
        if (!ImageLoader.getInstance().isInited())
            ImageLoader.getInstance().init(config);

        return ImageLoader.getInstance();
    }
}
