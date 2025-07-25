package com.kh.employee.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.employee.model.vo.Employee;


public class EmployeeDao {

	public int inputEmployee(Employee e) {
		int result = 0 ;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
//		empName, empNo, email, phone, deptCode, jobCode, salLevel, salary, bonus, managerId
		String sql = "INSERT INTO COPY_EMP VALUES(SEQ_EMPNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE, default)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kh","kh");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, e.getEmpName());
			pstmt.setString(2, e.getEmpNo());
			pstmt.setString(3, e.getEmail());
			pstmt.setString(4, e.getPhone());
			pstmt.setString(5, e.getDeptCode());
			pstmt.setString(6, e.getJobCode());
			pstmt.setString(7, e.getSalLevel());
			pstmt.setInt(8, e.getSalary());
			pstmt.setDouble(9, e.getBonus());
			pstmt.setString(10, e.getManagerId());
			
			result = pstmt.executeUpdate();
			
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} return result;
		
	}
	
	public ArrayList<Employee> selectList() { // 여기 (에 왜 list 안ㄷ르어가지?)
		ArrayList<Employee> list = new ArrayList<Employee>(); // []
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM COPY_EMP";
		
		//String empId, String empName, String empNo, String email, String phone, String depCode, String jobCode, String salLevel,
//		int salary, double bonus, String managerId, Date hireDate, Date entDate, char entYn
		
		try { // SELECT시에는 모든 사원의 정보를 콘솔에 출력하여
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kh","kh");
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Employee
						(rset.getString("EMP_ID"),
						rset.getString("EMP_NAME"),
						rset.getString("EMP_NO"),
						rset.getString("EMAIL"),
						rset.getString("PHONE"),
						rset.getString("DEPT_CODE"),
						rset.getString("JOB_CODE"),
						rset.getString("SAL_LEVEL"),
						rset.getInt("SALARY"),
						rset.getDouble("BONUS"),
						rset.getString("MANAGER_ID"),
						rset.getDate("HIRE_DATE"),
						rset.getDate("ENT_DATE"),
						rset.getString("ENT_YN")));
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
	
	public int updateEmployee(Employee e) {
		
		int result = 0 ;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		/* 사용자에게 변경하려고 하는 사원의 사번을 입력 받은 뒤
	       이메일, 전화번호, 급여를 변경하는 UPDATE를 진행한다.
		 */
		
		String sql = "UPDATE COPY_EMP "  //미완성쿼리.?
				+  "SET EMAIL = ? "
				+     ", PHONE = ?"
				+     ", SALARY = ? "
				+ 	"WHERE EMP_NAME = ?";
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kh","kh");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, e.getEmail());
			pstmt.setString(2, e.getPhone());
			pstmt.setInt(3, e.getSalary());
			pstmt.setString(4, e.getEmpId());
			
			result = pstmt.executeUpdate();
			
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
				
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} return result;
	
	}
	public int deleteEmployee(String empName){
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM COPY_EMP WHERE EMP_NAME = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kh","kh");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, empName);
			
			result = pstmt.executeUpdate();
			
			
			if (result > 0 ) {
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
