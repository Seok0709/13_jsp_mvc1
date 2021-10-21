package step2_00_loginEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DAO(Data Access Object) : 데이터 접근 객체
public class MemberDao {
	
	//SingleTon 패턴 
	private MemberDao() {};
	private static MemberDao instance = new MemberDao();
	public static MemberDao getInstance(){
		return instance;
	}
	
	
	private Connection conn;     
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 반환타입은 Connection이며 메서드 명은 getter형식으로 getConnection으로 작성한다.
	public Connection getConnection() {
			
			try {
				
				Class.forName("com.mysql.jdbc.Driver");
				String url     = "jdbc:mysql://localhost:3306/login_ex?serverTimezone=UTC";
				String user    = "root";
				String passwd  = "1234";
				conn = DriverManager.getConnection(url, user, passwd);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return conn;
		}
	
	public boolean insertMember(MemberDto mdto) {
		
		boolean isFirstMember = false;
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID = ?");
			pstmt.setString(1, mdto.getId());
			rs = pstmt.executeQuery();
			
			if (!rs.next()) {
				
				pstmt = conn.prepareStatement("INSERT INTO MEMBER VALUES(?,?,?,NOW())");
				pstmt.setString(1, mdto.getId());
				pstmt.setString(2, mdto.getPasswd());
				pstmt.setString(3, mdto.getName());
				pstmt.executeUpdate();
				isFirstMember = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();}    catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return isFirstMember;
		
	}
	
	public boolean login(String id , String passwd) {
		
		boolean isValidMember = false;
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID = ? AND PASSWD = ?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				isValidMember = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();}    catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return isValidMember;
		
	}
	
	public boolean deleteMember(MemberDto memberDto) {
		
		boolean isDeleteMember = false;
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID = ? AND PASSWD = ?");
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPasswd());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				pstmt = conn.prepareStatement("DELETE FROM MEMBER WHERE ID = ?");
				pstmt.setString(1, memberDto.getId());
				pstmt.executeUpdate();
				isDeleteMember = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();}    catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return isDeleteMember;
		
	}
	
	public boolean updateMember(MemberDto memberDto) {
		
		boolean isUpdateMember = false;
		 
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID=? AND PASSWD=?");
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPasswd());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				pstmt = conn.prepareStatement("UPDATE MEMBER SET NAME=? WHERE ID=?");
				pstmt.setString(1, memberDto.getName());
				pstmt.setString(2, memberDto.getId());
				pstmt.executeUpdate();
				isUpdateMember = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {rs.close();}    catch (SQLException e) {e.printStackTrace();}
			try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return isUpdateMember;
		
	}
	
	
	
	
}
