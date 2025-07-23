package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

//DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과 받기
						// 끝! 결과를 Controller로 다시 리턴하는 역할도 해야함

public class MemberDao {
	
	// 컨트롤러는 위에 자료형이 있으니까 어떤 자료형인지 아는데 다오에서는 모르니까 Member 타입이라고 명시 해줌
	/**
	 * 사용자가 입력한 정보들을 추가시켜주는 메소드
	 * @param m		: 사용자가 입력한 값들이 담겨있는 Member 객체
	 * @return      : insert문 수행 후 처리된 행수
	 */
	public int insertMember(Member m) {
		// insert문 => 처리된 행수(int) => 트렌젝션 처리
		
		// 필요한 변수들 먼저 셋팅 
		// 얘네는 그냥 룰임
		int result = 0; //처리된 결과를 받아줄 변수
		Connection conn = null; // 연결된 DB의 연결정보를 담는 객체
		Statement stmt = null; // "완성된 sql"문 전달해서 곧바로 실행 후 결과 받는 객체
		
		// 실행할 sql문
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,'XXX', 'XXX', 'XXX', 'X', XX, 'XXXX','XXXX' ,'XXXXXX', 'XXXX', SYSDATE);
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,"
					 + "'" + m.getUserId() + "',"
					 + "'" + m.getUserPwd() + "',"
					 + "'" + m.getUserName() + "',"
					 + "'" + m.getGender() + "',"
					 		+ m.getAge() + ","
					 + "'" + m.getEmail() + "',"
					 + "'" + m.getPhone() + "',"
					 + "'" + m.getAddress() + "',"
					 + "'" + m.getHobby() + "', SYSDATE)";
		// System.out.println(sql); ->콘솔에 출력된 거 오라클에 넣어보고 빨간줄 뜨는지 확인! 
		
		// 1) jdbc driber 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
		// 2) Connection 객체 생성
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
		
		// 3) Statement 객체 생성
		stmt = conn.createStatement();
		
		// 4,5) sql문을 전달하면서 실행 후 결과 받는 작업!
		result = stmt.executeUpdate(sql); // 쿼리를 돌리면 int 행수를 반환
		
		// 6) 트렌젝션 처리
		if(result>0) {
			conn.commit();
		}else {
			conn.rollback();
		}
			
			
			
		
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 JDBC용 객체 반납!
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result; // 처음엔 밑줄, 왜냐하면 int형이 반환인데 이 메소드가 void였어서. 고쳐줌
	}
	
// 끝!

		
	/**
	 * 사용자가 요청한 회원 전체 조회를 처리해주는 메소드
	 * @return : 조회된 결과가 있었으면 조회된 결과물들이 담겨 있는 list | 조회된 결과가 없으면 텅 빈 list
	 */
	public ArrayList<Member> selectList() {
		// select문(여러행 조회) => ResultSet객체 => ArrayList에 차곡차곡 담기
		
		// 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<Member>(); // [] : 현재 텅 비어있음
		Connection conn = null;
		Statement stmt =null;
		ResultSet rset = null;
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER";
		
		try { 
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4,5) sql 실행 결과 받기
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) { // while문은 true일 때만 돌기 때문에 rset.next()에 값이 없으면 멈춤
				
				Member m = new Member(); // 기본생성자 초기값이 다 들어감 -> 그래서 db로부터 컬럼값을 가져오는 것
				// ArrayList<Member> list = new ArrayList<Member>(); 
//				들어가면 안되는 이유? 무한 반복
				// 사용자가 입력한 데이터들이 저장된 m 변수.. 거기에 셋팅된 값을 
				m.setUserNo(rset.getInt("USERNO")); // oracle의 USERNO컬럼의 값을 m의 USERNO로 바꿔줌
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAGE(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				//현재 참조하고 있는 행에 대한 모든 컬럼에 대한 데이터들을 한 Member 객체에 담기 끝!
				
				list.add(m);// 입력된 값들 list에 추가
				
			}
			
			// 반복문이 다 끝난 시점에 만약에 조회된 데이터가 없었다면? list는 텅 빈 상태일거임
			// 만약에 조회된 데이터가 있었다면 list에 뭐라도 담겨있었을거임
			
			// null~포인트 오류는 null. 일때 생김.
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list; // 텅빈리스트 or 뭐라도 담겨있는 리스트
		// Controller 로 list가 반환됨
	}
	
	
	
}
