package kgban.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 掲示板フォーム.
 */

public class KgbanForm {
	/** 投稿名 */
	@NotEmpty(message = "{errors.validation.Required.message}")
	@Size(max = 12, message = "{errors.validation.MaxSize.message}")
	private String name;
	/** 投稿メッセージ */
	@NotEmpty(message = "{errors.validation.Required.message}")
	@Size(max = 25, message = "{errors.validation.MaxSize.message}")
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
