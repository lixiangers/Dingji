package com.lixiangers.dingji.protocol.http;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.util.Constant;

import static com.lixiangers.dingji.application.MyApplication.getInstance;

public class HttpResponseResult {
    private ResponseResultType resultType;
    private Exception responseException;
    private String responseResult;

    public ResponseResultType getResultType() {
        return resultType;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public HttpResponseResult(ResponseResultType resultType, String responseResult) {
        this(resultType, responseResult, null);
    }

    public HttpResponseResult(ResponseResultType resultType, String responseResult, Exception responseException) {
        this.resultType = resultType;
        this.responseException = responseException;
        this.responseResult = responseResult;
    }

    public boolean isSuccess() {
        return ResponseResultType.SUCCEEDED == resultType;
    }

    public HttpResponse<Object> getHttpResponse() {
        if (resultType == HttpResponseResult.ResponseResultType.FAILED) {
            return createHttpResponse(String.valueOf(Constant.HttpConstant.NETWORK_CONNECT_ERROR_CODE), getInstance().getString(R.string.fail_connect_server_text));
        } else if (resultType == HttpResponseResult.ResponseResultType.TOKEN_ERROR) {
            return createHttpResponse(String.valueOf(Constant.HttpConstant.TOKEN_ERROR_CODE), getInstance().getString(R.string.fail_connect_server_text));
        } else if (resultType == HttpResponseResult.ResponseResultType.TOKEN_OVERDUE) {
            return createHttpResponse(String.valueOf(Constant.HttpConstant.TOKEN_ERROR_CODE), getInstance().getString(R.string.fail_connect_server_text));
        }

        return null;
    }

    private HttpResponse<Object> createHttpResponse(String networkConnectErrorCode, String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(networkConnectErrorCode);
        errorMessage.setMessage(message);

        HttpResponse<Object> httpResponse = new HttpResponse<Object>();
        httpResponse.setError(errorMessage);
        return httpResponse;
    }

    public enum ResponseResultType {
        SUCCEEDED,
        FAILED,
        TOKEN_OVERDUE,
        TOKEN_ERROR
    }
}