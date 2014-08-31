package com.lixiangers.dingji.util;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiangers.dingji.application.MyApplication;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;
import static com.lixiangers.dingji.application.MyApplication.getInstance;
import static java.lang.String.format;

public final class StringUtil {

    private static final Pattern whiteSpacePattern = Pattern.compile("\\s+");
    private static final Pattern numberPattern = Pattern.compile("[0-9]*");
    private static final Pattern bankCardNumberPattern = Pattern.compile("[0-9]{19}");
    public static final Pattern mobileNumberPattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
    public static final String EMPTY = "";
    private static Pattern identificationCardPattern = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");

    public static boolean isNotBlank(String text) {
        return text != null && !EMPTY.equalsIgnoreCase(text.trim());
    }

    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    public static boolean isNumber(String text) {
        return text != null && numberPattern.matcher(text).matches();
    }

    public static String removeAllWhiteSpace(String text) {
        return text == null ? null : whiteSpacePattern.matcher(text).replaceAll(EMPTY);
    }

    public static String formatTimeToHHMMString(Date time) {
        return time == null ? null : new SimpleDateFormat("HH:mm").format(time);
    }

    public static String formatTime(Date time) {
        return time == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static String formatTimeWithoutSecond(Date time) {
        return time == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);
    }

    public static String getYMdHmsOfDateTime(DateTime referenceTime) {
        return referenceTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static boolean isEqual(String text, String target) {
        return !isBlank(text) && text.equals(target);
    }

    public static String formatCashierId(String cashId) {
        int id = Integer.parseInt(cashId);
        return String.format("%04d", id);
    }

    public static String formatOrderCreateTime(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(time);
    }

    public static String getHmsOfDate(Date date) {
        return new DateTime(date).toString("HH:mm:ss");
    }

    public static String formatTimeToString(Date time) {
        return time == null ? null : new SimpleDateFormat("yyyyMMddHHmmss").format(time);
    }

    public static String formatPriceWithOutUnit(Double amount) {
        DecimalFormat format = new DecimalFormat("#0.00");
        return format.format(amount);
    }

    public static boolean isMobileNumber(String mobiles) {
        if (isBlank(mobiles))
            return false;

        Matcher m = mobileNumberPattern.matcher(mobiles);
        return m.matches();
    }

    public static boolean isBankCardNumber(String text) {
        return isNotBlank(text) && bankCardNumberPattern.matcher(text).matches();
    }

    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] getHexStringByteArray(String hexString) {
        return new BigInteger(hexString, 16).toByteArray();
    }

    public static boolean isCorrectIDCardNo(String idCardNo) {
        return identificationCardPattern.matcher(idCardNo).matches();
    }

    public static float formatYuanFromFen(int fen) {
        BigDecimal b = new BigDecimal(fen / 100f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static String generateRandomCode(int num) {
        int[] numbers = new int[num];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 10);
        }
        return numbers[0] + numbers[1] + numbers[2] + numbers[3] + numbers[4] + numbers[5] + "";
    }

    public static void showText(String text) {
        Toast.makeText(getInstance(), text, LENGTH_SHORT).show();
    }

    public static void showText(int text) {
        Toast.makeText(getInstance(), text, LENGTH_SHORT).show();
    }


    public static String getGoodsImagePath() {
        return MyApplication.getInstance().getExternalFilesDir("image").getAbsolutePath();
    }

    public static String getGoodsImageName() {
        return "file://" + getGoodsImagePath() + "/" + formatTimeToString(DateTime.now().toDate()) + ".jpg";
    }

    public static String formatTemplateString(int templateId, Object... params) {
        String template = getInstance().getResources().getText(templateId).toString();
        return format(template, params);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String formatTimeToMMDDString(Date time) {
        return time == null ? null : new SimpleDateFormat("M月d日").format(time);
    }

    public static String getTextFrom(TextView textView) {
        return textView.getText().toString();
    }
}