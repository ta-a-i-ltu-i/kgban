package kgban;

import org.springframework.stereotype.Component;


@Component
public class KgbanDao {
	

	public int getId() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("OK");
		return 0;
		
		
	}

	public void daoInsert(KgbanDto dto) {
		// TODO 自動生成されたメソッド・スタブ
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT                    ");
		builder.append("  MAX(id)+1 AS max_id ");
		builder.append("FROM                      ");
		builder.append("  message_board              ");
		
	}

}
