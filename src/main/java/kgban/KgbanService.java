package kgban;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KgbanService {
	@Autowired
	KgbanDao dao;

	public void setmessage(KgbanForm kf) {
		//dtoに値をセットしてdaoメソッドを呼ぶ
		//ここではmaxIDと現在時刻もdtoに入れないといけないためその二つも取得する
		int maxId = dao.getId();

		KgbanDto dto = new KgbanDto();
		dto.setId(maxId);
		dto.setName(kf.getName());
		dto.setMessage(kf.getMessage());
		dto.setTime(new Timestamp(System.currentTimeMillis()));
  		
		dao.daoInsert(dto);
		
		
	}
	
	public ArrayList<KgbanDto> getMessage(){
		ArrayList<KgbanDto> list = dao.select();		
		return list;
	}

}
