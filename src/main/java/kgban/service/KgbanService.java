package kgban.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kgban.dao.KgbanDao;
import kgban.dto.KgbanDto;
import kgban.form.KgbanForm;

/**
 * 掲示板サービス.
 */
@Service
public class KgbanService {

	@Autowired
	private KgbanDao dao;

	/**
	 * 過去の投稿を取得.
	 * 
	 * @return 過去の投稿を格納したリスト
	 * @throws SQLException データベースアクセスエラー
	 */
	public ArrayList<KgbanDto> getUserMessages() throws SQLException {

		// 投稿内容を格納したリストを受け取り、呼び出し元へ返す
		return dao.selectUserMessages();
	}

	/**
	 * 投稿を登録.
	 * 
	 * @param kgbanForm 投稿されたnameとmessage
	 * @throws SQLException データベースアクセスエラー
	 */
	@Transactional
	public void setUserMessage(KgbanForm kgbanForm) throws SQLException {

		// 最大IDを取得する
		int maxId = dao.getMaxId();

		// KgbanDtoに登録する内容を格納する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		KgbanDto dto = new KgbanDto();
		dto.setId(maxId + 1);
		dto.setName(kgbanForm.getName());
		dto.setMessage(kgbanForm.getMessage());
		dto.setTime(sdf.format(new Timestamp(System.currentTimeMillis())));

		dao.insertUserMessage(dto);
	}

	/**
	 * 画面から送られてきたIDと同じIDの投稿が１件のみか確認する.
	 * 
	 * @param id 画面から送られてきたID
	 * @return 画面から送られてきたIDと同じIDの投稿が１件のみかの真偽
	 * @throws SQLException データベースアクセスエラー
	 */
	public int countUserMessage(int id) throws SQLException {

		// 画面から送られてきたIDが使われているか確認するメソッド
		return dao.getCountId(id);
	}

	/**
	 * 画面から送られてきたIDの投稿が既に無効になっていないか確認する.
	 * 
	 * @param id 画面から送られてきたID
	 * @return 画面から送られてきたIDの投稿が既に削除されていないかの真偽
	 * @throws SQLException データベースアクセスエラー
	 */
	public boolean checkInvalidMessage(int id) throws SQLException {

		// 既に無効になっていないか確認するメソッド
		return dao.selectIsInvalid(id);
	}

	/**
	 * 削除したい投稿の無効フラグを0→1にする.
	 * 
	 * @param id 画面から送られてきたID
	 * @throws SQLException データベースアクセスエラー
	 */
	public void deleteUserMessage(int id) throws SQLException {

		// 送られてきたIDの投稿の無効フラグを0→1に変えるメソッド
		dao.updateInvalidMessage(id);
	}

}
