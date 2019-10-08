package kgban.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 掲示板フォーム.
 */

public class KgbanForm {
	/** 投稿名 */
	private String name;
	/** 投稿メッセージ */
	private String message;

	@NotEmpty(message = "※入力必須")
	@Size(max = 12, message = "※全半角12文字以内")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty(message = "※入力必須")
	@Size(max = 25, message = "※全半角25文字以内")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
