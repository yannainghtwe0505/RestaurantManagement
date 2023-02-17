package com.afroci.cashapp.constant;

/**
 * regexp
 */
public class PushMessage {

    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String MESSAGE_BODY = "MESSAGE_BODY";
    public static final String MESSAGE_TITLE = "MESSAGE_TITLE";

    // 顧客注文通知
    public static final String MESSAGE_TYPE_1 = "1";
    public static final String TITLE_1 = "新しい注文がございます。";
    public static final String BODY_1 = "注文番号:%s、詳細は注文履歴画面へご確認ください。";

    // オーダー状態変更通知(準備中＝＝＞完成)
    public static final String MESSAGE_TYPE_2 = "2";
    public static final String TITLE_2 = "完成注文がございます。";
    public static final String BODY_2 = "注文番号:%s、詳細は注文履歴画面へご確認ください。";

    // オーダー状態変更通知(準備中＝＝＞取り消し)
    public static final String MESSAGE_TYPE_3 = "3";
    public static final String TITLE_3 = "取り消し注文がございます。";
    public static final String BODY_3 = "注文番号:%s、詳細は注文履歴画面へご確認ください。";

    // オーダー詳細状態変更通知(準備中＝＝＞配送中)
    public static final String MESSAGE_TYPE_4 = "4";
    public static final String TITLE_4 = "配送中注文がございます。";
    public static final String BODY_4 = "注文番号:%s、詳細は注文履歴画面へご確認ください。";

    // オーダー詳細状態変更通知(配送中＝＝＞完成)
    public static final String MESSAGE_TYPE_5 = "5";
    public static final String TITLE_5 = "完成注文がございます。";
    public static final String BODY_5 = "注文番号:%s、詳細は注文履歴画面へご確認ください。";

    // オーダー詳細状態変更通知(取り消し)
    public static final String MESSAGE_TYPE_6 = "6";
    public static final String TITLE_6 = "取り消し注文がございます。";
    public static final String BODY_6 = "注文番号:%s、詳細は注文履歴画面へご確認ください。";

    // 呼び出し
    public static final String MESSAGE_TYPE_CALL = "7";
    public static final String TITLE_CALL = "呼出";
    public static final String BODY_CALL = "テーブル:%s(%s)";
}
