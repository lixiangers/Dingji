package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ProblemExpandeAdapter;
import com.lixiangers.dingji.model.Problem;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;

import java.lang.reflect.Type;
import java.util.List;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class ProblemActivity extends NeolixNaviagationBaseActivity {

    private ExpandableListView problemListView;
    private ProblemExpandeAdapter adapter;

    public ProblemActivity() {
        super(R.layout.activity_problem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        problemListView = (ExpandableListView) findViewById(R.id.elv_problem);
        setTitle(R.string.problem);

        adapter = new ProblemExpandeAdapter(getApplication());
        problemListView.setAdapter(adapter);
        getProblem();
    }

    private void getProblem() {
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.faq_json, null);

        Type type = new TypeToken<HttpResponse<List<Problem>>>() {
        }.getType();

        showRequestDialog(ProblemActivity.this, getString(R.string.is_get_problem));
        RequestServerAsyncTask<HttpResponse<List<Problem>>> task =
                new RequestServerAsyncTask<HttpResponse<List<Problem>>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<List<Problem>> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            List<Problem> responseParams = httpResponse.getResponseParams();
                            if (responseParams != null)
                                adapter.setData(responseParams);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }
}
