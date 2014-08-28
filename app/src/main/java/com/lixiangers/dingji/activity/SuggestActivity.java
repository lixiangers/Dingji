package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.SuggestRequest;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class SuggestActivity extends NeolixNaviagationBaseActivity {

    private View submitSuggestButton;
    private EditText suggestEditView;

    public SuggestActivity() {
        super(R.layout.activity_suggest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.suggset));

        suggestEditView = (EditText) findViewById(R.id.et_suggest);
        submitSuggestButton = findViewById(R.id.bt_submit_suggest);

        submitSuggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suggest = getTextFrom(suggestEditView);
                if (isBlank(suggest)) {
                    showText(R.string.data_incomplete);
                    return;
                }

                submitSuggest(suggest);
            }
        });
    }

    private void submitSuggest(String suggest) {
        SuggestRequest request = new SuggestRequest(suggest);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.feedback, request);

        Type type = new TypeToken<HttpResponse<String>>() {
        }.getType();

        showRequestDialog(SuggestActivity.this, getString(R.string.is_upload));
        RequestServerAsyncTask<HttpResponse<String>> task =
                new RequestServerAsyncTask<HttpResponse<String>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<String> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            showText(getString(R.string.submit_success));
                            finish();
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }
}
