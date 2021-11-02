package step4_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;



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
		
		String dbURL 	  = "jdbc:mysql://localhost:3306/STEP4_BOARD_EX?serverTimezone=UTC";
		String dbID 	  = "root";
		String dbPassword = "1234";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

	
	// [페이징 테스트용] 데이터 생성 DAO
	public void setDummy() {
		
		Random ran = new Random();
		
		try {
			
			String[] word = {"가","나","다","라","마","바","사","아","자","차","카","타","파","하"};
			
			for (int i = 1; i < 201; i++) {
				String writer  = "";
				String passwd  = "1111";
				String subject = "";
				String email   = "";
				String content = "";
				for (int j = 0; j < 7; j++) {
					writer  += word[ran.nextInt(word.length)];
					subject += word[ran.nextInt(word.length)];
					content += word[ran.nextInt(word.length)];
					if (j < 4) {
						email += word[ran.nextInt(word.length)];
					}
				}
				email += "@gmail.com";
				
				String sql = "INSERT INTO BOARD(WRITER,EMAIL,SUBJECT,PASSWORD,REG_DATE,REF,RE_STEP,RE_LEVEL,READ_COUNT,CONTENT)";
					   sql += "VALUES(?, ?, ?, ?, now(), ?, 1, 1, 0, ?)";
				
			    conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, writer);
				pstmt.setString(2, email);
				pstmt.setString(3, subject);
				pstmt.setString(4, passwd);
				pstmt.setInt(5, i);
				pstmt.setString(6, content);
				pstmt.executeUpdate();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
	}
	
	
	// 게시글 생성 DAO
	public void insertBoard(BoardDto boardDto) {

		int ref = 0;
		int num = 0;
		
		try {
			
			conn = getConnection();

			pstmt = conn.prepareStatement("SELECT MAX(REF) FROM BOARD");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ref = rs.getInt(1) + 1;
			}

			pstmt = conn.prepareStatement("SELECT MAX(NUM) FROM BOARD");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}

			pstmt = conn.prepareStatement("INSERT INTO BOARD VALUES(?, ?, ?, ?, ?, now(), ?, 1, 1, 0, ?)");
			pstmt.setInt(1, num);
			pstmt.setString(2, boardDto.getWriter());
			pstmt.setString(3, boardDto.getEmail());
			pstmt.setString(4, boardDto.getSubject());
			pstmt.setString(5, boardDto.getPassword());
			pstmt.setInt(6, ref);
			pstmt.setString(7, boardDto.getContent());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
	}

	
	// 게시글을 수정하는 DAO
	public boolean updateBoard(BoardDto boardDto) {

		boolean isUpdate = false;
		
		try {
			
			if (validMemberCheck(boardDto)) {
				conn = getConnection();
				pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT=?, CONTENT=? WHERE NUM=?");
				pstmt.setString(1, boardDto.getSubject());
				pstmt.setString(2, boardDto.getContent());
				pstmt.setInt(3, boardDto.getNum());
				pstmt.executeUpdate();
				isUpdate = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return isUpdate;
		
	}

	
	// 게시글을 삭제하는 DAO
	public boolean deleteBoard(BoardDto boardDto) {

		boolean isDelete = false;
		
		try {
			
			if (validMemberCheck(boardDto)) {
				conn = getConnection();
				pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE NUM=?");
				pstmt.setInt(1, boardDto.getNum());
				pstmt.executeUpdate();
				isDelete = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return isDelete;
		
	}
	
	
	// 전체 게시글 갯수를 조회하는 DAO
	public int getAllCount(String searchKeyword , String searchWord) {
		
		int totalBoardCount = 0;
		
		try {
			
			conn = getConnection();
			
			String sql = "";
			if (searchKeyword.equals("total")) { // searchKeyword가 전체검색일 경우
				if (searchWord.equals("")) { // 특정 키워드가 없을경우 (보통의 경우) 
					sql = "SELECT COUNT(*) FROM BOARD";
				}
				else {	// 특정 키워드가 있을 경우
					sql = "SELECT COUNT(*) FROM BOARD ";
					sql += "WHERE SUBJECT LIKE '%" + searchWord +"%' OR ";
					sql += "WRITER LIKE '%" + searchWord +"%' "; 
				}
				
			}
			else { // searchKeyword가 전체검색이 아닐 경우
				sql = "SELECT COUNT(*) FROM BOARD WHERE " + searchKeyword + " LIKE '%" + searchWord +"%'"; 
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totalBoardCount = rs.getInt(1);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return totalBoardCount;
		
	}

	
	// 한개의 게시글 정보를 조회하는 DAO
	public BoardDto getOneBoard(int num) {

		BoardDto boardDto = new BoardDto();

		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_COUNT=READ_COUNT+1 WHERE NUM=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				boardDto.setNum(rs.getInt("NUM"));
				boardDto.setWriter(rs.getString("WRITER"));
				boardDto.setEmail(rs.getString("EMAIL"));
				boardDto.setSubject(rs.getString("SUBJECT"));
				boardDto.setPassword(rs.getString("PASSWORD"));
				boardDto.setRegDate(rs.getDate("REG_DATE"));
				boardDto.setRef(rs.getInt("REF"));
				boardDto.setReStep(rs.getInt("RE_STEP"));
				boardDto.setReLevel(rs.getInt("RE_LEVEL"));
				boardDto.setReadCount(rs.getInt("READ_COUNT"));
				boardDto.setContent(rs.getString("CONTENT"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return boardDto;
	
	}

	
	// 전체(검색) 게시글을 조회하는 DAO
	public ArrayList<BoardDto> getSearchBoard(String searchKeyword, String searchWord,  int startBoardIdx, int searchCount) {

		ArrayList<BoardDto> boardList = new ArrayList<BoardDto>();
		BoardDto boardDto = null;
		
		try {
			
			conn = getConnection();
			String sql = "";
			
			if (searchKeyword.equals("total")) { // searchKeyword가 전체검색일 경우
				if (searchWord.equals("")) { // 특정 키워드가 없을경우 (보통의 경우) 
					sql = "SELECT * FROM BOARD ORDER BY REF DESC , RE_STEP LIMIT ?,?";
				}
				else {	// 특정 키워드가 있을 경우
					sql = "SELECT * FROM BOARD ";
					sql += "WHERE SUBJECT LIKE '%" + searchWord +"%' OR ";
					sql += "WRITER LIKE '%" + searchWord +"%' "; 
					sql += "ORDER BY REF DESC , RE_STEP LIMIT  ?,?";
				}
				
			}
			else { // searchKeyword가 전체검색이 아닐 경우
				sql = "SELECT * FROM BOARD  WHERE " + searchKeyword + " LIKE '%" + searchWord +"%' ORDER BY REF DESC , RE_STEP LIMIT ?,?"; 
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startBoardIdx);
			pstmt.setInt(2, searchCount);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				boardDto = new BoardDto();
				boardDto.setNum(rs.getInt("NUM"));
				boardDto.setWriter(rs.getString("WRITER"));
				boardDto.setEmail(rs.getString("EMAIL"));
				boardDto.setSubject(rs.getString("SUBJECT"));
				boardDto.setPassword(rs.getString("PASSWORD"));
				boardDto.setRegDate(rs.getDate("REG_DATE"));
				boardDto.setRef(rs.getInt("REF"));
				boardDto.setReStep(rs.getInt("RE_STEP"));
				boardDto.setReLevel(rs.getInt("RE_LEVEL"));
				boardDto.setReadCount(rs.getInt("READ_COUNT"));
				boardDto.setContent(rs.getString("CONTENT"));
				boardList.add(boardDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return boardList;
		
	}
	
	
	// 업데이트하기 위한 게시글을 불러오는 DAO
	public BoardDto getOneUpdateBoard(int num) {

		BoardDto boardDto = new BoardDto();

		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				boardDto.setNum(rs.getInt("NUM"));
				boardDto.setWriter(rs.getString("WRITER"));
				boardDto.setEmail(rs.getString("EMAIL"));
				boardDto.setSubject(rs.getString("SUBJECT"));
				boardDto.setPassword(rs.getString("PASSWORD"));
				boardDto.setRegDate(rs.getDate("REG_DATE"));
				boardDto.setRef(rs.getInt("REF"));
				boardDto.setReStep(rs.getInt("RE_STEP"));
				boardDto.setReLevel(rs.getInt("RE_LEVEL"));
				boardDto.setReadCount(rs.getInt("READ_COUNT"));
				boardDto.setContent(rs.getString("CONTENT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		
		return boardDto;
		
	}


	// 인증된 유저인지 검증하는 DAO
	public boolean validMemberCheck(BoardDto boardDTO) {

		boolean isValidMember = false;
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=? AND PASSWORD=?");
			pstmt.setInt(1, boardDTO.getNum());
			pstmt.setString(2, boardDTO.getPassword());
			rs = pstmt.executeQuery();

			if (rs.next()) 	isValidMember = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}

		return isValidMember;
		
	}

	
	// 댓글 등록 DAO
	public void reWriteBoard(BoardDto boardDto) {
		 
		int ref      = boardDto.getRef();
		int reStep   = boardDto.getReStep();
		int reLevel  = boardDto.getReLevel();
		int boardNum = 0;

		try {

			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT MAX(NUM) FROM BOARD");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boardNum = rs.getInt(1) + 1;
			}

			pstmt = conn.prepareStatement("UPDATE BOARD SET RE_STEP=RE_STEP+1 WHERE REF=? and RE_STEP > ?");
			pstmt.setInt(1, ref);
			pstmt.setInt(2, reStep);
			pstmt.executeUpdate();

			String sql = "INSERT INTO BOARD (NUM , WRITER, EMAIL, SUBJECT, PASSWORD, REG_DATE, REF, RE_STEP, RE_LEVEL, READ_COUNT, CONTENT) ";
				   sql += "VALUES (?,?,?,?,?,NOW(),?,?,?,0,?)";
			
				   pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setString(2, boardDto.getWriter());
			pstmt.setString(3, boardDto.getEmail());
			pstmt.setString(4, boardDto.getSubject());
			pstmt.setString(5, boardDto.getPassword());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, reStep + 1);
			pstmt.setInt(8, reLevel + 1);
			pstmt.setString(9, boardDto.getContent());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}

	}
	
}
