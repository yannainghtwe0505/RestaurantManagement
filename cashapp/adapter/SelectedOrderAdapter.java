package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomCourseMasterSubDto;
import com.afroci.cashapp.dto.CustomMenuMasterSubDto;
import com.afroci.cashapp.util.AmountUtil;
import com.afroci.cashapp.view.OrderConfirmActivity;

import java.util.List;

public class SelectedOrderAdapter extends ArrayAdapter<String[]> {

    private int resourceId;
    private OrderConfirmActivity orderConfirmActivity = null;

    public SelectedOrderAdapter(OrderConfirmActivity activity, int textViewResourceId, List<String[]> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.orderConfirmActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] selectedId = getItem(position);
        String selectedMenuId = selectedId[0];
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView menuId = view.findViewById(R.id.menu_id);
        TextView menuName = view.findViewById(R.id.menu_name);
        TextView price = view.findViewById(R.id.price);
        TextView zaiko = view.findViewById(R.id.zaiko);
        TextView inventory = view.findViewById(R.id.inventory);
        ImageView btnAdd = view.findViewById(R.id.btnAdd);
        ImageView btnSub = view.findViewById(R.id.btnSub);
        btnAdd.setClickable(true);
        btnSub.setClickable(true);

        if("m".equals(selectedId[1])){
            CustomMenuMasterSubDto menu = orderConfirmActivity.getApp().getMenuInfoList().get(selectedMenuId);
            menuId.setText(menu.getMenuId());
            menuName.setText(menu.getMenuName());
            if(CodeKbn.menuPriceDisplayKbn_TAX_OUT.equals(orderConfirmActivity.getApp().getMenuPriceDisplayKbn())){
                price.setText(AmountUtil.amountFormat(menu.getOutTaxPrice()));
            } else {
                price.setText(AmountUtil.amountFormat(menu.getInTaxPrice()));
            }
            if(CodeKbn.statusFlg_ON.equals(menu.getInventoryControlKbn())){
                zaiko.setText("在庫:" + menu.getInventory());
            } else {
                zaiko.setVisibility(View.GONE);
            }
            setInventory(inventory, orderConfirmActivity.getApp().getMenuList().get(selectedMenuId));

            // ドリンクの場合
            if(CodeKbn.comKbn_YES.equals(menu.getDrinkKbn())){
                btnAdd.setVisibility(View.GONE);
                btnSub.setVisibility(View.GONE);

                Button btnEdit = view.findViewById(R.id.btnEdit);
                btnEdit.setVisibility(View.VISIBLE);
                btnEdit.setOnClickListener(v -> {
                    orderConfirmActivity.openDrinkDialog(menu);
                });
            }
            // ランチの場合
            else if(CodeKbn.comKbn_YES.equals(menu.getLunchKbn())){
                btnAdd.setVisibility(View.GONE);
                btnSub.setVisibility(View.GONE);

                Button btnEdit = view.findViewById(R.id.btnEdit);
                btnEdit.setVisibility(View.VISIBLE);
                btnEdit.setOnClickListener(v -> {
                    orderConfirmActivity.openLunchDetailDialog(menu);
                });
            } else {
                btnAdd.setOnClickListener(v -> {
                    Integer oldCount = orderConfirmActivity.getApp().getMenuList().get(menu.getMenuId());
                    if(oldCount == null){
                        oldCount = 0;
                    }
                    int newCount = oldCount + 1;
                    if(CodeKbn.statusFlg_ON.equals(menu.getInventoryControlKbn())){
                        if(newCount <= menu.getInventory()){
                            orderConfirmActivity.getApp().getMenuList().put(menu.getMenuId(), newCount);
                            setInventory(inventory, newCount);
                        }
                    } else {
                        orderConfirmActivity.getApp().getMenuList().put(menu.getMenuId(), newCount);
                        setInventory(inventory, newCount);
                    }
                });

                btnSub.setOnClickListener(v -> {
                    Integer oldCount = orderConfirmActivity.getApp().getMenuList().get(menu.getMenuId());
                    if(oldCount == null || oldCount <=1 ){
                        orderConfirmActivity.getApp().getMenuList().remove(menu.getMenuId());
                        orderConfirmActivity.initPage();
                    } else {
                        int newCount = oldCount - 1;
                        orderConfirmActivity.getApp().getMenuList().put(menu.getMenuId(), newCount);
                        setInventory(inventory, newCount);
                    }
                });
            }
        } else {
            CustomCourseMasterSubDto course = orderConfirmActivity.getApp().getCourseInfoList().get(selectedMenuId);
            menuId.setText(course.getCourseId());
            menuName.setText(course.getCourseName());
            if(CodeKbn.menuPriceDisplayKbn_TAX_OUT.equals(orderConfirmActivity.getApp().getMenuPriceDisplayKbn())){
                price.setText(AmountUtil.amountFormat(course.getOutTaxPrice()));
            } else {
                price.setText(AmountUtil.amountFormat(course.getInTaxPrice()));
            }
            if(CodeKbn.statusFlg_ON.equals(course.getInventoryControlKbn())){
                zaiko.setText("在庫:" + course.getInventory());
            } else {
                zaiko.setVisibility(View.GONE);
            }
            setInventory(inventory, orderConfirmActivity.getApp().getCourseList().get(selectedMenuId));

            btnAdd.setOnClickListener(v -> {
                Integer oldCount = orderConfirmActivity.getApp().getCourseList().get(course.getCourseId());
                if(oldCount == null){
                    oldCount = 0;
                }
                int newCount = oldCount + 1;
                if(CodeKbn.statusFlg_ON.equals(course.getInventoryControlKbn())){
                    if(newCount <= course.getInventory()){
                        orderConfirmActivity.getApp().getCourseList().put(course.getCourseId(), newCount);
                        setInventory(inventory, newCount);
                    }
                } else {
                    orderConfirmActivity.getApp().getCourseList().put(course.getCourseId(), newCount);
                    setInventory(inventory, newCount);
                }
            });

            btnSub.setOnClickListener(v -> {
                Integer oldCount = orderConfirmActivity.getApp().getCourseList().get(course.getCourseId());
                if(oldCount == null || oldCount <=1 ){
                    orderConfirmActivity.getApp().getCourseList().remove(course.getCourseId());
                    orderConfirmActivity.initPage();
                } else {
                    int newCount = oldCount - 1;
                    orderConfirmActivity.getApp().getCourseList().put(course.getCourseId(), newCount);
                    setInventory(inventory, newCount);
                }
            });
        }

        return view;
    }

    private void setInventory(TextView inventory, int count){
        inventory.setText("数量:" + count);
    }
}