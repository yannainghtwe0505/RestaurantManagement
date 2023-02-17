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
import com.afroci.cashapp.dto.TableResDto;
import com.afroci.cashapp.view.CategoriMenuActivity;
import com.afroci.cashapp.view.OrderHistoryActivity;
import com.afroci.cashapp.view.TableActivity;
import com.afroci.cashapp.view.TableSelectActivity;
import com.afroci.cashapp.view.TableSelectForOrderHistoryActivity;

import java.util.List;

public class TableAdapter extends ArrayAdapter<TableResDto> {

    private int resourceId;
    private TableActivity tableActivity = null;
    private TableSelectActivity tableSelectActivity = null;
    private TableSelectForOrderHistoryActivity tableSelectForOrderHistoryActivity = null;

    public TableAdapter(TableActivity activity, int textViewResourceId, List<TableResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.tableActivity = activity;
    }

    public TableAdapter(TableSelectActivity activity, int textViewResourceId, List<TableResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.tableSelectActivity = activity;
    }

    public TableAdapter(TableSelectForOrderHistoryActivity activity, int textViewResourceId, List<TableResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.tableSelectForOrderHistoryActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableResDto table = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        ImageView icon = view.findViewById(R.id.imageView);
        if(CodeKbn.tableFreeKbn_IN.equals(table.getTableFreeKbn())){
            icon.setImageResource(R.drawable.table1);
        } else {
            icon.setImageResource(R.drawable.table2);
        }

        TextView tableId = view.findViewById(R.id.table_id);
        tableId.setText(table.getTableId());

        TextView tableName = view.findViewById(R.id.table_name);
        tableName.setText(table.getTableCodeName());

        TextView tableFreeKbnStr = view.findViewById(R.id.table_free_kbn_str);
        String str = CodeKbn.tableFreeKbn_map.get(table.getTableFreeKbn());
        if(CodeKbn.existKbn_Y.equals(table.getTimeLockKbn())){
            str += "(時間制御)";
        }
        tableFreeKbnStr.setText(str);

        Button btnOrder = view.findViewById(R.id.btnOrder);
        Button btnIn = view.findViewById(R.id.btnIn);
        Button btnOut = view.findViewById(R.id.btnOut);
        Button btnPrintDetail = view.findViewById(R.id.btnPrintDetail);
        Button btnOrderHistory = view.findViewById(R.id.btnOrderHistory);

        // 座席
        if(tableActivity != null) {
            btnPrintDetail.setVisibility(View.GONE);
            btnOrderHistory.setVisibility(View.GONE);
            if(CodeKbn.tableFreeKbn_IN.equals(table.getTableFreeKbn())){
                icon.setClickable(true);
                icon.setOnClickListener(v -> {
                    getCheckinInfo(table);
                });
                btnOrder.setVisibility(View.VISIBLE);
                btnOrder.setOnClickListener(v -> showMenu(tableActivity,table));
                btnOut.setVisibility(View.VISIBLE);
                btnOut.setOnClickListener(v -> checkOut(table));

                btnIn.setVisibility(View.GONE);
            } else {
                icon.setClickable(false);
                btnIn.setVisibility(View.VISIBLE);
                btnIn.setOnClickListener(v -> checkIn(table));

                btnOrder.setVisibility(View.GONE);
                btnOut.setVisibility(View.GONE);
            }
        }

        // テーブル選択 注文
        else if(tableSelectActivity != null) {
            btnOrder.setVisibility(View.VISIBLE);
            btnOrder.setOnClickListener(v -> showMenu(tableSelectActivity,table));

            btnIn.setVisibility(View.GONE);
            btnOut.setVisibility(View.GONE);
            btnPrintDetail.setVisibility(View.GONE);
            btnOrderHistory.setVisibility(View.GONE);
        }

        // テーブル選択 注文履歴
        else if(tableSelectForOrderHistoryActivity != null) {
            btnPrintDetail.setVisibility(View.VISIBLE);
            btnPrintDetail.setOnClickListener(v -> {
                tableSelectForOrderHistoryActivity.printDetail(table);
            });

            btnOrderHistory.setVisibility(View.VISIBLE);
            btnOrderHistory.setOnClickListener(v -> showOrderHistory(table));

            btnIn.setVisibility(View.GONE);
            btnOut.setVisibility(View.GONE);
            btnOrder.setVisibility(View.GONE);
        }

        return view;
    }

    private void showOrderHistory(TableResDto table) {
        tableSelectForOrderHistoryActivity.gotoPage(OrderHistoryActivity.class, table);
    }

    private void showMenu(BaseActivity activity, TableResDto table) {
        activity.getApp().setActiveTableCheckInId(table.getActiveCheckInId());
        activity.gotoPage(CategoriMenuActivity.class);
    }

    private void checkOut(TableResDto table) {
        tableActivity.showCheckOut(table);
    }

    private void checkIn(TableResDto table) {
        tableActivity.showCheckIn(table);
    }

    private void getCheckinInfo(TableResDto table) {
        tableActivity.getCheckinInfo(table);
    }
}