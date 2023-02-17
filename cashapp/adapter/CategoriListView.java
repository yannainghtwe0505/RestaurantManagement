package com.afroci.cashapp.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomMenuCategoryMasterSubDto;
import com.afroci.cashapp.view.CategoriMenuActivity;

import java.util.List;

public class CategoriListView extends ListView {

    public CategoriListView(Context context) {
        super(context);
    }

    public CategoriListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoriListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CategoriListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int customHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, customHeight);
    }
}