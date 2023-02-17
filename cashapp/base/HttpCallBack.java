package com.afroci.cashapp.base;

import org.json.JSONObject;

public interface HttpCallBack {
    void onRequestComplete(Object response, String reqType);
    void showLoading();
    void hideLoading();
    void timeout();
    void onErrorMsg(String msg);
}
