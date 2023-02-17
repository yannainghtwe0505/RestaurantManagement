package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomMenuMasterSubDto;
import com.afroci.cashapp.view.CategoriMenuActivity;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<CustomMenuMasterSubDto> {

    private int resourceId;
    private CategoriMenuActivity categoriMenuActivity = null;

    public MenuAdapter(CategoriMenuActivity activity, int textViewResourceId, List<CustomMenuMasterSubDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.categoriMenuActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomMenuMasterSubDto menu = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView menuId = view.findViewById(R.id.menu_id);
        menuId.setText(menu.getMenuId());

        TextView menuName = view.findViewById(R.id.menu_name);
        menuName.setText(menu.getMenuName());

        TextView soldOut = view.findViewById(R.id.soldOut);

        ImageView btnAdd = view.findViewById(R.id.btnAdd);
        ImageView btnSub = view.findViewById(R.id.btnSub);

        TextView menu_count = view.findViewById(R.id.menu_count);

        if(CodeKbn.statusFlg_ON.equals(menu.getInventoryControlKbn()) && CodeKbn.soldOutKbn_Y.equals(menu.getSoldOutKbn())){
            soldOut.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
            btnSub.setVisibility(View.GONE);
            menu_count.setVisibility(View.GONE);
        } else {
            soldOut.setVisibility(View.GONE);
            LinearLayout menu_row = view.findViewById(R.id.menu_row);
            menu_row.setClickable(true);

            // ドリンク
            if(CodeKbn.comKbn_YES.equals(menu.getDrinkKbn())){
                btnAdd.setVisibility(View.GONE);
                btnSub.setVisibility(View.GONE);

                menu_row.setOnClickListener(v -> {
                    categoriMenuActivity.openDrinkDialog(menu);
                });
            }

            // ランチの場合
            else if(CodeKbn.comKbn_YES.equals(menu.getLunchKbn())){
                btnAdd.setVisibility(View.GONE);
                btnSub.setVisibility(View.GONE);

                menu_row.setOnClickListener(v -> {
                    categoriMenuActivity.openLunchDialog(menu);
                });
            }

            // 一般メニュ
            else {

                menu_row.setOnClickListener(v -> {
                    Integer oldCount = categoriMenuActivity.getApp().getMenuList().get(menu.getMenuId());
                    if(oldCount == null){
                        oldCount = 0;
                    }
                    int newCount = oldCount + 1;
                    if(CodeKbn.statusFlg_ON.equals(menu.getInventoryControlKbn())){
                        if(newCount <= menu.getInventory()){
                            categoriMenuActivity.getApp().getMenuList().put(menu.getMenuId(), newCount);
                            categoriMenuActivity.refreshSelectedCount();
                        }
                    } else {
                        categoriMenuActivity.getApp().getMenuList().put(menu.getMenuId(), newCount);
                        categoriMenuActivity.refreshSelectedCount();
                    }
                    setMenuCount(menu_count, menu);
                });

                // + -
                btnAdd.setClickable(true);
                btnSub.setClickable(true);
                btnAdd.setOnClickListener(v -> {menu_row.performClick();});
                btnSub.setOnClickListener(v -> {
                    Integer oldCount = categoriMenuActivity.getApp().getMenuList().get(menu.getMenuId());
                    if(oldCount == null){
                        oldCount = 0;
                    }
                    if(oldCount > 1){
                        int newCount = oldCount - 1;
                        categoriMenuActivity.getApp().getMenuList().put(menu.getMenuId(), newCount);
                    } else {
                        categoriMenuActivity.getApp().getMenuList().remove(menu.getMenuId());
                    }
                    categoriMenuActivity.refreshSelectedCount();
                    setMenuCount(menu_count, menu);
                });
            }

            setMenuCount(menu_count, menu);
        }

        return view;
    }

    private void setMenuCount(TextView menu_count, CustomMenuMasterSubDto menu) {
        Integer count = categoriMenuActivity.getApp().getMenuList().get(menu.getMenuId());
        if(count == null){
            count = 0;
        }
        menu_count.setText(String.valueOf(count));
    }
}