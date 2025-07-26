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
		result = stmt.executeUpdate(sql); // 쿼리를 돌리면 int 행수를 반환!
		
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
	

	/**
	 * 사용자의 아이디로 회원검색 요청 처리해주는 메소드
	 * @param userId : 사용자가 입력한 검색하고자 하는 회원 아이디가 담겨있겠지
	 * @return : 검색된 결과가 있으면 생성이 된 Member 객체를, 결과가 없으면 null을 반환한다. => 얘를 호출한 컨트롤러로
	 */
	public Member selectByUserId(String userId) { // 컨트롤러에서 받아온 값
		// select문은(최대 한행)=> ResultSet 객체에 담아야 
		// 한 행나오니까 ArrayList 필요 없음! Member 객체 한개만 있으면 될듯?
		
		// 필요한 변수들 셋팅
		Member m = null; // 조회결과가 있을 수도 있지만 없을수가 있으니까 
		
		// JDBC용 객체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;;
		String sql = "SELECT * FROM MEMBER WHERE USERID ='"+ userId + "'"; // 파란색 글자로 세미콜론이 있으면 안 됨
		
		// 다 했으니까 이제 연결~
		
		// 1. jdbc driver 등록 : 자바랑 디비 연결하려면 클래스로
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "jdbc", "jdbc");
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) { // 한 행이라도 조회가 됐을 때
				// 조회가 됐다면 해당 조회된 컬럼값들을 하나하나 쏙쏙 뽑아서 Member 객체의 값 필드에 담기
				m = new Member(rset.getInt("userno"), // 위에서 선선안만 햇으니까. () 이렇게만 있으면 얘는 기본, 우리는 담아올게 있으니까
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
			
			// 위의 조건문 다 끝난 시점에 만약 조회된 데이터가 없었을 경우 => m은 null임!
			// 만약 조회된 데이터가 있었을 경우 그때는 => m은 생성 후 뭐라도 담겨있음! 
			// 조회가 된건지 안된건지 알 수 있게 null;로 셋팅해준거임
			
			
			
			
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
				e.printStackTrace();
			}
		} return m;// => 컨트롤러로
		
		
		
		
		
		
		
	}
	
	
	/** 사용자의 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param keyword : 사용자가 입력한 keyword 검색 요청한 keyword의 값이 있다.
	 * @return  부른 컨트롤러로 ㄱㄱ
	 */
	public ArrayList<Member> selectByUserName(String keyword) { 
		// 얘도 다오형에서 자료형 변수로 받아줘야 어떤 자료형의 키워드인지 알지
		// select문 수행 (키워드니까 여러 행 나옴) => ResultSet => ArrayList로 짜야함!! 여러행이면 무조건! 하나면 아까 멤버객체m으로 해도는거고
		
		
		ArrayList<Member>  list = new ArrayList<Member>(); // 지금은 일단 [] < 비어있는 상태
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" +keyword + "%'"; // 입력 받아온 키워드를 넘기고 넘겨서 여기서 활용하려고!
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc"); //forname이랑 크로스
			stmt = conn.createStatement(); // 크리에이트 스테이트먼트의 반환형이 스테이트먼트니까 넣을 수있지 Statement stmt = null;
			rset = stmt.executeQuery(sql); // String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" +keyword + "%'"; 표와같은 걸 담음.. 
			// 키워드 검색이니까 여러행이 나오겠구나 => 그러면 while문 반복
			
			while(rset.next()) { // 결과가 있을 때마다 돈다
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
								   rset.getDate("enrolldate"))); // 이 리스트에 Member 밖에 못 들어옴 => 위에서 어리이리스트멤버형으로
			}
			
			/*
			 * 
			 * select문으로 입력해서 oracle에 나온 결과가 sql에 저장된거죠? 그거의 결과값이 sql에 담겨서 그걸 rset에 저장한거고
			 * 반복문을 통해서 list에 한 줄 한줄 담은거고? 
			 * 
			 * 
			 * */
			
		} catch (ClassNotFoundException e) { //이런 클래스가 없으면? 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 무조건 수행하고 지나가니까. 실패했어도 클로즈는 해야지
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		return list; // 텅빈 리스트 | 뭐라도 담겨있는 리스트 
		// 반환형 ? 어레이리스트 제네릭 멤버형
	}
	
	/** 사용자가 입력한 아이디의 정보 변경 요청 처리해주는 메소드
	 * @param m 사용자가 앞에서부터 입력한 멤버 객체가 있는 것
	 * @return 처리된 행수! int형~
	 */
	public int updateMember(Member m) { // 어떤 m인지 모르니까
		//update 문 돌리기 = > 처리된 행수가 리턴이 된다 . => 얘를 받아줄 인트 변수가 필요하겠다. 1이상이면 커밋 , 아니면 롤백
	
		int result = 0 ;  // 변수 셋팅
		
		Connection conn = null;
		Statement stmt = null;
		String sql = "UPDATE MEMBER " // m.에 있는 비밀번호가 꺼내지겟지 사용자가 입력한/
					+ "SET USERPWD = " +"'" + m.getUserPwd() + "'"
					+     ", EMAIL = " + "'" +m.getEmail() + "'"
					+     ", PHONE = " + "'" + m.getPhone()	+ "'"
					+    ", ADDRESS = " + "'" + m.getAddress() +"'"
					+ "WHERE USERID = " + "'" + m.getUserId() +"'"; // 조건 ! 가져온 아이디
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql); // int 반환
			
			if (result>0) {
				conn.commit(); // conn.은 왜 붙는거임?
			}else {
				conn.rollback();
			} 
			
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
	 return result; //자료형 int => 이 메소드의 반환형도 int로 고쳐줘야
	}
	
	/**
	 * 사용자가 입력한 아이디의 회원정보를 삭제하는 메소드
	 * @param userId : 사용자가 입력한 탈퇴를 요청한 아이디
	 * @return result : 처리된 행수
	 */
	public int deleteMember(String userId) {
		// delete문 -> 처리된 행수(int) -> 트랜젝션 처리
		
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = '"+ userId +"'";
		
		// 이제 드라이브 등록~ 연결하는 준비
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql); // dml은 다 익스큐트업데이트로 돌리면 됨
			
			if(result >0) {
				conn.commit(); // 자체에서 제공하는 메소드라서.. conn
			}else {
				conn.rollback();
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} return result;
			
			
	
	
	}
	
}
