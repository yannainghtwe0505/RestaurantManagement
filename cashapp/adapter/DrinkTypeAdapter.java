package com.afroci.cashapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.base.BaseActivity;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomOrderDetailDrinkTypeMaster;
import com.afroci.cashapp.dto.CustomOrderDetailResDto;
import com.afroci.cashapp.view.KitchenActivity;

import java.util.List;

public class DrinkTypeAdapter extends ArrayAdapter<CustomOrderDetailDrinkTypeMaster> {

    private int resourceId;
    private BaseActivity myActivity = null;

    public DrinkTypeAdapter(BaseActivity activity, int textViewResourceId, List<CustomOrderDetailDrinkTypeMaster> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.myActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomOrderDetailDrinkTypeMaster type = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView typeName = view.findViewById(R.id.typeName);
        typeName.setText(type.getDrinkTypeName());

        TextView typeCount = view.findViewById(R.id.typeCount);
        typeCount.setText("x " + type.getMenuCount());

        return view;
    }

}