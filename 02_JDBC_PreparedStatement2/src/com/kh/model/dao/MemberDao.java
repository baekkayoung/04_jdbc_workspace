package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

//DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과 받기
						// 끝! 결과를 Controller로 다시 리턴하는 역할도 해야함

public class MemberDao {
		
	/*
	 * * Statement와 PreparedStatement
	 * - 둘 다 sql문 실행하고 결과를 받아내는 객체
	 * 
	 * * Statement와 PreparedStatement 차이점
	 * - Statement 같은 경우 sql문을 바로 전달하면서 실행시키는 객체
	 * (즉, sql문을 완성 형태로 만들어 둬야함! 사용자가 입력한 값이 다 채워진 형태로!! stmt.executeUpdate(sql);)
	 * 
	 * 		> 기존의 statement 방식
	 * 		1) Connection 객체를 통해 Statement 객체 생성 : stmt = conn.CreateStatement();
	 * 		2) Statement 객체를 통해 쿼리를 돌렸는데, 이때 반드시 완성된 sql문을 실행 및 결과 받기 : 결과 =executeXXXX(완성된 sql);
	 * 		
	 * - PreparedStament 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체
	 * (즉, 사용자가 입력한 값들을 채워두지 않고 각각 들어갈 공간을 확보만 미리 해놓아도 됨!)
	 * 단, 해당 sql문 복격적으로 실행하기 전에는 빈 공간을 사용자가 입력한 값으로 채워서 실행하긴 해야함
	 * 		
	 * 		> preparedStatement 방식
	 * 		1) Connection 객체를 통해 PreparedStatment 객체 생성 : pstmt = conn.preparedStatement(여기에 미완성된 sql문 | 완성이 된 sql문);
	 *      2) pstmt에 담긴 sql문이 미완성 상태일 경우 우선은 완성시켜야됨. pstmt.setXXX(1, "대체할값");
	 *      3) 완성된 sql문 실행 결과 받기 : rusult = pstmt.executeXXXX(); 앞에서 이미 전달했기 때문에 ()안에 비워두기

	 *      update member set user_pwd =?
	 *      				  user_phone =?..
	 *      
	 */
	
	/** 
	 * 회원 추가해주는 메소드
	 * @param m
	 * @return
	 */
	public int insertMember(Member m) {
		// insert문 => 처리된 행수 => 트렌젝션 처리
		
		int result = 0 ;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 실행할 sql문
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,'XXX', 'XXX', 'XXX', 'X', XX, 'XXXX','XXXX' ,'XXXXXX', 'XXXX', SYSDATE);
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간확보(? == 홀더 )만 해두면 됨! 물음표 개수랑 컬럼명 개수 같은지 확인
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ? , ? , ? , ? , ? , ? , ? , ? , ? , SYSDATE)"; // 홀더(?)를 이용해서 자리만 뚫어놓은 것임
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			
			pstmt = conn.prepareStatement(sql); //원래 그냥 Statement에서는 괄호 안에 없었는데 여기는 sql을 줌
			// 애초에 pstmt 객체 생성시 sql문 담은 채로 생성!
			
			
			// pstmt.setSting(홀더 순번, 대체할 값); => '대체할값' : db에 들어갈때 홑따옴표가 자동으로 들어감
			// pstmt.setInt(홀더순번, 대체할값); 	 => 대체할값 
			
			// > 빈 공간을 실제값으로 채워준 후 실행
			pstmt.setString(1, m.getUserId()); // 첫번째
			pstmt.setString(2, m.getUserId()); // 들어가는게 문자열이라 스트링
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// >> 쿼리 돌리고 결과 받기
			result =pstmt.executeUpdate(); // 걍 stmt에서는 result =pstmt.executeUpdate(sql); 이미 위에서 했으니까 안 넣어도 됨
			
			if(result> 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	public ArrayList<Member> selectList(){
		// select문 (여러행) => ResultSet => ArrayList
		ArrayList<Member> list =new ArrayList<Member>(); //[]
		
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
		
		
		
		
		
	}
	
	public Member selectByUserId(String userId) {
		// select 문 => 한 행만 조회하겠지 아이디는 고유하니까. => ResultSet => 하나니까 걍 Member객체 하나만 있어도 괜찮
		Member m = null;
		
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?"; // Prepared스테이트먼트는 홀더 (?) 사용
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			
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
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();	
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return m ;
	}
	
	public ArrayList<Member> selectByUserName(String keyword) {
		// 키워드 검색이면 여러가지 -> 어레이리스트 반환형
		// select문 => 여러행이 나오니까 => ResultSet => ArrayList에 담아야겠다.
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'"; // 이렇게 쓰는 게 맞나?
																// '%''차'%''이렇게 돌아감.
		// 해결방법1
		// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%'||?||'%'"; // 하나의 문자열로 만들어줌
		
		// 해결방법 2
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			
			pstmt = conn.prepareStatement(sql); // ?가 있으니까 미완성 쿼리
			
			//해결방법1의 sql문일 경우
			//pstmt.setString(1, keyword);
			
			//해결방법2의 sql문일 경우
			pstmt.setString(1, "%"+"?"+"%");
			
			
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} return list;
		
	}
	
	public int updateMember(Member m) { 
		
		int result = 0 ;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER "  //미완성쿼리.?
				+  "SET USERPWD = ? "
				+     ", EMAIL = ?"
				+     ", PHONE = ? "
				+    ", ADDRESS = ? "
				+ "WHERE USERID = ?";
		
	
		
		try { // try문 안에 pstmt의 값 해주는거랑 클래스~컨 스테이트먼트 다 ?
			
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
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
			
			if(result >0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} return result;
		
	} 
	
	public int deleteMember(String userId) {
		
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			
			
			if (result >0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} return result ;
		
	}
	
	
}

