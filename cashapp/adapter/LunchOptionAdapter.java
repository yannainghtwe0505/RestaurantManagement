package com.afroci.cashapp.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomCurryOptionMasterDto;
import com.afroci.cashapp.dto.CustomLunchOptionMasterDto;
import com.afroci.cashapp.dto.CustomMenuMasterSubDto;
import com.afroci.cashapp.dto.LunchReqDto;
import com.afroci.cashapp.view.OrderConfirmActivity;

import java.util.List;

public class LunchOptionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private OrderConfirmActivity menuActivity = null;
    private CustomMenuMasterSubDto menu;
    private List<LunchReqDto> lunchOptionList;

    public LunchOptionAdapter(OrderConfirmActivity activity, CustomMenuMasterSubDto menu, List<LunchReqDto> lunchOptionList) {
        this.menuActivity = activity;
        this.menu = menu;
        this.lunchOptionList = lunchOptionList;
    }

    @Override
    public int getCount() {
        return lunchOptionList.size();
    }

    @Override
    public Object getItem(int i) {
        return lunchOptionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LunchReqDto lunch = (LunchReqDto) getItem(position);
        view = View.inflate(menuActivity, R.layout.lunch_option_item,null);

        // ご飯
        CustomLunchOptionMasterDto lunchOptionData = menuActivity.getLunchData(lunch.getLunchOptionId());
        if(lunchOptionData != null) {
            TextView gohan_option_name = view.findViewById(R.id.gohan_option_name);
            gohan_option_name.setText(lunchOptionData.getName());
        }

        // カレー
        TextView curry_option_name = view.findViewById(R.id.curry_option_name);
        if(CodeKbn.comKbn_YES.equals(menu.getCurryKbn()) && !TextUtils.isEmpty(lunch.getCurryOptionId())){
            CustomCurryOptionMasterDto curryOptionData = menuActivity.getCurryData(lunch.getCurryOptionId());
            if(curryOptionData != null){
                curry_option_name.setText(curryOptionData.getName());
            }
        }else{
            curry_option_name.setVisibility(View.INVISIBLE);
        }

        // 数量
        TextView option_count = view.findViewById(R.id.option_count);
        option_count.setText(String.valueOf(1));

        // 削除
        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view1 -> {
            menuActivity.deleteLunch(menu, position);
        });

        // トッピング
        if(lunch.getToppingIdList().size() > 0){
            TextView topping_list = view.findViewById(R.id.topping_list);
            topping_list.setText(menuActivity.getToppingText(lunch.getToppingIdList()));
            topping_list.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
