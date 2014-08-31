package com.lixiangers.dingji.manager;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.model.OrderStatus;

import java.util.List;

import static com.google.common.primitives.Ints.asList;

public class FinishOrderListActivity extends OrderListActivity {
    @Override
    protected boolean isUnFinish() {
        return false;
    }

    @Override
    protected List<Integer> getStatus() {
        return asList(OrderStatus.SUCCESS.getIndex());
    }

    @Override
    public String getTitleString() {
        return getString(R.string.finish_order);
    }
}
