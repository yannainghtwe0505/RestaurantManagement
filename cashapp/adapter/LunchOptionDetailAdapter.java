package com.afroci.cashapp.adapter;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.base.BaseActivity;
import com.afroci.cashapp.dto.LunchResDto;
import com.afroci.cashapp.dto.ToppingResDto;

import java.util.List;

public class LunchOptionDetailAdapter extends ArrayAdapter<LunchResDto> {

    private int resourceId;

    public LunchOptionDetailAdapter(BaseActivity activity, int textViewResourceId, List<LunchResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LunchResDto lunch = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        // ご飯
        TextView gohan_option_name = view.findViewById(R.id.gohan_option_name);
        gohan_option_name.setText(lunch.getLunchOptionName());

        // カレー
        TextView curry_option_name = view.findViewById(R.id.curry_option_name);
        if(!TextUtils.isEmpty(lunch.getCurryOptionId())){
            curry_option_name.setText(lunch.getCurryOptionName());
        }else{
            curry_option_name.setVisibility(View.INVISIBLE);
        }

        // 数量
        TextView option_count = view.findViewById(R.id.option_count);
        option_count.setText("x 1");

        // 削除
        LinearLayout btnDeleteArea = view.findViewById(R.id.btnDeleteArea);
        btnDeleteArea.setVisibility(View.GONE);

        // トッピング
        if(lunch.getToppingList().size() > 0){
            TextView topping_list = view.findViewById(R.id.topping_list);
            topping_list.setText(getToppingText(lunch.getToppingList()));
            topping_list.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private String getToppingText(List<ToppingResDto> toppingIdList) {
        StringBuilder sb = new StringBuilder("");
        for(ToppingResDto toppingResDto : toppingIdList){
            sb.append(toppingResDto.getToppingMenuName()).append("  x 1、\n");
        }
        return sb.toString();
    }
}