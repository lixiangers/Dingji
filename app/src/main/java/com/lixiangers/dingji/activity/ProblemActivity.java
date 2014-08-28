package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.lixiangers.dingji.R;

public class ProblemActivity extends NeolixNaviagationBaseActivity {

    private ExpandableListView problemListView;

    public ProblemActivity() {
        super(R.layout.activity_problem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        problemListView = (ExpandableListView) findViewById(R.id.elv_problem);
    }
}
