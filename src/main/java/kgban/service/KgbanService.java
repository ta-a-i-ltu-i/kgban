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
	 * 削除したい投稿のIDと同じIDの投稿の数を取得.
	 * 
	 * @param id 削除したい投稿のID
	 * @return 削除したい投稿のIDと同じIDの投稿の数
	 * @throws SQLException データベースアクセスエラー
	 */
	// IDが使われているか確認するメソッド
	public int getCountId(int id) throws SQLException {

		return dao.countId(id);
	}

	/**
	 * 削除したい投稿の無効フラグの値を取得.
	 * 
	 * @param id 削除したい投稿のID
	 * @return 無効フラグの値
	 * @throws SQLException データベースアクセスエラー
	 */
	// すでに削除されていないか確認するメソッド
	public int getIsInvald(int id) throws SQLException {

		return dao.selectIsInvalid(id);
	}

	/**
	 * 削除したい投稿を無効にする.
	 * 
	 * @param id 削除したい投稿のID
	 * @throws SQLException データベースアクセスエラー
	 */
	public void postDelete(int id) throws SQLException {

		dao.UpdateDelete(id);
	}

}
