package com.afroci.cashapp.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.afroci.cashapp.R;
import com.afroci.cashapp.constant.ApiUrl;
import com.afroci.cashapp.constant.CodeKbn;
import com.afroci.cashapp.constant.Constants;
import com.afroci.cashapp.constant.Messages;
import com.afroci.cashapp.dto.CustomDrinkTypyMasterDto;
import com.afroci.cashapp.dto.CustomMenuMasterSubDto;
import com.afroci.cashapp.dto.CustomOrderDetailResDto;
import com.afroci.cashapp.dto.CustomOrderResDto;
import com.afroci.cashapp.util.AlertUtil;
import com.afroci.cashapp.util.DialogUtil;
import com.afroci.cashapp.util.HttpUtil;
import com.afroci.cashapp.util.SharedPreferencesUtil;
import com.afroci.cashapp.view.LoginActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

public class BaseActivity extends AppCompatActivity implements HttpCallBack{

    protected static final String REQ_TYPE_STATUS_UPDATE = "9";

    private static final String PAGE_DATA_KEY = "data";

    protected ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = AlertUtil.initLoading(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (loading != null && loading.isShowing()){
            loading.dismiss();
        }
    }

    public BaseApplication getApp() {
        return (BaseApplication)super.getApplication();
    }

    @Override
    public void onRequestComplete(Object response, String reqType) {

    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading() {
        loading.dismiss();
    }

    @Override
    public void timeout() {
        DialogUtil.showTimeoutConfirm(this, () -> {
            Map<String,Object> params = new HashMap<>();
            params.put(Constants.tokenId, "");
            SharedPreferencesUtil.set(getApplicationContext(), params);
            getApp().setTokenId("");
            gotoPage(LoginActivity.class);
            this.finish();
        });
    }

    @Override
    public void onErrorMsg(String msg) {
        runOnUiThread(() -> DialogUtil.showMsg(this, msg));
    }

    /**
     * 画面遷移
     */
    public void gotoPage(Class<?> zlass) {
        Intent intent = new Intent(getApplicationContext(), zlass);
        startActivity(intent);
    }

    /**
     * 画面遷移(パラメータ)
     */
    public void gotoPage(Class<?> zlass, Serializable data) {
        Intent intent = new Intent(getApplicationContext(), zlass);
        Bundle b = new Bundle();
        b.putSerializable(PAGE_DATA_KEY, data);
        intent.putExtras(b);
        startActivity(intent);
    }

    public Object getPageData() {
        return super.getIntent().getSerializableExtra(PAGE_DATA_KEY);
    }

    /**
     * 注    文詳細ステータス更新
     */
    public void updateOrderDetailStatus(CustomOrderDetailResDto order, String status) {
        DialogUtil.showConfirm(this, String.format(Messages.ORDER_STATUS_UPDATE_CONFIRM, CodeKbn.orderStatus_map.get(status)), () -> {
            Map<String,Object> params = new HashMap<>();
            params.put(Constants.tokenId, getApp().getTokenId());
            params.put(Constants.reqType, REQ_TYPE_STATUS_UPDATE);
            params.put(Constants.orderId, order.getOrderId());
            params.put(Constants.menuId, order.getMenuId());
            params.put(Constants.orderStatus, status);
            params.put(Constants.oldOrderStatus, order.getOrderStatus());

            HttpUtil.doPostAsync(ApiUrl.KITCHEN_ORDER_STATUS_UPDATE, params, this, getApplicationContext());
        });
    }

    /**
     * 注文ステータス更新
     */
    public void updateOrderStatus(CustomOrderResDto order, String status) {
        DialogUtil.showConfirm(this, String.format(Messages.ORDER_STATUS_UPDATE_CONFIRM, CodeKbn.orderStatus_map.get(status)), () -> {
            Map<String,Object> params = new HashMap<>();
            params.put(Constants.tokenId, getApp().getTokenId());
            params.put(Constants.reqType, REQ_TYPE_STATUS_UPDATE);
            params.put(Constants.orderId, order.getOrderId());
            params.put(Constants.orderStatus, status);
            params.put(Constants.oldOrderStatus, order.getOrderStatus());

            HttpUtil.doPostAsync(ApiUrl.KITCHEN_ORDER_STATUS_UPDATE, params, this, getApplicationContext());
        });
    }

    /**
     * プリンターサービス
     */
    protected IWoyouService woyouService;

    protected ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
        }
    };

    protected ICallback callback = new ICallback.Stub() {

        @Override
        public void onRunResult(boolean success) throws RemoteException {
            System.out.print("onRunResult START");
            System.out.print("success:" + success);
            System.out.print("onRunResult END");
        }

        @Override
        public void onReturnString(final String value) throws RemoteException {
            System.out.print("onReturnString START");
            System.out.print("value:" + value);
            System.out.print("onReturnString END");
        }

        @Override
        public void onRaiseException(int code, final String msg) throws RemoteException {
            System.out.print("onRaiseException START");
            System.out.print("code:" + code);
            System.out.print("msg:" + msg);
            System.out.print("onRaiseException END");
        }
    };

    protected void initPrintService() {
        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");

        startService(intent);
        bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    /** ドリンク */
    protected AlertDialog drinkDialog = null;
    public Map<String, Integer> currentDrinkTypeCountList = new LinkedHashMap<String, Integer>();

    /**
     * お酒注文画面初期化
     *
     * @param menu
     */
    public void openDrinkDialog(CustomMenuMasterSubDto menu) {
        if(drinkDialog == null || !drinkDialog.isShowing()){
            _openDrinkDialog(menu);
        }
    }

    private void _openDrinkDialog(CustomMenuMasterSubDto menu) {
        View drinkTypeView = View.inflate(this, R.layout.drink_type, null);
        drinkDialog = new AlertDialog.Builder(this).create();
        drinkDialog.setCancelable(false);
        drinkDialog.show();

        Window window = drinkDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = DialogUtil.getWidth(this);
        window.setGravity(Gravity.CENTER);
        window.setContentView(drinkTypeView);
        window.setAttributes(lp);

        TextView menuName = drinkTypeView.findViewById(R.id.menuName);
        menuName.setText(menu.getMenuName());

        ImageButton menu_btnSub = drinkTypeView.findViewById(R.id.menu_btnSub);
        ImageButton menu_btnAdd = drinkTypeView.findViewById(R.id.menu_btnAdd);
        TextView menu_count = drinkTypeView.findViewById(R.id.menu_count);
        setDrinkMenuAdd(menu_btnSub, menu_btnAdd, menu_count, menu);

        initDrinkType(drinkTypeView, menu);

        Button btnClose = (Button) drinkTypeView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(view -> {
            closeDrinkDialog();
        });

        Button btnConfirm = drinkTypeView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            // 注文数認証
            int menuCount = Integer.parseInt(menu_count.getText().toString());
            if(menuCount == 0){
                currentDrinkTypeCountList.clear();
            }

            if(currentDrinkTypeCountList.size() > 0){
                // ドリンクタイプ設定数認証
                int typeTotalCount = getTypeTotalCount();
                if(typeTotalCount != menuCount){
                    DialogUtil.showMsg(this, Messages.DRINK_COUNT2);
                    return;
                }

                if(getApp().getSelectedDrinkMenuDrinkTypeCountList().get(menu.getMenuId()) == null){
                    getApp().getSelectedDrinkMenuDrinkTypeCountList().put(menu.getMenuId(), new HashMap<String, Integer>());
                }

                for(Map.Entry<String, Integer> type : currentDrinkTypeCountList.entrySet()){
                    getApp().getSelectedDrinkMenuDrinkTypeCountList().get(menu.getMenuId()).put(type.getKey(), type.getValue());
                }

            } else {
                getApp().getSelectedDrinkMenuDrinkTypeCountList().remove(menu.getMenuId());
            }

            if(menuCount > 0){
                getApp().getMenuList().put(menu.getMenuId(), menuCount);
            } else {
                getApp().getMenuList().remove(menu.getMenuId());
            }

            refreshSelectedCount();
            closeDrinkDialog();
        });
    }

    protected void refreshSelectedCount() {
    }

    private void setDrinkMenuAdd(ImageButton btnSub, ImageButton btnAdd, TextView menuCount, CustomMenuMasterSubDto displayMenuDto) {
        boolean zaiko = true;
        if(CodeKbn.statusFlg_ON.equals(displayMenuDto.getInventoryControlKbn())){
            if(displayMenuDto.getInventory() <= 0){
                zaiko = false;
            }
        }

        if(zaiko){
            btnAdd.setClickable(true);
            btnAdd.setOnClickListener(view -> {
                Integer oldCount = Integer.parseInt(menuCount.getText().toString());
                if(oldCount == null){
                    oldCount = 0;
                }
                int newCount = oldCount + 1;
                boolean add = false;
                if(CodeKbn.statusFlg_ON.equals(displayMenuDto.getInventoryControlKbn())){
                    if(newCount <= displayMenuDto.getInventory()){
                        add = true;
                    }
                } else {
                    add = true;
                }
                if(add){
                    menuCount.setText(newCount + "");
                }
            });
            btnSub.setClickable(true);
            btnSub.setOnClickListener(view -> {
                Integer oldCount = Integer.parseInt(menuCount.getText().toString());
                if(oldCount == null || oldCount == 0){
                    return;
                }
                int newCount = oldCount - 1;
                if(newCount < 0){
                    newCount = 0;
                }
                menuCount.setText(newCount + "");
            });
        } else {
            btnAdd.setClickable(false);
            btnSub.setClickable(false);
        }

        Integer c = getApp().getMenuList().get(displayMenuDto.getMenuId());
        if(c != null){
            menuCount.setText(String.valueOf(c));
        }
    }

    private void closeDrinkDialog() {
        currentDrinkTypeCountList.clear();
        if(drinkDialog != null && drinkDialog.isShowing()){
            drinkDialog.dismiss();
            drinkDialog = null;
        }
    }

    private int getTypeTotalCount() {
        int totalCount = 0;
        for(Map.Entry<String, Integer> type : currentDrinkTypeCountList.entrySet()){
            totalCount += type.getValue();
        }
        return totalCount;
    }

    private void initDrinkType(View drinkTypeView, CustomMenuMasterSubDto displayMenuDto) {
        LinearLayout drink_type_require = drinkTypeView.findViewById(R.id.drink_type_require);
        LinearLayout row1 = drinkTypeView.findViewById(R.id.drink_type_row_1);
        LinearLayout row2 = drinkTypeView.findViewById(R.id.drink_type_row_2);
        LinearLayout row3 = drinkTypeView.findViewById(R.id.drink_type_row_3);
        LinearLayout row_1_col_1 = drinkTypeView.findViewById(R.id.drink_type_row_1_col_1);
        LinearLayout row_1_col_2 = drinkTypeView.findViewById(R.id.drink_type_row_1_col_2);
        LinearLayout row_2_col_1 = drinkTypeView.findViewById(R.id.drink_type_row_1_col_3);
        LinearLayout row_2_col_2 = drinkTypeView.findViewById(R.id.drink_type_row_2_col_1);
        LinearLayout row_3_col_1 = drinkTypeView.findViewById(R.id.drink_type_row_2_col_2);
        LinearLayout row_3_col_2 = drinkTypeView.findViewById(R.id.drink_type_row_2_col_3);
        drink_type_require.setVisibility(View.GONE);
        row1.setVisibility(View.GONE);
        row2.setVisibility(View.GONE);
        row3.setVisibility(View.GONE);
        row_1_col_1.setVisibility(View.INVISIBLE);
        row_1_col_2.setVisibility(View.INVISIBLE);
        row_2_col_1.setVisibility(View.INVISIBLE);
        row_2_col_2.setVisibility(View.INVISIBLE);
        row_3_col_1.setVisibility(View.INVISIBLE);
        row_3_col_2.setVisibility(View.INVISIBLE);
        currentDrinkTypeCountList.clear();

        List<CustomDrinkTypyMasterDto> drinkTypeList = getDrinkType(displayMenuDto.getMenuCategoryId(), displayMenuDto.getMenuId());
        if(drinkTypeList != null){
            ImageButton menu_btnSub = drinkTypeView.findViewById(R.id.menu_btnSub);
            ImageButton menu_btnAdd = drinkTypeView.findViewById(R.id.menu_btnAdd);
            if(drinkTypeList.size() > 0){
                menu_btnSub.setVisibility(View.INVISIBLE);
                menu_btnAdd.setVisibility(View.INVISIBLE);

                drink_type_require.setVisibility(View.VISIBLE);
                row1.setVisibility(View.VISIBLE);
            }
            if(drinkTypeList.size() > 2){
                row2.setVisibility(View.VISIBLE);
            }
            if(drinkTypeList.size() > 4){
                row3.setVisibility(View.VISIBLE);
            }
            int count = 0;
            Map<String, Integer> selectedDrinkTypeCountList = getApp().getSelectedDrinkMenuDrinkTypeCountList().get(displayMenuDto.getMenuId());
            for(CustomDrinkTypyMasterDto drinkType : drinkTypeList){
                count++;

                if(selectedDrinkTypeCountList != null && selectedDrinkTypeCountList.get(drinkType.getDrinkTypyId()) != null){
                    currentDrinkTypeCountList.put(drinkType.getDrinkTypyId(), selectedDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else {
                    currentDrinkTypeCountList.put(drinkType.getDrinkTypyId(), 0);
                }

                if(count == 1){
                    initDrinkType1(drinkType, drinkTypeView, displayMenuDto, menu_btnSub, menu_btnAdd, currentDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else if(count == 2){
                    initDrinkType2(drinkType, drinkTypeView, displayMenuDto, menu_btnSub, menu_btnAdd, currentDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else if(count == 3){
                    initDrinkType3(drinkType, drinkTypeView, displayMenuDto, menu_btnSub, menu_btnAdd, currentDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else if(count == 4){
                    initDrinkType4(drinkType, drinkTypeView, displayMenuDto, menu_btnSub, menu_btnAdd, currentDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else if(count == 5){
                    initDrinkType5(drinkType, drinkTypeView, displayMenuDto, menu_btnSub, menu_btnAdd, currentDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else if(count == 6){
                    initDrinkType6(drinkType, drinkTypeView, displayMenuDto, menu_btnSub, menu_btnAdd, currentDrinkTypeCountList.get(drinkType.getDrinkTypyId()));
                } else {
                    break;
                }
            }
        }
    }

    private void initDrinkType1(CustomDrinkTypyMasterDto drinkType, View drinkTypeView, CustomMenuMasterSubDto displayMenuDto, ImageButton menu_btnSub, ImageButton menu_btnAdd, Integer count) {
        LinearLayout row_1_col_1 = drinkTypeView.findViewById(R.id.drink_type_row_1_col_1);
        row_1_col_1.setVisibility(View.VISIBLE);
        TextView row_1_col_1_name = drinkTypeView.findViewById(R.id.drink_type_row_1_col_1_name);
        row_1_col_1_name.setText(drinkType.getName());

        ImageButton drink_type_row_1_col_1_btnSub = drinkTypeView.findViewById(R.id.drink_type_row_1_col_1_btnSub);
        ImageButton drink_type_row_1_col_1_btnAdd = drinkTypeView.findViewById(R.id.drink_type_row_1_col_1_btnAdd);
        TextView drink_type_row_1_col_1_count = drinkTypeView.findViewById(R.id.drink_type_row_1_col_1_count);
        drink_type_row_1_col_1_count.setText(String.valueOf(count));
        setDrinkTypeAdd(drink_type_row_1_col_1_btnSub, drink_type_row_1_col_1_btnAdd, drink_type_row_1_col_1_count, displayMenuDto, drinkType, menu_btnSub, menu_btnAdd);
    }

    private void initDrinkType2(CustomDrinkTypyMasterDto drinkType, View drinkTypeView, CustomMenuMasterSubDto displayMenuDto, ImageButton menu_btnSub, ImageButton menu_btnAdd, Integer count) {
        LinearLayout row_1_col_2 = drinkTypeView.findViewById(R.id.drink_type_row_1_col_2);
        row_1_col_2.setVisibility(View.VISIBLE);
        TextView row_1_col_2_name = drinkTypeView.findViewById(R.id.drink_type_row_1_col_2_name);
        row_1_col_2_name.setText(drinkType.getName());

        ImageButton drink_type_row_1_col_2_btnSub = drinkTypeView.findViewById(R.id.drink_type_row_1_col_2_btnSub);
        ImageButton drink_type_row_1_col_2_btnAdd = drinkTypeView.findViewById(R.id.drink_type_row_1_col_2_btnAdd);
        TextView drink_type_row_1_col_2_count = drinkTypeView.findViewById(R.id.drink_type_row_1_col_2_count);
        drink_type_row_1_col_2_count.setText(String.valueOf(count));
        setDrinkTypeAdd(drink_type_row_1_col_2_btnSub, drink_type_row_1_col_2_btnAdd, drink_type_row_1_col_2_count, displayMenuDto, drinkType, menu_btnSub, menu_btnAdd);
    }

    private void initDrinkType3(CustomDrinkTypyMasterDto drinkType, View drinkTypeView, CustomMenuMasterSubDto displayMenuDto, ImageButton menu_btnSub, ImageButton menu_btnAdd, Integer count) {
        LinearLayout row_1_col_3 = drinkTypeView.findViewById(R.id.drink_type_row_1_col_3);
        row_1_col_3.setVisibility(View.VISIBLE);
        TextView row_1_col_3_name = drinkTypeView.findViewById(R.id.drink_type_row_1_col_3_name);
        row_1_col_3_name.setText(drinkType.getName());

        ImageButton drink_type_row_1_col_3_btnSub = drinkTypeView.findViewById(R.id.drink_type_row_1_col_3_btnSub);
        ImageButton drink_type_row_1_col_3_btnAdd = drinkTypeView.findViewById(R.id.drink_type_row_1_col_3_btnAdd);
        TextView drink_type_row_1_col_3_count = drinkTypeView.findViewById(R.id.drink_type_row_1_col_3_count);
        drink_type_row_1_col_3_count.setText(String.valueOf(count));
        setDrinkTypeAdd(drink_type_row_1_col_3_btnSub, drink_type_row_1_col_3_btnAdd, drink_type_row_1_col_3_count, displayMenuDto, drinkType, menu_btnSub, menu_btnAdd);
    }

    private void initDrinkType4(CustomDrinkTypyMasterDto drinkType, View drinkTypeView, CustomMenuMasterSubDto displayMenuDto, ImageButton menu_btnSub, ImageButton menu_btnAdd, Integer count) {
        LinearLayout row_2_col_1 = drinkTypeView.findViewById(R.id.drink_type_row_2_col_1);
        row_2_col_1.setVisibility(View.VISIBLE);
        TextView row_2_col_1_name = drinkTypeView.findViewById(R.id.drink_type_row_2_col_1_name);
        row_2_col_1_name.setText(drinkType.getName());

        ImageButton drink_type_row_2_col_1_btnSub = drinkTypeView.findViewById(R.id.drink_type_row_2_col_1_btnSub);
        ImageButton drink_type_row_2_col_1_btnAdd = drinkTypeView.findViewById(R.id.drink_type_row_2_col_1_btnAdd);
        TextView drink_type_row_2_col_1_count = drinkTypeView.findViewById(R.id.drink_type_row_2_col_1_count);
        drink_type_row_2_col_1_count.setText(String.valueOf(count));
        setDrinkTypeAdd(drink_type_row_2_col_1_btnSub, drink_type_row_2_col_1_btnAdd, drink_type_row_2_col_1_count, displayMenuDto, drinkType, menu_btnSub, menu_btnAdd);
    }

    private void initDrinkType5(CustomDrinkTypyMasterDto drinkType, View drinkTypeView, CustomMenuMasterSubDto displayMenuDto, ImageButton menu_btnSub, ImageButton menu_btnAdd, Integer count) {
        LinearLayout row_2_col_2 = drinkTypeView.findViewById(R.id.drink_type_row_2_col_2);
        row_2_col_2.setVisibility(View.VISIBLE);
        TextView row_2_col_2_name = drinkTypeView.findViewById(R.id.drink_type_row_2_col_2_name);
        row_2_col_2_name.setText(drinkType.getName());

        ImageButton drink_type_row_2_col_2_btnSub = drinkTypeView.findViewById(R.id.drink_type_row_2_col_2_btnSub);
        ImageButton drink_type_row_2_col_2_btnAdd = drinkTypeView.findViewById(R.id.drink_type_row_2_col_2_btnAdd);
        TextView drink_type_row_2_col_2_count = drinkTypeView.findViewById(R.id.drink_type_row_2_col_2_count);
        drink_type_row_2_col_2_count.setText(String.valueOf(count));
        setDrinkTypeAdd(drink_type_row_2_col_2_btnSub, drink_type_row_2_col_2_btnAdd, drink_type_row_2_col_2_count, displayMenuDto, drinkType, menu_btnSub, menu_btnAdd);
    }

    private void initDrinkType6(CustomDrinkTypyMasterDto drinkType, View drinkTypeView, CustomMenuMasterSubDto displayMenuDto, ImageButton menu_btnSub, ImageButton menu_btnAdd, Integer count) {
        LinearLayout row_2_col_3 = drinkTypeView.findViewById(R.id.drink_type_row_2_col_3);
        row_2_col_3.setVisibility(View.VISIBLE);
        TextView row_2_col_3_name = drinkTypeView.findViewById(R.id.drink_type_row_2_col_3_name);
        row_2_col_3_name.setText(drinkType.getName());

        ImageButton drink_type_row_2_col_3_btnSub = drinkTypeView.findViewById(R.id.drink_type_row_2_col_3_btnSub);
        ImageButton drink_type_row_2_col_3_btnAdd = drinkTypeView.findViewById(R.id.drink_type_row_2_col_3_btnAdd);
        TextView drink_type_row_2_col_3_count = drinkTypeView.findViewById(R.id.drink_type_row_2_col_3_count);
        drink_type_row_2_col_3_count.setText(String.valueOf(count));
        setDrinkTypeAdd(drink_type_row_2_col_3_btnSub, drink_type_row_2_col_3_btnAdd, drink_type_row_2_col_3_count, displayMenuDto, drinkType, menu_btnSub, menu_btnAdd);
    }

    private List<CustomDrinkTypyMasterDto> getDrinkType(String categoryId, String menuId) {
        List<CustomDrinkTypyMasterDto> drinkType = null;
        if(getApp().getCategoryDrinkTypeList() != null){
            drinkType = getApp().getCategoryDrinkTypeList().get(categoryId);

            // 一般カテゴリーにお酒メニューの場合
            if(drinkType == null){
                String eachCategoryId;
                List<CustomMenuMasterSubDto> list;
                loop : for(Map.Entry<String, List<CustomDrinkTypyMasterDto>> e : getApp().getCategoryDrinkTypeList().entrySet()){
                    eachCategoryId = e.getKey();
                    list = getApp().getCategoryMenuList().get(eachCategoryId);
                    if(list != null){
                        for(CustomMenuMasterSubDto menu : list){
                            if(menu.getMenuId().equals(menuId)){
                                drinkType = e.getValue();
                                break loop;
                            }
                        }
                    }
                }
            }
        }

        return drinkType;
    }

    private void setDrinkTypeAdd(ImageButton drink_type_row_1_col_1_btnSub, ImageButton drink_type_row_1_col_1_btnAdd, TextView drink_type_count, CustomMenuMasterSubDto displayMenuDto, CustomDrinkTypyMasterDto drinkType, ImageButton menu_btnSub, ImageButton menu_btnAdd) {
        drink_type_row_1_col_1_btnAdd.setClickable(true);
        drink_type_row_1_col_1_btnAdd.setOnClickListener(view -> {
            int typeTotalCount = getTypeTotalCount();
            if(CodeKbn.statusFlg_ON.equals(displayMenuDto.getInventoryControlKbn())){
                if(typeTotalCount >= displayMenuDto.getInventory()){
                    return;
                }
            }

            Integer count = Integer.parseInt(drink_type_count.getText().toString());
            if(count == null){
                count = 0;
            }
            count++;
            drink_type_count.setText(String.valueOf(count));
            currentDrinkTypeCountList.put(drinkType.getDrinkTypyId(), count);
            menu_btnAdd.performClick();
        });

        drink_type_row_1_col_1_btnSub.setClickable(true);
        drink_type_row_1_col_1_btnSub.setOnClickListener(view -> {
            Integer count = Integer.parseInt(drink_type_count.getText().toString());
            if(count == null){
                count = 0;
            }
            count--;
            if(count < 0){
                return;
            }
            drink_type_count.setText(String.valueOf(count));
            currentDrinkTypeCountList.put(drinkType.getDrinkTypyId(), count);
            menu_btnSub.performClick();
        });

    }
}
