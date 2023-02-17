package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.base.BaseActivity;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomMenuCategoryMasterSubDto;
import com.afroci.cashapp.dto.TableResDto;
import com.afroci.cashapp.view.CategoriMenuActivity;
import com.afroci.cashapp.view.TableActivity;
import com.afroci.cashapp.view.TableSelectActivity;

import java.util.List;

public class CategoriAdapter extends ArrayAdapter<CustomMenuCategoryMasterSubDto> {

    private int resourceId;
    private CategoriMenuActivity categoriMenuActivity = null;
    private String type;

    public CategoriAdapter(CategoriMenuActivity activity, int textViewResourceId, List<CustomMenuCategoryMasterSubDto> objects, String type) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.categoriMenuActivity = activity;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomMenuCategoryMasterSubDto categori = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView categoriName = view.findViewById(R.id.categoriId);
        categoriName.setText(categori.getMenuCategoryName());
        categoriName.setOnClickListener(v -> show(categori));
        categoriName.setClickable(true);

        if("parent".equals(type)) {
            ImageView icon = view.findViewById(R.id.rightSideIcon);
            if(CodeKbn.subDisplayKbn_CATEGORY.equals(categori.getSubDisplayKbn())){
                icon.setVisibility(View.VISIBLE);
            } else {
                icon.setVisibility(View.GONE);
            }
        }

        return view;
    }

    private void show(CustomMenuCategoryMasterSubDto categori) {
        String subDisplayKbn = categori.getSubDisplayKbn();

        if("parent".equals(type)) {
            if(CodeKbn.subDisplayKbn_CATEGORY.equals(subDisplayKbn)){
                showSubCategori(categori);
            } else if(CodeKbn.subDisplayKbn_MENU.equals(subDisplayKbn)){
                showMenu(categori);
            } else if(CodeKbn.subDisplayKbn_COURSE.equals(subDisplayKbn)){
                showCourse(categori);
            }
        } else if("sub".equals(type)) {
            if(CodeKbn.subDisplayKbn_MENU.equals(subDisplayKbn)){
                showMenu(categori);
            } else if(CodeKbn.subDisplayKbn_COURSE.equals(subDisplayKbn)){
                showCourse(categori);
            }
        }
    }

    private void showCourse(CustomMenuCategoryMasterSubDto categori) {
        categoriMenuActivity.showCourse(categori);
    }

    private void showMenu(CustomMenuCategoryMasterSubDto categori) {
        categoriMenuActivity.showMenu(categori);
    }

    private void showSubCategori(CustomMenuCategoryMasterSubDto categori) {
        categoriMenuActivity.showSubCategori(categori);
    }
}