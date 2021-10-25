package step3_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {

	private BoardDao() {}
	private static BoardDao instance = new BoardDao();
	public static BoardDao getInstance() {
		return instance;
	}
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public Connection getConnection() {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String url     = "jdbc:mysql://localhost:3306/STEP3_BOARD_EX?serverTimezone=UTC";
			String user    = "root";
			String passwd  = "1234";
			conn = DriverManager.getConnection(url, user, passwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	public void insertBoard(BoardDto boardDto) {
		
		try {
			
			conn = getConnection();
			String sql = "INSERT INTO BOARD(WRITER,EMAIL,SUBJECT,PASSWORD,REG_DATE,READ_COUNT,CONTENT)";
			       sql += "VALUES(?, ?, ?, ?, NOW(), 0, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDto.getWriter());
			pstmt.setString(2, boardDto.getEmail());
			pstmt.setString(3, boardDto.getSubject());
			pstmt.setString(4, boardDto.getPassword());
			pstmt.setString(5, boardDto.getContent());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {e.printStackTrace();}}
		}
		
	}
	
	public ArrayList<BoardDto> getAllBoard() {
		
		ArrayList<BoardDto> boardList = new ArrayList<BoardDto>();
		BoardDto boardDto = null;
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boardDto = new BoardDto();
				
				boardDto.setNum(rs.getInt("NUM"));					// rs.getInt(1);	
				boardDto.setWriter(rs.getString("WRITER"));			// rs.getString(2);
				boardDto.setEmail(rs.getString("EMAIL"));           // rs.getString(3);	
				boardDto.setSubject(rs.getString("SUBJECT"));       // rs.getString(4);
				boardDto.setPassword(rs.getString("PASSWORD"));		// rs.getString(5);
				boardDto.setRegDate(rs.getDate("REG_DATE"));        // rs.getDate(6);
				boardDto.setReadCount(rs.getInt("READ_COUNT"));		// rs.getInt(7);
				boardDto.setContent(rs.getString("CONTENT"));       // rs.getString(8);
				
				boardList.add(boardDto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {e.printStackTrace();}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {e.printStackTrace();}}
		}
		
		return boardList;
		
	}
	
	
	public BoardDto getOneBoard(int num) {
		
		BoardDto boardDto = new BoardDto();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_COUNT = READ_COUNT + 1 WHERE NUM = ?");
			pstmt.setInt(1 , num);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				boardDto.setNum(rs.getInt("NUM"));
				boardDto.setWriter(rs.getString("WRITER"));
				boardDto.setEmail(rs.getString("EMAIL"));
				boardDto.setSubject(rs.getString("SUBJECT"));
				boardDto.setPassword(rs.getString("PASSWORD"));
				boardDto.setRegDate(rs.getDate("REG_DATE"));
				boardDto.setReadCount(rs.getInt("READ_COUNT"));
				boardDto.setContent(rs.getString("CONTENT"));
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {e.printStackTrace();}}
		}
		
		return boardDto;
		
	}
	
	
	
	
	
	
}
