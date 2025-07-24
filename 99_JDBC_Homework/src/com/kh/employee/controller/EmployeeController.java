package com.kh.employee.controller;

import java.util.ArrayList;

import com.kh.employee.model.dao.EmployeeDao;
import com.kh.employee.model.vo.Employee;
import com.kh.employee.view.EmployeeMenu;
import com.kh.employee.model.dao.EmployeeDao;
import com.kh.employee.view.EmployeeMenu;

public class EmployeeController {
	
	public void inputEmployee(String empName, String empNo, String email, String phone, String deptCode, 
							String jobCode, String salLevel, String salary, String bonus, String managerId){
		
		Employee e = new Employee (empName, empNo, email, phone, deptCode, jobCode, salLevel, Integer.parseInt(salary), Double.parseDouble(bonus), managerId);
		
		int result = new EmployeeDao().inputEmployee(e);
		
		if (result > 0) {
			new EmployeeMenu().displaySuccess("성공적으로 사원이 추가되었습니다"); 
		}else {
			new EmployeeMenu().displayFail("사원 추가에 실패했습니다.");
		}
		
	}
	
	public void selectList() {
		ArrayList<Employee> list= new EmployeeDao().selectList();
		
		if(list.isEmpty()) {
			new EmployeeMenu().displyNodate("전체 조회 결과가 없습니다.");
		}else {
			new EmployeeMenu().displayEmployeeList(list);
		}
		
	}
	
	public void updateEmployee(String empId, String email, String phone, int salary) {
		
		Employee e = new Employee();
		e.setEmpId(empId);
		e.setEmail(email);
		e.setPhone(phone);
		e.setSalary(salary);
		
//		new EmployeeDao().updateEmployee(e);
		
		int result = new EmployeeDao().updateEmployee(e);
		
		if (result > 0) {
			new EmployeeMenu().displaySuccess("성공적으로 사원 정보가 변경 되었습니다");
		}else {
			new EmployeeMenu().displayFail("사원 정보 변경 실패");
		}
		}
	
	public void deleteEmployee(String empName) {
		
		int result = new EmployeeDao().deleteEmployee(empName);
		
		if (result >0) { // 1행이 삭제되었습니다 => 1이 리턴이 됨
			new EmployeeMenu().displaySuccess(empName +"의 사원이 성공적으로 탈퇴되었습니다");
		}else {
			new EmployeeMenu().displayFail(empName + " 사원이 없습니다.");
		}
		
	}
		
	

}
