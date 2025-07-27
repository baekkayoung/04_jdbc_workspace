package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;

//DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과 받기
						// 끝! 결과를 Controller로 다시 리턴하는 역할도 해야함

public class MemberDao {
	
	/*
	 *  기존의 방식 : Dao 클래스에 사용자가 요청할 때마다 실행히야되는 sql문을 자바 소스코드 내에 명시적으로 작성가능 => 정적코딩방식
	 *  	문제점 : sql문을 수정해야 될 경우 자바소스코드를 수정해야됨 => 수정된 내용을 반영시키고자 ㅎ나담녀 프로글매 재구동해야됨
	 *  
	 *   해결방식 : sql문들을 별도로 관리하는 외부하일 .xml을 만들어서 실시간으로 그 파일에 기록된 sql문을 읽어들여서 실행 => 동적코딩방식
	 *   
	 */
	
	private Properties prop = new Properties(); // 텅빈상태
	//사용자가 어떤 서비스 요청할 때마다 new Memberdao().xxxx; 호출
	// 즉, 서비스 요청할 때마다 이 기본생성자 매번 실행됨
	
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	/** 
	 * 회원 추가해주는 메소드
	 * @param m
	 * @return
	 */						
	public int insertMember(Connection conn, Member m) {
		
		int result = 0 ;	
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertMember");  
		
		try {
			
			conn = getConnection(); 
			
			pstmt = conn.prepareStatement(sql); 
		
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd()); 
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result =pstmt.executeUpdate(); 
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			close(conn);
			close(pstmt);
		}
		return result;
	}
	
	
	public ArrayList<Member> selectList(Connection conn){
		
		ArrayList<Member> list =new ArrayList<Member>(); //[]
	
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("userno"),
							   rset.getString("userid"),
							   rset.getString("userpwd"),
							   rset.getString("username"),
							   rset.getString("gender"),
							   rset.getInt("age"),
							   rset.getString("email"),
							   rset.getString("phone"),
							   rset.getString("address"),
							   rset.getString("hobby"),
							   rset.getDate("enrolldate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			//close(conn);
		}
		return list;
		
		
		
		
		
	}
	
	public Member selectByUserId(Connection conn, String userId) {
		
		Member m = null;

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectByUserId"); 
		
		try {

			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member (rset.getInt("userno"), 
						   rset.getString("userid"),
						   rset.getString("userpwd"),
						   rset.getString("username"),
						   rset.getString("gender"),
						   rset.getInt("age"),
						   rset.getString("email"),
						   rset.getString("phone"),
						   rset.getString("address"),
						   rset.getString("hobby"), 
						   rset.getDate("enrolldate"));
			}
			
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			
			
		}
		return m ;
	}
	
	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		
		ArrayList<Member> list = new ArrayList<Member>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'"; // 이렇게 쓰는 게 맞나?
																// '%''차'%''이렇게 돌아감.
		// 해결방법1
		// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%'||?||'%'"; // 하나의 문자열로 만들어줌
		
		// 해결방법 2
		String sql = prop.getProperty("selectByUserName");
		
		try {
			pstmt = conn.prepareStatement(sql); 
			
			//해결방법1의 sql문일 경우
			//pstmt.setString(1, keyword);
			
			//해결방법2의 sql문일 경우
			pstmt.setString(1, "%"+keyword+"%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("userno"), // 위에서 선선안만 햇으니까. () 이렇게만 있으면 얘는 기본, 우리는 담아올게 있으니까
						   rset.getString("userid"),
						   rset.getString("userpwd"),
						   rset.getString("username"),
						   rset.getString("gender"),
						   rset.getInt("age"),
						   rset.getString("email"),
						   rset.getString("phone"),
						   rset.getString("address"),
						   rset.getString("hobby"), // 
						   rset.getDate("enrolldate"))); // db로 부타 뽑아야하니까 위에 서 가지고와야함
				
				
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		
			
		} return list;
		
	}
	
	public int updateMember(Connection conn, Member m) { 
		
		int result = 0 ;

		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER "  
				+  "SET USERPWD = ? "
				+     ", EMAIL = ?"
				+     ", PHONE = ? "
				+    ", ADDRESS = ? "
				+ "WHERE USERID = ?";
		
	
		
		try {
			
			

			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
			
			/*
			if(result >0) {
				commit(conn);
			}else {
				rollback(conn); 트렌젝션은 서비스에서
			}*/ 
			
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
			
			
		} return result;
		
	} 
	
	
	
	
	
	
	public int deleteMember(Connection conn , String userId) {
		
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteMembe");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(conn);
			
			
		} return result ;
		
	}
	
	
}

