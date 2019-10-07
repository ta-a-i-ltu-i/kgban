package kgban.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kgban.dao.KgbanDao;
import kgban.dto.KgbanGetDto;
import kgban.dto.KgbanPostDto;
import kgban.form.KgbanForm;

/**
 * 掲示板サービス.
 */
@Service
public class KgbanService {
	@Autowired
	KgbanDao dao;

	/**
	 * 過去の投稿を取得するメソッド.
	 * 
	 * @return 過去の投稿を格納したリスト
	 */
	  @Transactional
	public ArrayList<KgbanGetDto> getMessages() throws SQLException{
		ArrayList<KgbanGetDto> list = dao.selectMessages();
		return list;
	}

	/**
	 * 投稿を登録するメソッド.
	 * 
	 * @param kf 投稿されたnameとmessage
	 */
	  @Transactional
	public void setMessage(KgbanForm kgbanForm) throws SQLException {
		int maxId = dao.getId();
		

		KgbanPostDto dto = new KgbanPostDto();
		dto.setId(maxId + 1);
		dto.setName(kgbanForm.getName());
		dto.setMessage(kgbanForm.getMessage());
		dto.setTime(new Timestamp(System.currentTimeMillis()));
		
		System.out.println(dto.getTime());

		dao.insertMessage(dto);

	}

}
