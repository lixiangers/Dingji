package com.lixiangers.dingji.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lixiangers.dingji.model.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusListDialogFragment extends DialogFragment {

    private OrderStatus orderOption;
    private List<OrderStatus> companies = new ArrayList<OrderStatus>();

    private OnSelectedListener onSelectedListener = new OnSelectedListener() {
        @Override
        public void onSelected(OrderStatus orderOption) {
        }
    };

    public void setCompany(OrderStatus company) {
        this.orderOption = company;
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(new OptionAdapter(getActivity()), getIndex(), null);
        companies.clear();
        companies.add(OrderStatus.SUBMIT_ORDER);
        companies.add(OrderStatus.START_TRANSPORT);
        companies.add(OrderStatus.SUCCESS);
        return builder.create();
    }

    private int getIndex() {
        if (orderOption == null) {
            return -1;
        }

        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getIndex() == (orderOption.getIndex())) {
                return i;
            }
        }

        return 0;
    }

    class OptionAdapter extends ArrayAdapter {
        OptionAdapter(Context context) {
            super(context, 0, companies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderMoreItemView orderMoreItemView = new OrderMoreItemView(parent.getContext());

            final OrderStatus company = companies.get(position);
            orderMoreItemView.setText(company.getDes());
            orderMoreItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelectedListener.onSelected(company);
                    dismiss();
                }
            });

            return orderMoreItemView;
        }
    }

    public static interface OnSelectedListener {
        void onSelected(OrderStatus orderOption);
    }
}
