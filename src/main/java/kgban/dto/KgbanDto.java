package kgban.dto;

/**
 * データ登録用掲示板Dto.
 */
public class KgbanDto {

	/** ID */
	private int id;
	/** 投稿名 */
	private String name;
	/** 投稿時間 */
	private String time;
	/** 投稿メッセージ */
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
