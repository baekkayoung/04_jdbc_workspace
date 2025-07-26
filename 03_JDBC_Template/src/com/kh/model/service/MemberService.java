package com.kh.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*; // static .* 

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
	
	public int insertMember(Member m) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jdbc","jdbc");
		Connection conn = getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		// 6) 트랜젝션 처리
		if (result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// Connection 객체 반납
		close(conn);
		
		return result;
	}
	
	public ArrayList<Member> selectList() {
		Connection conn = getConnection(); 
		ArrayList<Member>  list = new MemberDao().selectList(conn);
		
		close(conn);
		return list;
	}
	
	public Member selectByUserId(String userId) {
		Connection conn = getConnection(); 
		Member m = new MemberDao().selectByUserId(conn, userId);
		
		
		close(conn);
		return m;
	}
	
	public ArrayList<Member> selectByUserName(String keyword) {
		Connection conn = getConnection();
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		
		
		 close(conn);
		 return list;
	}
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().updateMember(conn , m);
		
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		return result;
		
	}
	
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn ,userId);
		
		if (result >0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		return result ; 
		
	}

}
