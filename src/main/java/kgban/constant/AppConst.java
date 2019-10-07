package kgban.constant;

/**
 * アプリケーション共通定数定義.
 */
public class AppConst {

  /** 空文字. */
  public static final String EMPTY = "";

  /** 処理完了メッセージID 正常. */
  public static final String APP_COMPLETE_MESSAGE_ID_SUCCESS = "appCompleteMessageSuccess";
  /** 処理完了メッセージID 警告. */
  public static final String APP_COMPLETE_MESSAGE_ID_WARNING = "appCompleteMessageWarning";
  /** 処理完了メッセージID 例外. */
  public static final String APP_COMPLETE_MESSAGE_ID_FAILURE = "appCompleteMessageFailure";

  /* ---------------------------------------------------- */
  /* ユーザ情報関連 */
  /* ---------------------------------------------------- */
  /** ロール名_一般ユーザ. */
  public static final String ROLE_NAME_USER = "USER";
  /** ロール名_管理者ユーザ. */
  public static final String ROLE_NAME_ADMIN = "ADMIN";
  /** ロール名_全て（検索用）. */
  public static final String ROLE_NAME_ALL = "ALL";

  /** 利用可/不可_利用可. **/
  public static final String NON_VALID_ON = "on";
  /** 無効フラグ_OFF（有効）. **/
  public static final String INVALID_OFF = "0";
  /** 無効フラグ_ON（無効）. **/
  public static final String INVALID_ON = "1";


  /* ---------------------------------------------------- */
  /* フォーマット */
  /* ---------------------------------------------------- */

  /** 日付フォーマット(更新日時、登録日時を表示する際に使用する標準的なパターン). */
  public static final String DATE_PATTERN_STD = "yyyy/MM/dd HH:mm:ss";
  
  /** null時に置換する文字.*/
  public static final String DRAWING_IF_NULL = "-";
  /** 文字数オーバーのときに超過部分を置換する文字.*/
  public static final String DRAWING_OVER_CHAR = "...";

}
