package com.afroci.cashapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.base.BaseActivity;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomOrderDetailDrinkTypeMaster;
import com.afroci.cashapp.dto.CustomOrderDetailResDto;
import com.afroci.cashapp.dto.TableResDto;
import com.afroci.cashapp.util.ListViewUtil;
import com.afroci.cashapp.view.CategoriMenuActivity;
import com.afroci.cashapp.view.KitchenActivity;
import com.afroci.cashapp.view.TableActivity;
import com.afroci.cashapp.view.TableSelectActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderAdapter extends ArrayAdapter<CustomOrderDetailResDto> {

    private int resourceId;
    private KitchenActivity kitchenActivity = null;

    public OrderAdapter(KitchenActivity activity, int textViewResourceId, List<CustomOrderDetailResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.kitchenActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomOrderDetailResDto order = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        LinearLayout orderTitleArea = view.findViewById(R.id.order_title_area);
        if(CodeKbn.displayKbn_HIDE.equals(order.getShowTitle())){
            orderTitleArea.setVisibility(View.GONE);
        } else {
            orderTitleArea.setVisibility(View.VISIBLE);

            TextView tableId = view.findViewById(R.id.table_id);
            tableId.setText("テーブル:(" + order.getTableId() + ")" + order.getTableCodeName());

            TextView orderId = view.findViewById(R.id.order_id);
            orderId.setText("注文番号:" + order.getOrderId());

            TextView orderCreateTime = view.findViewById(R.id.order_create_time);
            orderCreateTime.setText("注文日時:" + order.getCreateTimeStr());
        }

        TextView menuId = view.findViewById(R.id.menu_id);
        menuId.setText(order.getMenuId());

        TextView menuName = view.findViewById(R.id.menu_name);
        menuName.setText(order.getMenuName());

        TextView menuCount = view.findViewById(R.id.menu_count);
        menuCount.setText("数量:" + order.getMenuCount());

        TextView menuStatus = view.findViewById(R.id.menu_status);
        menuStatus.setText(CodeKbn.orderStatus_map.get(order.getOrderStatus()));

        View optionLine = view.findViewById(R.id.optionLine);
        ListView optionList = view.findViewById(R.id.optionList);
        List<CustomOrderDetailDrinkTypeMaster> drinkTypeCountList = order.getDrinkTypeCountList();
        // ドリンクタイプ
        if(drinkTypeCountList != null && drinkTypeCountList.size() > 0){
            optionLine.setVisibility(View.VISIBLE);
            optionList.setVisibility(View.VISIBLE);
            DrinkTypeAdapter adapter = new DrinkTypeAdapter(kitchenActivity,R.layout.drink_type_item, drinkTypeCountList);
            ListView listView = view.findViewById(R.id.optionList);
            listView.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(listView);
        }
        // ランチオプション
        else if(order.getLunchOptionList() != null && order.getLunchOptionList().size() > 0){
            optionLine.setVisibility(View.VISIBLE);
            optionList.setVisibility(View.VISIBLE);
            LunchOptionDetailAdapter adapter = new LunchOptionDetailAdapter(kitchenActivity,R.layout.lunch_option_item, order.getLunchOptionList());
            ListView listView = view.findViewById(R.id.optionList);
            listView.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(listView);
        } else {
            optionLine.setVisibility(View.GONE);
            optionList.setVisibility(View.GONE);
        }

        Button btnHaiSou = view.findViewById(R.id.btnHaiSou);
        Button btnKanryou = view.findViewById(R.id.btnKanryou);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnHaiSou.setText(CodeKbn.orderStatus_map.get(CodeKbn.orderStatus_IN_DELIVERY));
        btnKanryou.setText(CodeKbn.orderStatus_map.get(CodeKbn.orderStatus_COMPLETE));
        btnCancel.setText(CodeKbn.orderStatus_map.get(CodeKbn.orderStatus_CANCEL));

        if(CodeKbn.orderStatus_PREPARING.equals(order.getOrderStatus())){
            btnHaiSou.setVisibility(View.VISIBLE);
            btnKanryou.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);

            btnHaiSou.setOnClickListener(v -> {
                kitchenActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_IN_DELIVERY);
            });
            btnKanryou.setOnClickListener(v -> {
                kitchenActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_COMPLETE);
            });
            btnCancel.setOnClickListener(v -> {
                kitchenActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_CANCEL);
            });
        } else if(CodeKbn.orderStatus_IN_DELIVERY.equals(order.getOrderStatus())){
            btnHaiSou.setVisibility(View.GONE);
            btnKanryou.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);

            menuStatus.setTextColor(Color.parseColor("#FF0000"));

            btnKanryou.setOnClickListener(v -> {
                kitchenActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_COMPLETE);
            });
            btnCancel.setOnClickListener(v -> {
                kitchenActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_CANCEL);
            });
        } else if(CodeKbn.orderStatus_COMPLETE.equals(order.getOrderStatus())){
            btnHaiSou.setVisibility(View.GONE);
            btnKanryou.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);

            btnCancel.setOnClickListener(v -> {
                kitchenActivity.updateOrderDetailStatus(order, CodeKbn.orderStatus_CANCEL);
            });
        } else {
            btnHaiSou.setVisibility(View.GONE);
            btnKanryou.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }

        return view;
    }

}