package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.base.BaseActivity;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomPaymentResDto;
import com.afroci.cashapp.dto.TableResDto;
import com.afroci.cashapp.util.AmountUtil;
import com.afroci.cashapp.view.CategoriMenuActivity;
import com.afroci.cashapp.view.PaymentConfirmActivity;
import com.afroci.cashapp.view.PaymentSearchActivity;
import com.afroci.cashapp.view.TableActivity;
import com.afroci.cashapp.view.TableSelectActivity;

import java.util.List;

public class PaymentAdapter extends ArrayAdapter<CustomPaymentResDto> {

    private int resourceId;
    private PaymentSearchActivity paymentSearchActivity = null;

    public PaymentAdapter(PaymentSearchActivity activity, int textViewResourceId, List<CustomPaymentResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.paymentSearchActivity = activity;
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
        totalAmountInTax.setText(AmountUtil.amountFormat(payment.getTotalAmountInTax()) + AmountUtil.IN_TAX);

        Button toPay = view.findViewById(R.id.btnToPay);
        toPay.setOnClickListener(v -> {
            paymentSearchActivity.gotoPage(PaymentConfirmActivity.class, payment);
        });

        return view;
    }

}