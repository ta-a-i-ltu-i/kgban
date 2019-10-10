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

	@NotEmpty(message = "{errors.validation.Required.message}")
	@Size(max = 12, message = "{errors.validation.MaxSize.message}")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty(message = "{errors.validation.Required.message}")
	@Size(max = 25, message = "{errors.validation.MaxSize.message}")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
