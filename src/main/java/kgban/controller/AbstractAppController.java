package kgban.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kgban.contract.AppConst;

/**
 * 同期処理例外終了メッセージを設定します.
 *
 * @param attributes リダイレクトトリビュート
 * @param message    終了メッセージ
 */
public class AbstractAppController {

	protected void setCompleteMessageFailure(RedirectAttributes attributes, String message) {
		attributes.addFlashAttribute(AppConst.APP_COMPLETE_MESSAGE_ID_FAILURE, message);
	}

}
