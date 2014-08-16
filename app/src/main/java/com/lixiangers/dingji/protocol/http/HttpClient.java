package com.lixiangers.dingji.protocol.http;

import android.os.Build;
import android.util.Log;

import com.google.common.io.CharStreams;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.lixiangers.dingji.util.IOUtil.disconnectQuietly;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_OK;

public class HttpClient {
    private Map<String, String> propertiesMap;

    public HttpClient() {
        propertiesMap = new HashMap<String, String>();
    }

    public HttpResponseResult sendRequestGetResponse(String request, String token) {


        HttpURLConnection connection = null;
        try {
            String url = Constant.HttpConstant.SERVER_ADDRESS;
            if (StringUtil.isNotBlank(token)) {
                url += ("?token=" + token);
            }
            connection = initConnection(url);
            Log.d(Constant.HttpConstant.TAG, format("path -> %s, request -> %s", url, request));

            send(request, connection);

            return receive(connection);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponseResult(HttpResponseResult.ResponseResultType.FAILED, e.getMessage());
        } finally {
            disconnectQuietly(connection);
        }
    }

    private HttpResponseResult receive(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        Log.d(Constant.HttpConstant.TAG, format("code -> %s", responseCode));

        if (responseCode == HTTP_OK) {
            return getHttpResponseResult(connection.getInputStream(), HttpResponseResult.ResponseResultType.SUCCEEDED);
        } else if (responseCode == Constant.TOKEN_ERROR_CODE) {
            String errorString = CharStreams.toString(new InputStreamReader(connection.getErrorStream(), Constant.HttpConstant.DEFAULT_CHARSET));
            if (errorString.equals("Token Expired")) {
                return new HttpResponseResult(HttpResponseResult.ResponseResultType.TOKEN_OVERDUE, String.valueOf(responseCode));
            } else if (errorString.equals("Token Error"))
                return new HttpResponseResult(HttpResponseResult.ResponseResultType.TOKEN_ERROR, String.valueOf(responseCode));
        }

        String errorString = CharStreams.toString(new InputStreamReader(connection.getErrorStream(), Constant.HttpConstant.DEFAULT_CHARSET));
        Log.d(Constant.HttpConstant.TAG, "error String:" + errorString);
        return new HttpResponseResult(HttpResponseResult.ResponseResultType.FAILED, String.valueOf(responseCode));
    }

    private HttpResponseResult getHttpResponseResult(InputStream inputStream, HttpResponseResult.ResponseResultType succeeded) throws IOException {
        String response = CharStreams.toString(new InputStreamReader(inputStream, Constant.HttpConstant.DEFAULT_CHARSET));
        Log.d(Constant.HttpConstant.TAG, format("response -> %s", response));
        return new HttpResponseResult(succeeded, response);
    }

    private void send(String request, HttpURLConnection connection) throws IOException {
        byte[] bytes = request.getBytes(Constant.HttpConstant.DEFAULT_CHARSET);
        connection.setFixedLengthStreamingMode(bytes.length);
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private HttpURLConnection initConnection(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(Constant.HttpConstant.POST);
        setRequestProperties(connection);
        return connection;
    }

    private void setRequestProperties(HttpURLConnection connection) {
        connection.setRequestProperty(Constant.HttpConstant.HEADER_CONTENT_TYPE, Constant.HttpConstant.VALUE_APPLICATION_JSON);
        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(Constant.HttpConstant.TIME_OUT);
        connection.setReadTimeout(Constant.HttpConstant.TIME_OUT);
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Charsert", "UTF-8");

        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            //HACK: fixed http://stackoverflow.com/questions/15411213/android-httpsurlconnection-eofexception
            connection.setRequestProperty("Connection", "close");
        }

        for (String key : propertiesMap.keySet()) {
            connection.setRequestProperty(key, propertiesMap.get(key));
        }
    }
}