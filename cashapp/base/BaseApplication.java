package com.afroci.cashapp.base;

import android.app.Application;
import android.text.TextUtils;

import com.afroci.cashapp.constant.Constants;
import com.afroci.cashapp.dto.CustomCourseMasterSubDto;
import com.afroci.cashapp.dto.CustomCurryOptionMasterDto;
import com.afroci.cashapp.dto.CustomDrinkTypyMasterDto;
import com.afroci.cashapp.dto.CustomLunchOptionMasterDto;
import com.afroci.cashapp.dto.CustomMenuMasterSubDto;
import com.afroci.cashapp.dto.CustomMenuToppingMasterDto;
import com.afroci.cashapp.dto.LunchReqDto;
import com.afroci.cashapp.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseApplication extends Application {

    private String assetNumber;

    private String deviceUuid;

    private String tokenId;

    private String staffId;

    private String staffName;

    private String merchantId;

    private String merchantName;

    /** 座席管理機能表示区分、0:表示、1:非表示 */
    private String kbn1;

    /** 注文機能表示区分、0:表示、1:非表示 */
    private String kbn2;

    /** 注文状態管理機能表示区分、0:表示、1:非表示 */
    private String kbn3;

    /** 会計機能表示区分、0:表示、1:非表示 */
    private String kbn4;

    /** 注文履歴機能表示区分、0:表示、1:非表示 */
    private String kbn5;

    /** 会計履歴機能表示区分、0:表示、1:非表示 */
    private String kbn6;

    /** 呼出管理機能表示区分、0:表示、1:非表示 */
    private String kbn7;

    private String merchantDeviceId;

    private String timeLock;

    private String menuPriceDisplayKbn;

    private String orderHistorySearchTimeLimit;

    private List<String> payMethodList;

    private String selfOrderDetailAutoPrint;

    private String autoSendPageToPaymentDetail;

    /** Runtime パラメータ */

    private boolean refreshBtnDisabled = false;

    private boolean refreshCurrentPage = false;

    // 選択中テーブルチェックインID
    private String activeTableCheckInId;

    // 選択中オーダーID
    private String activeOrderId;

    /** 選択中メニューリスト */
    private Map<String, Integer> menuList = new HashMap<String, Integer>();

    /** 選択中コースリスト */
    private Map<String, Integer>  courseList = new HashMap<String, Integer>();

    /** メニュー情報 */
    private Map<String, CustomMenuMasterSubDto> menuInfoList = new HashMap<String, CustomMenuMasterSubDto>();

    /** コース情報 */
    private Map<String, CustomCourseMasterSubDto> courseInfoList = new HashMap<String, CustomCourseMasterSubDto>();

    /** ランチオプション */
    private List<CustomLunchOptionMasterDto> lunchOptionList;
    private CustomLunchOptionMasterDto selectedLunchOption;
    private Map<String, List<LunchReqDto>> selectedLunchOptionList = new HashMap<String, List<LunchReqDto>>();

    /** カレーオプション */
    private List<CustomCurryOptionMasterDto> curryOptionList;
    private CustomCurryOptionMasterDto selectedCurryOption;

    /** トッピング */
    private Map<String, List<CustomMenuToppingMasterDto>> menuToppingList = new HashMap<String, List<CustomMenuToppingMasterDto>>();
    private List<CustomMenuToppingMasterDto> selectedMenuTopping = new ArrayList<CustomMenuToppingMasterDto>();
    private Map<String, CustomMenuMasterSubDto> toppingList = new HashMap<String, CustomMenuMasterSubDto>();

    /** カテゴリーお酒ドリンクタイプリスト */
    private Map<String, List<CustomDrinkTypyMasterDto>> categoryDrinkTypeList = new HashMap<String, List<CustomDrinkTypyMasterDto>>();
    private Map<String, Map<String, Integer>> selectedDrinkMenuDrinkTypeCountList = new LinkedHashMap<String, Map<String, Integer>>();

    /** メニュー */
    private Map<String, List<CustomMenuMasterSubDto>> categoryMenuList = new HashMap<String, List<CustomMenuMasterSubDto>>();

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void init(){
        this.assetNumber = SharedPreferencesUtil.get(this, Constants.assetNumber);
        this.deviceUuid = SharedPreferencesUtil.get(this, Constants.deviceUuid);
        this.tokenId = SharedPreferencesUtil.get(this, Constants.tokenId);
        this.staffId = SharedPreferencesUtil.get(this, Constants.staffId);
        this.staffName = SharedPreferencesUtil.get(this, Constants.staffName);
        this.merchantId = SharedPreferencesUtil.get(this, Constants.merchantId);
        this.merchantName = SharedPreferencesUtil.get(this, Constants.merchantName);
        this.kbn1 = SharedPreferencesUtil.get(this, Constants.kbn1);
        this.kbn2 = SharedPreferencesUtil.get(this, Constants.kbn2);
        this.kbn3 = SharedPreferencesUtil.get(this, Constants.kbn3);
        this.kbn4 = SharedPreferencesUtil.get(this, Constants.kbn4);
        this.kbn5 = SharedPreferencesUtil.get(this, Constants.kbn5);
        this.kbn6 = SharedPreferencesUtil.get(this, Constants.kbn6);
        this.kbn7 = SharedPreferencesUtil.get(this, Constants.kbn7);
        this.merchantDeviceId = SharedPreferencesUtil.get(this, Constants.merchantDeviceId);
        this.timeLock = SharedPreferencesUtil.get(this, Constants.timeLock);
        this.menuPriceDisplayKbn = SharedPreferencesUtil.get(this, Constants.menuPriceDisplayKbn);
        this.orderHistorySearchTimeLimit = SharedPreferencesUtil.get(this, Constants.orderHistorySearchTimeLimit);

        String payMethod = SharedPreferencesUtil.get(this, Constants.payMethod);
        if(!TextUtils.isEmpty(payMethod)){
            this.payMethodList = Arrays.asList(payMethod.split(","));
        } else {
            this.payMethodList = new ArrayList<String>();
        }

        this.selfOrderDetailAutoPrint = SharedPreferencesUtil.get(this, Constants.selfOrderDetailAutoPrint);
        this.autoSendPageToPaymentDetail = SharedPreferencesUtil.get(this, Constants.autoSendPageToPaymentDetail);
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getKbn1() {
        return kbn1;
    }

    public void setKbn1(String kbn1) {
        this.kbn1 = kbn1;
    }

    public String getKbn2() {
        return kbn2;
    }

    public void setKbn2(String kbn2) {
        this.kbn2 = kbn2;
    }

    public String getKbn3() {
        return kbn3;
    }

    public void setKbn3(String kbn3) {
        this.kbn3 = kbn3;
    }

    public String getKbn4() {
        return kbn4;
    }

    public void setKbn4(String kbn4) {
        this.kbn4 = kbn4;
    }

    public String getKbn5() {
        return kbn5;
    }

    public void setKbn5(String kbn5) {
        this.kbn5 = kbn5;
    }

    public String getKbn6() {
        return kbn6;
    }

    public void setKbn6(String kbn6) {
        this.kbn6 = kbn6;
    }

    public String getKbn7() {
        return kbn7;
    }

    public void setKbn7(String kbn7) {
        this.kbn7 = kbn7;
    }

    public String getTimeLock() {
        return timeLock;
    }

    public void setTimeLock(String timeLock) {
        this.timeLock = timeLock;
    }

    public String getActiveTableCheckInId() {
        return activeTableCheckInId;
    }

    public void setActiveTableCheckInId(String activeTableCheckInId) {
        this.activeTableCheckInId = activeTableCheckInId;
    }

    public Map<String, Integer> getMenuList() {
        return menuList;
    }

    public void setMenuList(Map<String, Integer> menuList) {
        this.menuList = menuList;
    }

    public Map<String, Integer> getCourseList() {
        return courseList;
    }

    public void setCourseList(Map<String, Integer> courseList) {
        this.courseList = courseList;
    }

    public String getMenuPriceDisplayKbn() {
        return menuPriceDisplayKbn;
    }

    public void setMenuPriceDisplayKbn(String menuPriceDisplayKbn) {
        this.menuPriceDisplayKbn = menuPriceDisplayKbn;
    }

    public Map<String, CustomMenuMasterSubDto> getMenuInfoList() {
        return menuInfoList;
    }

    public void setMenuInfoList(Map<String, CustomMenuMasterSubDto> menuInfoList) {
        this.menuInfoList = menuInfoList;
    }

    public Map<String, CustomCourseMasterSubDto> getCourseInfoList() {
        return courseInfoList;
    }

    public void setCourseInfoList(Map<String, CustomCourseMasterSubDto> courseInfoList) {
        this.courseInfoList = courseInfoList;
    }

    public boolean isRefreshCurrentPage() {
        return refreshCurrentPage;
    }

    public void setRefreshCurrentPage(boolean refreshCurrentPage) {
        this.refreshCurrentPage = refreshCurrentPage;
    }

    public boolean isRefreshBtnDisabled() {
        return refreshBtnDisabled;
    }

    public void setRefreshBtnDisabled(boolean refreshBtnDisabled) {
        this.refreshBtnDisabled = refreshBtnDisabled;
    }

    public String getOrderHistorySearchTimeLimit() {
        return orderHistorySearchTimeLimit;
    }

    public void setOrderHistorySearchTimeLimit(String orderHistorySearchTimeLimit) {
        this.orderHistorySearchTimeLimit = orderHistorySearchTimeLimit;
    }

    public String getActiveOrderId() {
        return activeOrderId;
    }

    public void setActiveOrderId(String activeOrderId) {
        this.activeOrderId = activeOrderId;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getMerchantDeviceId() {
        return merchantDeviceId;
    }

    public void setMerchantDeviceId(String merchantDeviceId) {
        this.merchantDeviceId = merchantDeviceId;
    }

    public List<String> getPayMethodList() {
        return payMethodList;
    }

    public void setPayMethodList(List<String> payMethodList) {
        this.payMethodList = payMethodList;
    }

    public String getSelfOrderDetailAutoPrint() {
        return selfOrderDetailAutoPrint;
    }

    public void setSelfOrderDetailAutoPrint(String selfOrderDetailAutoPrint) {
        this.selfOrderDetailAutoPrint = selfOrderDetailAutoPrint;
    }

    public String getAutoSendPageToPaymentDetail() {
        return autoSendPageToPaymentDetail;
    }

    public void setAutoSendPageToPaymentDetail(String autoSendPageToPaymentDetail) {
        this.autoSendPageToPaymentDetail = autoSendPageToPaymentDetail;
    }

    public List<CustomLunchOptionMasterDto> getLunchOptionList() {
        return lunchOptionList;
    }

    public void setLunchOptionList(List<CustomLunchOptionMasterDto> lunchOptionList) {
        this.lunchOptionList = lunchOptionList;
    }

    public CustomLunchOptionMasterDto getSelectedLunchOption() {
        return selectedLunchOption;
    }

    public void setSelectedLunchOption(CustomLunchOptionMasterDto selectedLunchOption) {
        this.selectedLunchOption = selectedLunchOption;
    }

    public Map<String, List<LunchReqDto>> getSelectedLunchOptionList() {
        return selectedLunchOptionList;
    }

    public void setSelectedLunchOptionList(Map<String, List<LunchReqDto>> selectedLunchOptionList) {
        this.selectedLunchOptionList = selectedLunchOptionList;
    }

    public List<CustomCurryOptionMasterDto> getCurryOptionList() {
        return curryOptionList;
    }

    public void setCurryOptionList(List<CustomCurryOptionMasterDto> curryOptionList) {
        this.curryOptionList = curryOptionList;
    }

    public CustomCurryOptionMasterDto getSelectedCurryOption() {
        return selectedCurryOption;
    }

    public void setSelectedCurryOption(CustomCurryOptionMasterDto selectedCurryOption) {
        this.selectedCurryOption = selectedCurryOption;
    }

    public Map<String, List<CustomMenuToppingMasterDto>> getMenuToppingList() {
        return menuToppingList;
    }

    public void setMenuToppingList(Map<String, List<CustomMenuToppingMasterDto>> menuToppingList) {
        this.menuToppingList = menuToppingList;
    }

    public List<CustomMenuToppingMasterDto> getSelectedMenuTopping() {
        return selectedMenuTopping;
    }

    public void setSelectedMenuTopping(List<CustomMenuToppingMasterDto> selectedMenuTopping) {
        this.selectedMenuTopping = selectedMenuTopping;
    }

    public Map<String, CustomMenuMasterSubDto> getToppingList() {
        return toppingList;
    }

    public void setToppingList(Map<String, CustomMenuMasterSubDto> toppingList) {
        this.toppingList = toppingList;
    }

    public Map<String, List<CustomDrinkTypyMasterDto>> getCategoryDrinkTypeList() {
        return categoryDrinkTypeList;
    }

    public void setCategoryDrinkTypeList(Map<String, List<CustomDrinkTypyMasterDto>> categoryDrinkTypeList) {
        this.categoryDrinkTypeList = categoryDrinkTypeList;
    }

    public Map<String, Map<String, Integer>> getSelectedDrinkMenuDrinkTypeCountList() {
        return selectedDrinkMenuDrinkTypeCountList;
    }

    public void setSelectedDrinkMenuDrinkTypeCountList(Map<String, Map<String, Integer>> selectedDrinkMenuDrinkTypeCountList) {
        this.selectedDrinkMenuDrinkTypeCountList = selectedDrinkMenuDrinkTypeCountList;
    }

    public Map<String, List<CustomMenuMasterSubDto>> getCategoryMenuList() {
        return categoryMenuList;
    }

    public void setCategoryMenuList(Map<String, List<CustomMenuMasterSubDto>> categoryMenuList) {
        this.categoryMenuList = categoryMenuList;
    }
}
