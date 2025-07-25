package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 공통 템플릿 (매번 반복적으로 작성될 코드를 메소드로 정의해둘거임)

public class JDBCTemplate {
	
	// 모든 메소드 싹 다 static 메소드!!
	// 이거 실행되자마자 메모리 영역에 다 올라감
	// 싱클톤 패턴 : 메모리 영역에 단 한 번만 오려두고 재사용을 하는 개념(Math 클래스 같은거)
	
	//   커넥션을 생성해주는..
	/**
	 * 1. Connection 객체 생성 (DB와 접속) 한 후 해당 Connection 객체 반환해주는 메소드
	 * @return
	 */
	
	
	/*
	 *  기존의 방식 : jdbc driver 구문, 접속한 db의 url, 접속할 계정명이나 비번들을 자바 소스코드 내에 명시적으로 작성함 => 정적코딩방식
	 *  
	 *  > 문제점 : dbms가 변경되었을 경우, 접속 db의 url, 계정명 / 비밀번호 변경될 경우 => 자바 코드를 수정해야됨!
	 *  		=> 수정된 내용을 반영시키고자한다면 프로그램 재구동 해야함! (프로그램이 비정상적으로 죵료됐다가 다시 구동)
	 *  		=> 유지보수에 불편하다!
	 *  
	 *  > 해결방식 : db관련 정보들을 별도로 관리하는 외부파일(.properties)로 만들어서 관리
	 * 			   외부파일로부터 읽어들여서 반영시키면 됨 => 동적코딩방식
	 */
	public static Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties(); // 텅빈상태
		
		
		try {
			
			prop.load(new FileInputStream("resources/driver.properties")); // prop에 키벨류 추가됨
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"),
											prop.getProperty("username"),
											prop.getProperty("password")); // 디비에 저장
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} return conn;  // 쓸 수 있게 반환해줘
	} // prop
	
	
	/** 2. Commit 처리해주는 메소드 (Connection 전달받아서)
	 * @param conn
	 */
	public static void commit(Connection conn) { // 받아올것들은 매개변수로 쓴다
		try { // 이 conn이 null 인 상태.. : 주소가 잘못됏다거나.. null. 하면 널포인트오류
			
			if(conn != null && !conn.isClosed()) {// conn이 널이 아니거나, conn이 반납되지 않았을 때
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** 3. rollback을 처리해주는 메소드 (Connection 전달 받아서)
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
			conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// JDBC용 객체를 전달 받아서 반납처리해주는 메소드
	
	/** 
	 *  4. Statement 관련 객체 전달받아서 반납시켜주는 메소드
	 * @param stmt
	 */
	public static void close(Statement stmt) { // 다형성 부모타입 매개변수로 자식을 가질 수 있음 부모도, 자식도 들어올 수 있음. 왜? 다형성이 적용되어서
		try {				// 얘가 부모라서 PreparedStatement도 받을 수 있다!
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	// 라이딩 상속 로딩 같은 이름인데매개변수나 종류가 다른 것 ! 위에거랑 다름 오버로딩 되어있는 상태
	/** 5. Connection 객체 전달 받아서 반납시켜주는 메소드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null && conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** 6. ResultSet 객체 전달받아서 반납시켜주는 메소드
	 * @param rset
	 */
	public static void close(ResultSet rset) {
		try {
			if (rset!=null && !rset.isClosed()) {
			rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
