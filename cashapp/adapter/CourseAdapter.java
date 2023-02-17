package com.afroci.cashapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afroci.cashapp.R;
import com.afroci.cashapp.base.BaseActivity;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.dto.CustomCourseMasterSubDto;
import com.afroci.cashapp.dto.CustomMenuMasterSubDto;
import com.afroci.cashapp.dto.TableResDto;
import com.afroci.cashapp.util.AmountUtil;
import com.afroci.cashapp.view.CategoriMenuActivity;
import com.afroci.cashapp.view.TableActivity;
import com.afroci.cashapp.view.TableSelectActivity;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<CustomCourseMasterSubDto> {

    private int resourceId;
    private CategoriMenuActivity categoriMenuActivity = null;

    public CourseAdapter(CategoriMenuActivity activity, int textViewResourceId, List<CustomCourseMasterSubDto> objects) {
        super(activity, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
        this.categoriMenuActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomCourseMasterSubDto course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView courseId = view.findViewById(R.id.course_id);
        courseId.setText(course.getCourseId());

        TextView courseName = view.findViewById(R.id.course_name);
        courseName.setText(course.getCourseName());

//        TextView price = view.findViewById(R.id.price);
//        if(CodeKbn.menuPriceDisplayKbn_TAX_OUT.equals(categoriMenuActivity.getApp().getMenuPriceDisplayKbn())){
//            price.setText(AmountUtil.amountFormat(course.getOutTaxPrice()));
//        } else {
//            price.setText(AmountUtil.amountFormat(course.getInTaxPrice()));
//        }
//        if(CodeKbn.statusFlg_ON.equals(course.getInventoryControlKbn())){
//            TextView inventory = view.findViewById(R.id.inventory);
//            inventory.setText("在庫:" + course.getInventory());
//        }

        TextView soldOut = view.findViewById(R.id.soldOut);

        ImageView btnAdd = view.findViewById(R.id.btnAdd);
        ImageView btnSub = view.findViewById(R.id.btnSub);

        TextView menu_count = view.findViewById(R.id.menu_count);

        if(CodeKbn.statusFlg_ON.equals(course.getInventoryControlKbn()) && CodeKbn.soldOutKbn_Y.equals(course.getSoldOutKbn())){
            soldOut.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
            btnSub.setVisibility(View.GONE);
            menu_count.setVisibility(View.GONE);
        } else {
            soldOut.setVisibility(View.GONE);

            LinearLayout menu_row = view.findViewById(R.id.menu_row);
            menu_row.setClickable(true);
            menu_row.setOnClickListener(v -> {
                Integer oldCount = categoriMenuActivity.getApp().getCourseList().get(course.getCourseId());
                if(oldCount == null){
                    oldCount = 0;
                }
                int newCount = oldCount + 1;
                if(CodeKbn.statusFlg_ON.equals(course.getInventoryControlKbn())){
                    if(newCount <= course.getInventory()){
                        categoriMenuActivity.getApp().getCourseList().put(course.getCourseId(), newCount);
                        categoriMenuActivity.refreshSelectedCount();
                    }
                } else {
                    categoriMenuActivity.getApp().getCourseList().put(course.getCourseId(), newCount);
                    categoriMenuActivity.refreshSelectedCount();
                }
                setMenuCount(menu_count, course);
            });

            // + -
            btnAdd.setClickable(true);
            btnSub.setClickable(true);
            btnAdd.setOnClickListener(v -> {menu_row.performClick();});
            btnSub.setOnClickListener(v -> {
                Integer oldCount = categoriMenuActivity.getApp().getCourseList().get(course.getCourseId());
                if(oldCount == null){
                    oldCount = 0;
                }
                if(oldCount > 1){
                    int newCount = oldCount - 1;
                    categoriMenuActivity.getApp().getCourseList().put(course.getCourseId(), newCount);
                } else {
                    categoriMenuActivity.getApp().getCourseList().remove(course.getCourseId());
                }
                categoriMenuActivity.refreshSelectedCount();
                setMenuCount(menu_count, course);
            });

            setMenuCount(menu_count, course);
        }

        return view;
    }

    private void setMenuCount(TextView menu_count, CustomCourseMasterSubDto course) {
        Integer count = categoriMenuActivity.getApp().getCourseList().get(course.getCourseId());
        if(count == null){
            count = 0;
        }
        menu_count.setText(String.valueOf(count));
    }
}