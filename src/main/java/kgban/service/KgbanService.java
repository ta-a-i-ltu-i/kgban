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
	 */

	public ArrayList<KgbanDto> getUserMessages() {

		ArrayList<KgbanDto> list = new ArrayList<>();

		try {

			// 投稿内容を格納したリストを受け取る
			list = dao.selectUserMessages();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 投稿を登録.
	 * 
	 * @param kgbanForm 投稿されたnameとmessage
	 */
	@Transactional
	public void setUserMessage(KgbanForm kgbanForm) {

		try {

			// 最大IDを取得する
			int maxId = dao.getMaxId();

			// KgbanPostDtoに登録する内容を格納する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			KgbanDto dto = new KgbanDto();
			dto.setId(maxId + 1);
			dto.setName(kgbanForm.getName());
			dto.setMessage(kgbanForm.getMessage());
			dto.setTime(sdf.format(new Timestamp(System.currentTimeMillis())));

			dao.insertUserMessage(dto);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
