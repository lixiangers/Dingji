package com.lixiangers.dingji;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.lixiangers.dingji.application.MyApplication;

import org.joda.time.DateTime;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.media.AudioManager.STREAM_MUSIC;
import static java.lang.String.format;
import static org.apache.http.conn.util.InetAddressUtils.isIPv4Address;

public class DeviceUtil {

    private static final String TAG = DeviceUtil.class.getSimpleName();

    public static String getIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.isLoopbackAddress()) continue;
                    if (!isIPv4Address(inetAddress.getHostAddress())) continue;
                    return inetAddress.getHostAddress();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Log.d(TAG, format("device id -> %s", telephonyManager.getDeviceId()));
        return telephonyManager.getDeviceId();
    }

    public static boolean networkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetInfo != null && activeNetInfo.isConnected();
    }

    public static boolean isSimReady(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    private static String appVersion(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getPackageVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String versionName = info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getPackageVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void sendKey(final int KeyCode) {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    Log.e("Exception when sendKeyDownUpSync", e.toString());
                }
            }
        }.start();
    }

    private static void playSound(int resourceID) {
        MyApplication applicationContext = MyApplication.getInstance();
        adjustVolume(applicationContext);
        SoundPool soundPool;
        soundPool = new SoundPool(5, STREAM_MUSIC, 5);
        soundPool.load(applicationContext, resourceID, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(1, 1, 1, 0, 0, 1);
                Log.d("playSound", DateTime.now().toString());
            }
        });
    }

    private static void adjustVolume(MyApplication applicationContext) {
        AudioManager am = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume - 2, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
    }

    public static void call(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void sendSMS(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}