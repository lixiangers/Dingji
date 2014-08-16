package com.lixiangers.dingji.protocol.http;

import android.os.AsyncTask;
import android.util.Log;


import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.util.JsonConverter;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.util.SharedPreferencesUtil.getToken;


public abstract class RequestServerAsyncTask<T> {
    private final Type type;
    private RequestTask task;
    private HttpClient client;
    private Class<T> classtype;
    private boolean needToken;

    protected RequestServerAsyncTask(Type type) {
        this.type = type;
    }

    public abstract void OnResponse(T httpResponse);

    public void sendRequest(HttpRequest resp, boolean needToken) {
        this.needToken = needToken;
        client = new HttpClient();
        abort();
        task = new RequestTask();
        task.execute(resp);

    }

    public void sendRequest(HttpRequest resp) {
        sendRequest(resp, true);
    }

    public void stop() {
        if (task != null)
            task.cancel(true);
    }

    public class RequestTask extends AsyncTask<HttpRequest, Void, T> {

        @Override
        protected T doInBackground(HttpRequest... iHttpResps) {
            String requestJsonStr = JsonConverter.convertObjectToJson(iHttpResps[0]);
            Log.i(Constant.HttpConstant.TAG, "requestJsonStr :" + requestJsonStr);
            HttpResponseResult httpResponseResult = client.sendRequestGetResponse(requestJsonStr, needToken ? getToken() : "");
            if (httpResponseResult.isSuccess()) {
                return (T) JsonConverter.convertJsonToObject(httpResponseResult.getResponseResult(), type);
            } else {
                return (T) httpResponseResult.getHttpResponse();
            }
        }

        @Override
        protected void onPostExecute(T httpResponse) {
            super.onPostExecute(httpResponse);
            OnResponse(httpResponse);
        }
    }

    private void abort() {
        if (task == null)
            return;

        task.cancel(true);
        task = null;
    }
}
