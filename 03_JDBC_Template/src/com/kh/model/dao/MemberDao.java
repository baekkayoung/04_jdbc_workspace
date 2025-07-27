package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;

//DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과 받기
						// 끝! 결과를 Controller로 다시 리턴하는 역할도 해야함

public class MemberDao {
		
	
	/** 
	 * 회원 추가해주는 메소드
	 * @param m
	 * @return
	 */						// 여기 커니 오니까 밑에 선언 삭제
	public int insertMember(Connection conn, Member m) {
		
		int result = 0 ;	
		//Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ? , ? , ? , ? , ? , ? , ? , ? , ? , SYSDATE)"; // 홀더(?)를 이용해서 자리만 뚫어놓은 것임
		
		try {
			
			/*
			 * Class.forName("oracle.jdbc.driver.OracleDriver");
			 *conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			*/
			conn = getConnection(); // 내가 만든 스태틱메소드로 줄일 수 있음!
			
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
			
			/*
			 * if(result> 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			 * 
			 * */
			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// conn은 아직 반납하면 안됨!! (트랜젝션 처리 서비스 가서 해야함!!)
			close(pstmt);
		}
		return result;
	}
	
	
	public ArrayList<Member> selectList(Connection conn){
		// select문 (여러행) => ResultSet => ArrayList
		ArrayList<Member> list =new ArrayList<Member>(); //[]
	
		//Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			/*
			 * Class.forName("oracle.jdbc.driver.OracleDriver");
			 * conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			 */
			//conn = getConnection();
			
			pstmt = conn.prepareStatement(sql); // 물음표가 대체하는 작업해야되는데 지금 sql에는 ? 없으니까 안해도 됨
			
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
							   rset.getString("hobby"),
							   rset.getDate("enrolldate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
			//close(conn); 이왕이면 conn을 만든 곳에서 반납하자!
		}
		return list;
		
		
		
		
		
	}
	
	public Member selectByUserId(Connection conn, String userId) {
		// select 문 => 한 행만 조회하겠지 아이디는 고유하니까. => ResultSet => 하나니까 걍 Member객체 하나만 있어도 괜찮
		Member m = null;
		
		//Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?"; // Prepared스테이트먼트는 홀더 (?) 사용
		
		try {
			/*
			 * Class.forName("oracle.jdbc.driver.OracleDriver");
			 * conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			 */
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문 => 바로 돌리면 큰일남 => 대체값으로 채워준 다음에 돌려야 함 받아온 유저아이디를 이용!
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member (rset.getInt("userno"), // 위에서 선선안만 햇으니까. () 이렇게만 있으면 얘는 기본, 우리는 담아올게 있으니까
						   rset.getString("userid"),
						   rset.getString("userpwd"),
						   rset.getString("username"),
						   rset.getString("gender"),
						   rset.getInt("age"),
						   rset.getString("email"),
						   rset.getString("phone"),
						   rset.getString("address"),
						   rset.getString("hobby"), // 멤버객체 m을 우리가 원하는 걸로 채움
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
		// 키워드 검색이면 여러가지 -> 어레이리스트 반환형
		// select문 => 여러행이 나오니까 => ResultSet => ArrayList에 담아야겠다.
		
		ArrayList<Member> list = new ArrayList<Member>();
		//Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'"; // 이렇게 쓰는 게 맞나?
																// '%''차'%''이렇게 돌아감.
		// 해결방법1
		// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%'||?||'%'"; // 하나의 문자열로 만들어줌
		
		// 해결방법 2
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
		try {
			
			/*
			 * Class.forName("oracle.jdbc.driver.OracleDriver");
			 * conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			 * */
			
			pstmt = conn.prepareStatement(sql); // ?가 있으니까 미완성 쿼리
			
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
			// conn.close();
			
		} return list;
		
	}
	
	public int updateMember(Connection conn, Member m) { 
		
		int result = 0 ;
		
		//Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER "  //미완성쿼리.?
				+  "SET USERPWD = ? "
				+     ", EMAIL = ?"
				+     ", PHONE = ? "
				+    ", ADDRESS = ? "
				+ "WHERE USERID = ?";
		
	
		
		try { // try문 안에 pstmt의 값 해주는거랑 클래스~컨 스테이트먼트 다 ?
			
			/*
			 * 	Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			 * 
			 */

			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
//			> preparedStatement 방식
//			 * 		1) Connection 객체를 통해 PreparedStatment 객체 생성 : pstmt = conn.preparedStatement(여기에 미완성된 sql문 | 완성이 된 sql문);
//			 *      2) pstmt에 담긴 sql문이 미완성 상태일 경우 우선은 완성시켜야됨. pstmt.setXXX(1, "대체할값");
//			 *      3) 완성된 sql문 실행 결과 받기 : rusult = pstmt.executeXXXX(); 앞에서 이미 전달했기 때문에 ()안에 비워두기
			
			
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
		//Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
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

