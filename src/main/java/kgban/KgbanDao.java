package kgban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.datasource.DataSourceUtils;


@Component
public class KgbanDao {
	@Autowired
	DataSource dataSource;
	

	public int getId() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int nextId = 1;
		// データベースとの接続を行う
		try {
			con = DataSourceUtils.getConnection(dataSource);

			// 最大のIDを取得
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT                    ");
			builder.append("  MAX(id) AS max_id ");
			builder.append("FROM                      ");
			builder.append("  message_board              ");

			// ステートメントクラスにSQL文を格納
			ps = con.prepareStatement(builder.toString());
			// SQLを実行して取得結果をリザルトセットに格納
			rs = ps.executeQuery();
			// 最大のIDに１を足す
			if (rs.next()) {
				nextId = rs.getInt("max_id");
				nextId += 1;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException re) {
				re.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 呼び出し元に取得結果を返却
		return nextId;
		
	}

	/**
	 * データベースにメッセージを保存する
	 *
	 * @param dto 投稿メッセージを格納したMessageDtoクラス
	 * @return 処理結果（件数）
	 */
	public void daoInsert(KgbanDto dto) {
		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		//

		try {
			// データベースとの接続を行う
			con = DataSourceUtils.getConnection(dataSource);

			StringBuilder builder = new StringBuilder();
			builder.append("INSERT INTO message_board ( ");
			builder.append("   id ");
			builder.append("  ,name ");
			builder.append("  ,message ");
			builder.append("  ,created_at ");
			builder.append(") VALUES ( ");
			builder.append("   ? ");
			builder.append("  ,? ");
			builder.append("  ,? ");
			builder.append("  ,? ");
			builder.append(") ");

			// ステートメントクラスにSQL文を格納
			ps = con.prepareStatement(builder.toString());
			// パラメータをセット("?"に値を入れる)

			ps.setInt(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getMessage());
			ps.setTimestamp(4, dto.getTime());


			// SQLを実行
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DBからデータを受け取る
	/**
	 * データベースからメッセージを取得する
	 *
	 * @return メッセージを格納したリスト
	 */
	public ArrayList<KgbanDto> select() {

		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		// リザルトセットクラスの宣言
		ResultSet rs = null;

		ArrayList<KgbanDto> list = new ArrayList<>();

		// データベースにアクセス
		try {
			// データベースとの接続を行う
			con = DataSourceUtils.getConnection(dataSource);

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT ");
			builder.append("   id ");
			builder.append("  ,name ");
			builder.append("  ,message ");
			builder.append("  ,created_at ");
			builder.append("FROM ");
			builder.append("  message_board ");
			builder.append("ORDER BY ");
			builder.append("  id DESC");

			// ステートメントクラスにSQL文を格納
			ps = con.prepareStatement(builder.toString());
			// SQLを実行して取得結果をリザルトセットに格納
			rs = ps.executeQuery();

			// リザルトセットから1レコードずつデータを取り出す
			while (rs.next()) {
				// 取得結果を格納するDtoをインスタンス化
				KgbanDto dto = new KgbanDto();
				// Dtoに取得結果を格納
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setMessage(rs.getString("message"));
				dto.setTime(rs.getTimestamp("created_at"));
				// Dtoに格納された1レコード分のデータをリストに詰める
				list.add(dto);
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
}