package kgban.dto;

/**
 * データ取得用掲示板dto.
 */
public class KgbanGetDto {

	/** ID */
	private int Id;
	/** 投稿名 */
	private String name;
	/** 投稿時間 */
	private String time;
	/** 投稿メッセージ */
	private String message;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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
