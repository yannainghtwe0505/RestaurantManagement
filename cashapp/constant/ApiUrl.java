package com.afroci.cashapp.constant;

public class ApiUrl {

    // kaisha
     public static final String register = "http://180.222.186.17:9081/AfrociCRMSystem/open/api/device/register";
     private static final String serverUrl = "http://180.222.186.17:9083/CashRegisterSystem";

    // 本番
//    public static final String register = "https://admin.smapay.jp/AfrociCRMSystem/open/api/device/register";
//    private static final String serverUrl = "https://api.smapay.jp/CashRegisterSystem";

    /**
     * ログイン
     */
    public static final String login = serverUrl + "/api/base/getcashtoken";

    /**
     * 座席管理p
     */
    // 座席一覧取得
    public static final String TABLE_SEARCH = serverUrl + "/api/table/search";
    // チェックイン
    public static final String TABLE_CHECK_IN = serverUrl + "/api/table/checkin";
    // チェックアウト
    public static final String TABLE_CHECK_OUT = serverUrl + "/api/table/checkout";
    // チェックイン情報取得
    public static final String TABLE_CHECK_IN_INFO = serverUrl + "/api/table/checkininfo";
    // チェックイン情報更新
    public static final String TABLE_CHECK_IN_INFO_UPDATE = serverUrl + "/api/table/checkininfoupdate";

    /**
     * 注文
     */
    // カテゴリー取得、メニュー取得
    public static final String ORDER_MENU_SEARCH = serverUrl + "/api/order/menusearch";
    // 注文コミット
    public static final String ORDER_COMMIT = serverUrl + "/api/order/commit";

    /**
     * キッチン
     */
    // 注文一覧取得
    public static final String KITCHEN_ORDER_SEARCH = serverUrl + "/api/kitchen/ordersearch";
    // 注文状態更新
    public static final String KITCHEN_ORDER_STATUS_UPDATE = serverUrl + "/api/kitchen/orderstatusupdate";

    /**
     * 履歴
     */
    // 注文一覧取得
    public static final String HISTORY_ORDER_SEARCH = serverUrl + "/api/history/ordersearch";
    // 注文詳細取得
    public static final String HISTORY_ORDER_DETAIL = serverUrl + "/api/history/orderdetail";

    /**
     * 会計
     */
    // 会計
    public static final String PAYMENT_PAY = serverUrl + "/api/payment/pay";
    // 会計一覧取得
    public static final String PAYMENT_SEARCH = serverUrl + "/api/payment/search";
    // 詳細
    public static final String PAYMENT_DETAIL = serverUrl + "/api/payment/detail";
    // 支払方法変更
    public static final String PAYMENT_UPDATE_PAY_METHOD = serverUrl + "/api/payment/updatepaymethod";

    /**
     * 取引履歴
     */
    // 会計履歴一覧
    public static final String TRANSACTION_HISTORY_SEARCH = serverUrl + "/api/transaction/search";
    // 取引注文一覧
    public static final String TRANSACTION_HISTORY_ORDER_LIST = serverUrl + "/api/transaction/orderlist";

    /**
     * 印刷
     */
    // 領収証印刷
    public static final String PRINT_RECEIPT = serverUrl + "/api/print/receipt";
    // 注文明細印刷
    public static final String PRINT_DETAIL = serverUrl + "/api/print/detail";
    // オーダー印刷
    public static final String PRINT_ORDER = serverUrl + "/api/print/order";

    /**
     * 呼出管理
     */
    // 呼出一覧
    public static final String NOTIFICATION_CALL_SEARCH = serverUrl + "/api/notification/callsearch";
    // 呼出ステータス更新
    public static final String NOTIFICATION_CALL_STATUS_UPDATE = serverUrl + "/api/notification/callstatusupdate";
}
