package kgban.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kgban.dto.KgbanGetDto;
import kgban.dto.KgbanPostDto;

import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * 掲示板dao
 */
@Component
public class KgbanDao {
	/**
	 * データベースからメッセージを取得.
	 *
	 * @return メッセージを格納したリスト
	 */

	@Autowired
	DataSource dataSource;

	public ArrayList<KgbanGetDto> selectMessages() throws SQLException {

		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		// リザルトセットクラスの宣言
		ResultSet rs = null;

		ArrayList<KgbanGetDto> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		// データベースにアクセス

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
			KgbanGetDto dto = new KgbanGetDto();
			// Dtoに取得結果を格納
			dto.setId(rs.getInt("id"));
			dto.setName(rs.getString("name"));
			dto.setMessage(rs.getString("message"));
			dto.setTime(sdf.format(rs.getTimestamp("created_at")));
			
			// Dtoに格納された1レコード分のデータをリストに詰める
			list.add(dto);
		}
		
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		return list;
	}

	/**
	 * 最大のIDを取得.
	 * 
	 * @return 最大ID
	 */
	public int getId()throws SQLException  {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int getMaxId = 0;
		// データベースとの接続を行う
			con = DataSourceUtils.getConnection(dataSource);

			// 最大のIDを取得
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT                    ");
			builder.append("  NVL(MAX(id),0) AS max_id ");
			builder.append("FROM                      ");
			builder.append("  message_board              ");

			// ステートメントクラスにSQL文を格納
			ps = con.prepareStatement(builder.toString());
			// SQLを実行して取得結果をリザルトセットに格納
			rs = ps.executeQuery();
			// 最大のIDに１を足す
			if (rs.next()) {
				getMaxId = rs.getInt("max_id");

			}

				con.rollback();
			
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}

				// 呼び出し元に取得結果を返却
				return getMaxId;
			
		}

	

	/**
	 * データベースにメッセージを登録.
	 *
	 * @param dto 投稿メッセージを格納したDto
	 */
	public void insertMessage(KgbanPostDto dto)throws SQLException  {
		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		//
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

		
				if (ps != null) {
					ps.close();
				}
			
	}

}