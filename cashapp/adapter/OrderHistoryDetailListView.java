package com.afroci.cashapp.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class OrderHistoryDetailListView extends ListView {

    public OrderHistoryDetailListView(Context context) {
        super(context);
    }

    public OrderHistoryDetailListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderHistoryDetailListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OrderHistoryDetailListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int customHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, customHeight);
    }
}