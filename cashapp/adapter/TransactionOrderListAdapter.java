package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomOrderResDto;
import com.afroci.cashapp.util.AmountUtil;
import com.afroci.cashapp.view.OrderHistoryActivity;
import com.afroci.cashapp.view.OrderHistoryDetailActivity;
import com.afroci.cashapp.view.TransactionOrderListActivity;

import java.util.List;

public class TransactionOrderListAdapter extends ArrayAdapter<CustomOrderResDto> {

    private int resourceId;
    private TransactionOrderListActivity transactionOrderListActivity = null;

    public TransactionOrderListAdapter(TransactionOrderListActivity activity, int textViewResourceId, List<CustomOrderResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.transactionOrderListActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomOrderResDto order = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tableId = view.findViewById(R.id.table_id);
        tableId.setText("テーブル:(" + order.getTableId() + ")" + order.getTableCodeName());

        TextView orderId = view.findViewById(R.id.order_id);
        orderId.setText(order.getOrderId());

        TextView orderCreateTime = view.findViewById(R.id.order_create_time);
        orderCreateTime.setText(order.getCreateTimeStr());

        TextView amountInTax = view.findViewById(R.id.amount_in_tax);
        amountInTax.setText(AmountUtil.amountFormat(order.getAmountInTax()) + AmountUtil.IN_TAX);

        TextView orderStatus = view.findViewById(R.id.order_status);
        orderStatus.setText(CodeKbn.orderStatus_map.get(order.getOrderStatus()));

        Button btnOrderDetail = view.findViewById(R.id.btnOrderDetail);
        btnOrderDetail.setOnClickListener(v -> {
            transactionOrderListActivity.getApp().setActiveOrderId(order.getOrderId());
            transactionOrderListActivity.gotoPage(OrderHistoryDetailActivity.class);
        });

        return view;
    }
}