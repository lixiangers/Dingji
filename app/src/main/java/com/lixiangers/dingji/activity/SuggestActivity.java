package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lixiangers.dingji.R;

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
        //TODO submit suggest
    }
}
