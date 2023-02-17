package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.dto.NotificationMasterSearchResDto;
import com.afroci.cashapp.view.CallMasterActivity;

import java.util.List;

public class CallMasterAdapter extends ArrayAdapter<NotificationMasterSearchResDto> {

    private int resourceId;
    private CallMasterActivity activity = null;

    public CallMasterAdapter(CallMasterActivity activity, int textViewResourceId, List<NotificationMasterSearchResDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationMasterSearchResDto call = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tableName = view.findViewById(R.id.table_name);
        tableName.setText(call.getTableId() + "(" + call.getTableCodeName() + ")");

        TextView callTime = view.findViewById(R.id.call_time);
        callTime.setText(call.getCreateTimeStr());

        TextView callType = view.findViewById(R.id.call_type);
        callType.setText(call.getCallTypeStr());

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(view1 -> activity.update(call));

        return view;
    }
}