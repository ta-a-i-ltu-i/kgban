package kgban.dto;

import java.sql.Timestamp;

public class KgbanPostDto {

	/**
	 * データ登録用掲示板Dto.
	 */

	/** ID */
	private int Id;
	/** 投稿名 */
	private String name;
	/** 投稿時間 */
	private Timestamp time;
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
