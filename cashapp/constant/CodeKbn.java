package com.afroci.cashapp.constant;

import java.util.HashMap;
import java.util.Map;

public class CodeKbn {

    /**
     * 共通区分
     */
    public static final String comKbn_NO = "0";
    public static final String comKbn_YES = "1";
    public static Map<String, String> comKbn_YES_NO_map = new HashMap<String, String>();
    static {
        comKbn_YES_NO_map.put(comKbn_NO, "No");
        comKbn_YES_NO_map.put(comKbn_YES, "Yes");
    }

    /**
     * 公開状態、0:公開、1:非公開
     */
    public static final String displayKbn_SHOW = "0";
    public static final String displayKbn_HIDE = "1";

    /**
     * 完売区分、0:あり、1:完売
     */
    public static final String soldOutKbn_N = "0";
    public static final String soldOutKbn_Y = "1";

    /**
     * 有無区分、0:なし、1:あり
     */
    public static final String existKbn_N = "0";
    public static final String existKbn_Y = "1";
    public static Map<String, String> existKbn_map = new HashMap<String, String>();
    static {
        existKbn_map.put(existKbn_N, "なし");
        existKbn_map.put(existKbn_Y, "あり");
    }

    /**
     * 利用区分
     */
    public static final String useKbn_USING = "0";
    public static final String useKbn_STOPPED = "1";
    public static Map<String, String> useKbn_map = new HashMap<String, String>();
    static {
        useKbn_map.put(useKbn_USING, "利用中");
        useKbn_map.put(useKbn_STOPPED, "利用停止");
    }

    /**
     * メニュー区分
     */
    public static final String menuKbn_MENU = "0";
    public static final String menuKbn_COURSE = "1";
    public static Map<String, String> menuKbn_map = new HashMap<String, String>();
    static {
        menuKbn_map.put(menuKbn_MENU, "メニュー");
        menuKbn_map.put(menuKbn_COURSE, "コース");
    }

    /**
     * メニュー価格表示区分、0:税抜、1:税込、2:両方
     */
    public static final String menuPriceDisplayKbn_TAX_OUT = "0";
    public static final String menuPriceDisplayKbn_TAX_IN = "1";
    // public static final String menuPriceDisplayKbn_ALL = "2"; TODO
    public static Map<String, String> menuPriceDisplayKbn_map = new HashMap<String, String>();
    static {
        menuPriceDisplayKbn_map.put(menuPriceDisplayKbn_TAX_OUT, "税抜");
        menuPriceDisplayKbn_map.put(menuPriceDisplayKbn_TAX_IN, "税込");
        // menuPriceDisplayKbn_map.put(menuPriceDisplayKbn_ALL, "両方");
    }

    /**
     * レシートテンプレートタイプ、0:小計、1:領収証
     */
    public static final String receiptTemplateType_0 = "0";
    public static final String receiptTemplateType_1 = "1";
    public static Map<String, String> receiptTemplateType_map = new HashMap<String, String>();
    static {
        receiptTemplateType_map.put(receiptTemplateType_0, "小計");
        receiptTemplateType_map.put(receiptTemplateType_1, "領収証");
    }

    /**
     * レシートテンプレート詳細行タイプ、0:文字、1:写真、2:QR
     */
    public static final String rowType_STRING = "0";
    public static final String rowType_IMAGE = "1";
    public static final String rowType_QR = "2";
    public static Map<String, String> rowType_map = new HashMap<String, String>();
    static {
        rowType_map.put(rowType_STRING, "文字");
        rowType_map.put(rowType_IMAGE, "写真");
        rowType_map.put(rowType_QR, "QR");
    }

    /**
     * レシートテンプレート詳細行
     *
     * アラインメント、0:左 , 1:センター, 2:右
     */
    public static final String alignment_LEFT = "0";
    public static final String alignment_CENTER = "1";
    public static final String alignment_RIGHT = "2";
    public static Map<String, String> alignment_map = new HashMap<String, String>();
    static {
        alignment_map.put(alignment_LEFT, "左");
        alignment_map.put(alignment_CENTER, "センター");
        alignment_map.put(alignment_RIGHT, "右");
    }

    /**
     * 0:入席中、1:未入席
     */
    public static final String tableFreeKbn_IN = "0";
    public static final String tableFreeKbn_OUT = "1";
    public static Map<String, String> tableFreeKbn_map = new HashMap<String, String>();
    static {
        tableFreeKbn_map.put(tableFreeKbn_IN, "入席中");
        tableFreeKbn_map.put(tableFreeKbn_OUT, "未入席");
    }

    /**
     * チェックイン状態 0:チェックイン、1:チェックアウト
     */
    public static final String checkInStatus_IN = "0";
    public static final String checkInStatus_OUT = "1";
    public static Map<String, String> checkInStatus_map = new HashMap<String, String>();
    static {
        checkInStatus_map.put(checkInStatus_IN, "チェックイン");
        checkInStatus_map.put(checkInStatus_OUT, "チェックアウト");
    }

    /**
     * 予約区分、0:無、1:有
     */
    public static final String reservationKbn_N = "0";
    public static final String reservationKbn_Y = "1";
    public static Map<String, String> reservationKbn_map = new HashMap<String, String>();
    static {
        reservationKbn_map.put(reservationKbn_N, "無");
        reservationKbn_map.put(reservationKbn_Y, "有");
    }

    /**
     * オーダー状態、0:準備中、1:配送中、2:完成、9:取り消し
     */
    public static final String orderStatus_PREPARING = "0";
    public static final String orderStatus_IN_DELIVERY = "1";
    public static final String orderStatus_COMPLETE = "2";
    public static final String orderStatus_CANCEL = "9";
    public static Map<String, String> orderStatus_map = new HashMap<String, String>();
    static {
        orderStatus_map.put(orderStatus_PREPARING, "準備中");
        orderStatus_map.put(orderStatus_IN_DELIVERY, "配送中");
        orderStatus_map.put(orderStatus_COMPLETE, "完成");
        orderStatus_map.put(orderStatus_CANCEL, "取り消し");
    }

    /**
     * 支払状態、0:未支払、1:支払済み
     */
    public static final String payStatus_N = "0";
    public static final String payStatus_Y = "1";
    public static Map<String, String> payStatus_map = new HashMap<String, String>();
    static {
        payStatus_map.put(payStatus_N, "未支払");
        payStatus_map.put(payStatus_Y, "支払済み");
    }

    /**
     * 支払方法、0:現金、1:クレジットカード、2:その他
     */
    public static final String payMethod_CASH = "0";
    public static final String payMethod_CARD = "1";
    public static final String payMethod_OTHER = "2";
    public static Map<String, String> payMethod_map = new HashMap<String, String>();
    static {
        payMethod_map.put(payMethod_CASH, "現金");
        payMethod_map.put(payMethod_CARD, "クレジットカード");
        payMethod_map.put(payMethod_OTHER, "その他");
    }

    /**
     * サブ表示区分、0:メニュー、1:コース、2:カテゴリー
     */
    public static final String subDisplayKbn_MENU = "0";
    public static final String subDisplayKbn_COURSE = "1";
    public static final String subDisplayKbn_CATEGORY = "2";
    public static Map<String, String> subDisplayKbn_map = new HashMap<String, String>();
    static {
        subDisplayKbn_map.put(subDisplayKbn_MENU, "メニュー");
        subDisplayKbn_map.put(subDisplayKbn_COURSE, "コース");
        subDisplayKbn_map.put(subDisplayKbn_CATEGORY, "カテゴリー");
    }

    /**
     * FLG、0:ON、1:OFF
     */
    public static final String statusFlg_ON = "0";
    public static final String statusFlg_OFF = "1";
    public static Map<String, String> statusFlg_map = new HashMap<String, String>();
    static {
        statusFlg_map.put(statusFlg_ON, "ON");
        statusFlg_map.put(statusFlg_OFF, "OFF");
    }

}
