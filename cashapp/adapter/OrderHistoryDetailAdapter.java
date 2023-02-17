package com.afroci.cashapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomOrderDetailDrinkTypeMaster;
import com.afroci.cashapp.dto.CustomOrderDetailResDto;
import com.afroci.cashapp.util.ListViewUtil;
import com.afroci.cashapp.view.OrderHistoryDetailActivity;

import java.util.List;

public class OrderHistoryDetailAdapter extends ArrayAdapter<CustomOrderDetailResDto> {

    private int resourceId;
    private OrderHistoryDetailActivity orderDetailActivity = null;
    private String checkInStatus;

    public OrderHistoryDetailAdapter(OrderHistoryDetailActivity activity, int textViewResourceId, List<CustomOrderDetailResDto> objects, String checkInStatus) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.orderDetailActivity = activity;
        this.checkInStatus = checkInStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomOrderDetailResDto order = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView menuId = view.findViewById(R.id.menu_id);
        menuId.setText(order.getMenuId());

        TextView menuName = view.findViewById(R.id.menu_name);
        menuName.setText(order.getMenuName());

        TextView menuCount = view.findViewById(R.id.menu_count);
        menuCount.setText("数量:" + order.getMenuCount());

        TextView menuStatus = view.findViewById(R.id.menu_status);
        menuStatus.setText(CodeKbn.orderStatus_map.get(order.getOrderStatus()));

        Button btnHaiSou = view.findViewById(R.id.btnHaiSou);
        Button btnKanryou = view.findViewById(R.id.btnKanryou);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnHaiSou.setText(CodeKbn.orderStatus_map.get(CodeKbn.orderStatus_IN_DELIVERY));
        btnKanryou.setText(CodeKbn.orderStatus_map.get(CodeKbn.orderStatus_COMPLETE));
        btnCancel.setText(CodeKbn.orderStatus_map.get(CodeKbn.orderStatus_CANCEL));

        if(CodeKbn.checkInStatus_IN.equals(checkInStatus)) {
            if(CodeKbn.orderStatus_PREPARING.equals(order.getOrderStatus())){
                btnHaiSou.setVisibility(View.VISIBLE);
                btnKanryou.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);

                btnHaiSou.setOnClickListener(v -> {
                    orderDetailActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_IN_DELIVERY);
                });
                btnKanryou.setOnClickListener(v -> {
                    orderDetailActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_COMPLETE);
                });
                btnCancel.setOnClickListener(v -> {
                    orderDetailActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_CANCEL);
                });
            } else if(CodeKbn.orderStatus_IN_DELIVERY.equals(order.getOrderStatus())){
                btnHaiSou.setVisibility(View.GONE);
                btnKanryou.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);

                menuStatus.setTextColor(Color.parseColor("#FF0000"));

                btnKanryou.setOnClickListener(v -> {
                    orderDetailActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_COMPLETE);
                });
                btnCancel.setOnClickListener(v -> {
                    orderDetailActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_CANCEL);
                });
            } else if(CodeKbn.orderStatus_COMPLETE.equals(order.getOrderStatus())){
                btnHaiSou.setVisibility(View.GONE);
                btnKanryou.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);

                btnCancel.setOnClickListener(v -> {
                    orderDetailActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_CANCEL);
                });
            } else {
                btnHaiSou.setVisibility(View.GONE);
                btnKanryou.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
            }
        } else {
            btnHaiSou.setVisibility(View.GONE);
            btnKanryou.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }

        LinearLayout btnArea = view.findViewById(R.id.btnArea);
        if(CodeKbn.checkInStatus_IN.equals(checkInStatus) && !CodeKbn.orderStatus_CANCEL.equals(order.getOrderStatus())) {
            btnArea.setVisibility(View.VISIBLE);
        } else {
            btnArea.setVisibility(View.GONE);
        }

        View optionLine = view.findViewById(R.id.optionLine);
        ListView optionList = view.findViewById(R.id.optionList);
        List<CustomOrderDetailDrinkTypeMaster> drinkTypeCountList = order.getDrinkTypeCountList();
        if(drinkTypeCountList != null && drinkTypeCountList.size() > 0){
            optionLine.setVisibility(View.VISIBLE);
            optionList.setVisibility(View.VISIBLE);
            DrinkTypeAdapter adapter = new DrinkTypeAdapter(orderDetailActivity,R.layout.drink_type_item, drinkTypeCountList);
            ListView listView = view.findViewById(R.id.optionList);
            listView.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(listView);
        }
        // ランチオプション
        else if(order.getLunchOptionList() != null && order.getLunchOptionList().size() > 0){
            optionLine.setVisibility(View.VISIBLE);
            optionList.setVisibility(View.VISIBLE);
            LunchOptionDetailAdapter adapter = new LunchOptionDetailAdapter(orderDetailActivity,R.layout.lunch_option_item, order.getLunchOptionList());
            ListView listView = view.findViewById(R.id.optionList);
            listView.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(listView);
        }
        else {
            optionLine.setVisibility(View.GONE);
            optionList.setVisibility(View.GONE);
        }

        return view;
    }

}