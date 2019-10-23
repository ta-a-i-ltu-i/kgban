package kgban.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kgban.dto.KgbanDto;

import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * 掲示板dao.
 */
@Repository
public class KgbanDao {

	@Autowired
	private DataSource dataSource;

	/**
	 * DBから投稿内容を取得.
	 * 
	 * @return 投稿内容を格納したリスト
	 * @throws SQLException データベースアクセスエラー
	 */
	public ArrayList<KgbanDto> selectUserMessages() throws SQLException {

		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		// リザルトセットクラスの宣言
		ResultSet rs = null;

		ArrayList<KgbanDto> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		// データベースとの接続を行う
		con = DataSourceUtils.getConnection(dataSource);

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT           ");
		builder.append("   id            ");
		builder.append("  ,name          ");
		builder.append("  ,message       ");
		builder.append("  ,created_at    ");
		builder.append("FROM             ");
		builder.append("  message_board  ");
		builder.append("WHERE            ");
		builder.append("  is_invalid = 0 ");
		builder.append("ORDER BY         ");
		builder.append("  id DESC        ");

		// ステートメントクラスにSQL文を格納
		ps = con.prepareStatement(builder.toString());
		// SQLを実行して取得結果をリザルトセットに格納
		rs = ps.executeQuery();

		// リザルトセットから1レコードずつデータを取り出す
		while (rs.next()) {

			// 取得結果を格納するDtoをインスタンス化
			KgbanDto kgbanDto = new KgbanDto();
			// Dtoに取得結果を格納
			kgbanDto.setId(rs.getInt("id"));
			kgbanDto.setName(rs.getString("name"));
			kgbanDto.setMessage(rs.getString("message"));
			kgbanDto.setTime(sdf.format(rs.getTimestamp("created_at")));

			// Dtoに格納された1レコード分のデータをリストに詰める
			list.add(kgbanDto);
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
	 * DBから最大のIDを取得.
	 * 
	 * @return 最大ID
	 * @throws SQLException データベースアクセスエラー
	 */
	public int getMaxId() throws SQLException {

		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		// リザルトセットクラスの宣言
		ResultSet rs = null;

		int maxId = 0;
		// データベースとの接続を行う
		con = DataSourceUtils.getConnection(dataSource);

		// 最大のIDを取得
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT                     ");
		builder.append("  NVL(MAX(id),0) AS max_id ");
		builder.append("FROM                       ");
		builder.append("  message_board            ");

		// ステートメントクラスにSQL文を格納
		ps = con.prepareStatement(builder.toString());
		// SQLを実行して取得結果をリザルトセットに格納
		rs = ps.executeQuery();

		if (rs.next()) {
			maxId = rs.getInt("max_id");
		}

		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		// 呼び出し元に取得結果を返却
		return maxId;
	}

	/**
	 * DBにメッセージを登録.
	 * 
	 * @param kgbanDto 投稿メッセージを格納したDto
	 * @throws SQLException データベースアクセスエラー
	 */
	public void insertUserMessage(KgbanDto kgbanDto) throws SQLException {

		// コネクションクラスの宣言
		Connection con = null;
		// ステートメントクラスの宣言
		PreparedStatement ps = null;
		//
		// データベースとの接続を行う
		con = DataSourceUtils.getConnection(dataSource);

		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO message_board ( ");
		builder.append("   id                       ");
		builder.append("  ,name                     ");
		builder.append("  ,message                  ");
		builder.append("  ,created_at               ");
		builder.append(") VALUES (                  ");
		builder.append("   ?                        ");
		builder.append("  ,?                        ");
		builder.append("  ,?                        ");
		builder.append("  ,?                        ");
		builder.append(" )                          ");

		// ステートメントクラスにSQL文を格納
		ps = con.prepareStatement(builder.toString());

		// パラメータをセット("?"に値を入れる)
		ps.setInt(1, kgbanDto.getId());
		ps.setString(2, kgbanDto.getName());
		ps.setString(3, kgbanDto.getMessage());
		ps.setString(4, kgbanDto.getTime());

		// SQLを実行
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}

	}

	/**
	 * 画面から送られてきたIDと同じIDの投稿が１件のみか確認する.
	 * 
	 * @param id 画面から送られてきたID
	 * @return 送られてきたIDと同じIDの投稿が１件かどうかの真偽
	 * @throws SQLException データベースアクセスエラー
	 */
	public boolean countId(int id) throws SQLException {

		boolean existsId = false;

		// コネクションクラスの宣言し、データベースとの接続を行う
		Connection con = DataSourceUtils.getConnection(dataSource);

		// 画面から送られてきたIDと同じIDの数を取得
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT                     ");
		builder.append("  COUNT( id )              ");
		builder.append("FROM                       ");
		builder.append("  message_board            ");
		builder.append("WHERE                      ");
		builder.append("  id = ?                   ");

		// ステートメントクラスの宣言し、SQL文を格納
		PreparedStatement ps = con.prepareStatement(builder.toString());
		ps.setInt(1, id);
		// SQLを実行して取得結果をリザルトセットに格納
		ResultSet rs = ps.executeQuery();

		// 取得結果が１件ならtrueを返す
		if (rs.next() && rs.getInt("COUNT(id)") == 1) {
			existsId = true;
		}

		if (ps != null) {
			ps.close();
		}
		// 呼び出し元に取得結果を返却
		return existsId;

	}

	/**
	 * 画面から送られてきたIDの投稿の無効フラグの値を取得.
	 * 
	 * @param id 画面から送られてきたID
	 * @return 無効フラグの値の真偽
	 * @throws SQLException データベースアクセスエラー
	 */
	public boolean selectIsInvalid(int id) throws SQLException {

		boolean isInvalid = false;

		// コネクションクラスの宣言し、データベースとの接続を行う
		Connection con = DataSourceUtils.getConnection(dataSource);

		// 無効フラグの値を取得
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT                     ");
		builder.append("  is_invalid               ");
		builder.append("FROM                       ");
		builder.append("  message_board            ");
		builder.append("WHERE                      ");
		builder.append("  id = ?                   ");

		// ステートメントクラスの宣言し、にSQL文を格納
		PreparedStatement ps = con.prepareStatement(builder.toString());
		ps.setInt(1, id);
		// リザルトセットクラスの宣言をし、SQLを実行して取得結果をリザルトセットに格納
		ResultSet rs = ps.executeQuery();

		// is_invalidが0ならtrueを返す
		if (rs.next() && rs.getInt("is_invalid") == 0) {

			isInvalid = true;
		}

		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		// 呼び出し元に取得結果を返却
		return isInvalid;

	}

	/**
	 * 無効フラグの値を0から1に変える.
	 * 
	 * @param 画面から送られてきたID
	 * @throws SQLException データべースアクセスエラー
	 */
	public void updateIsInvalid(int id) throws SQLException {

		// コネクションクラスの宣言し、データベースとの接続を行う
		Connection con = DataSourceUtils.getConnection(dataSource);

		// 削除するmessageのIDを取得
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE                    ");
		builder.append(" message_board            ");
		builder.append("SET                       ");
		builder.append(" is_invalid = 1           ");
		builder.append("WHERE                     ");
		builder.append(" is_invalid = 0           ");
		builder.append("AND                       ");
		builder.append(" id = ?                   ");

		// ステートメントクラスの宣言し、にSQL文を格納
		PreparedStatement ps = con.prepareStatement(builder.toString());
		ps.setInt(1, id);
		// SQLを実行
		ps.executeUpdate();

		if (ps != null) {
			ps.close();
		}

	}

}