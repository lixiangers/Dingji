package com.lixiangers.dingji.manager;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.model.OrderStatus;

import java.util.List;

import static com.google.common.primitives.Ints.asList;

public class UnFinishOrderListActivity extends OrderListActivity {
    @Override
    protected boolean isUnFinish() {
        return true;
    }

    @Override
    protected List<Integer> getStatus() {
        return asList(OrderStatus.SUBMIT_ORDER.getIndex(), OrderStatus.START_TRANSPORT.getIndex());
    }

    @Override
    public String getTitleString() {
        return getString(R.string.unfinsih_order);
    }
}
