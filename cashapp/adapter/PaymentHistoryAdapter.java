package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomPaymentResDto;
import com.afroci.cashapp.util.AmountUtil;
import com.afroci.cashapp.view.PaymentHistoryActivity;
import com.afroci.cashapp.view.TransactionOrderListActivity;

import java.util.List;

public class PaymentHistoryAdapter extends ArrayAdapter<CustomPaymentResDto> {

    private int resourceId;
    private PaymentHistoryActivity paymentHistoryActivity = null;

    public PaymentHistoryAdapter(PaymentHistoryActivity activity, int textViewResourceId, List<CustomPaymentResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.paymentHistoryActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomPaymentResDto payment = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tableId = view.findViewById(R.id.tableId);
        tableId.setText("テーブル:(" + payment.getTableId() + ")" + payment.getTableCodeName());

        TextView checkInId = view.findViewById(R.id.checkInId);
        checkInId.setText(payment.getCheckInId());

        TextView checkInDate = view.findViewById(R.id.checkInDate);
        checkInDate.setText(payment.getCheckInTimeStr());

        TextView checkOutDate = view.findViewById(R.id.checkOutDate);
        checkOutDate.setText(payment.getCheckOutTimeStr());

        TextView totalAmountInTax = view.findViewById(R.id.totalAmountInTax);
        totalAmountInTax.setText(AmountUtil.amountFormat(payment.getPayAmount()) + AmountUtil.IN_TAX);

        TextView payStatus = view.findViewById(R.id.payStatus);
        payStatus.setText(CodeKbn.payStatus_map.get(payment.getPayStatus()));

        Button btnPrintDetail = view.findViewById(R.id.btnPrintDetail);
        btnPrintDetail.setClickable(true);
        btnPrintDetail.setOnClickListener(v -> {
            paymentHistoryActivity.printDetail(payment);
        });

        Button btnPrintReceipt = view.findViewById(R.id.btnPrintReceipt);
        if(CodeKbn.payStatus_Y.equals(payment.getPayStatus())){
            btnPrintReceipt.setClickable(true);
            btnPrintReceipt.setOnClickListener(v -> {
                paymentHistoryActivity.printReceipt(payment);
            });
        } else {
            btnPrintReceipt.setVisibility(View.GONE);
            btnPrintReceipt.setClickable(false);
        }

        Button btnPaymentHistoryDetail = view.findViewById(R.id.btnPaymentHistoryDetail);
        btnPaymentHistoryDetail.setClickable(true);
        btnPaymentHistoryDetail.setOnClickListener(v -> {
            paymentHistoryActivity.gotoPage(TransactionOrderListActivity.class, payment);
        });

        return view;
    }

}