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
	private KgbanDao dao;

	/**
	 * 過去の投稿を取得.
	 * 
	 * @return 過去の投稿を格納したリスト
	 */
	
	//トランザクション制御
	@Transactional
	public ArrayList<KgbanGetDto> registerContents() throws SQLException {
		
		//投稿内容を格納したリストを受け取る
		ArrayList<KgbanGetDto> list = dao.selectContents();
		
		return list;
	}

	/**
	 * 投稿を登録.
	 * 
	 * @param kgbanForm 投稿されたnameとmessage
	 */
	@Transactional
	public void loadContent(KgbanForm kgbanForm) throws SQLException {
		
		//最大IDを取得する
		int maxId = dao.loadMaxId();

		//KgbanPostDtoに登録する内容を格納する
		KgbanPostDto dto = new KgbanPostDto();
		dto.setId(maxId + 1);
		dto.setName(kgbanForm.getName());
		dto.setMessage(kgbanForm.getMessage());
		dto.setTime(new Timestamp(System.currentTimeMillis()));

		dao.insertContent(dto);

	}

}
