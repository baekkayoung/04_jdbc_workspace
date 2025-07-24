package com.kh.employee.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.employee.controller.EmployeeController;
import com.kh.employee.model.vo.Employee;




public class EmployeeMenu {
	
		// Scanner 객체 생성 (전역으로 다 쓸 수 있도록)
		private Scanner sc = new Scanner(System.in);
		
		// MembaeController 객체 생성
		private EmployeeController mc = new EmployeeController();
	
	
	public void mainMenu() {
		//(메뉴 구성 => 1. 사원추가 2. 사원전체조회 3. 사원수정 4. 사원삭제 0. 프로그램 종료)
		while(true) { //
			System.out.println("\n===사원관리 프로그램===");
			System.out.println("1. 사원추가");
			System.out.println("2. 사원전체조회");
			System.out.println("3. 사원수정");
			System.out.println("4. 사원삭제");
			System.out.println("0. 프로그램 종료");
			
			System.out.println(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
		
			switch(menu) {
			case 1 : inputEmployee();
					break;
			case 2 : mc.selectList();
					break;
			case 3 : updateEmployee();
					break;
			case 4 : mc.deleteEmployee(inputEmployeeName());
					break;
			case 0 : System.out.println("프로그램이 종료되었습니다"); 
					return;
			default :System.out.println("메뉴를 잘못입력하셨습니다.");
			} 
			
			
		}
		
	}
	/*
	 * 사용자에게 직원명, 주민등록번호, 이메일, 전화번호, 부서코드, 직급코드, 급여등급, 급여,
보너스율, 관리자사번을 입력 받은 뒤 COPY_EMP에 INSERT를 진행한다.
	 * 
	 */
	public void inputEmployee() {
		System.out.println("\n=== 사원 추가 ===");
		
		System.out.println("직원명 : ");
		String empName =sc.nextLine();
		
		System.out.println("주민등록번호 : ");
		String empNo = sc.nextLine();
		
		System.out.println("이메일 : ");
		String email = sc.nextLine();
		
		System.out.println("전화번호 : ");
		String phone = sc.nextLine();
		
		System.out.println("부서코드 : ");
		String deptCode = sc.nextLine();
		
		System.out.println("직급코드 : ");
		String jobCode = sc.nextLine();
		
		System.out.println("급여 등급 : ");
		String salLevel =sc.nextLine();
		
		System.out.println("급여 : ");
		String salary = sc.nextLine();
		
		System.out.println("보너스율 : ");
		String bonus = sc.nextLine();
		
		System.out.println("관리자사번 : ");
		String managerId = sc.nextLine();
		
		mc.inputEmployee(empName, empNo, email, phone, deptCode, jobCode, salLevel, salary, bonus , managerId);
	}
	
	// ------------------------------------응답화면--------------------------------
	
	public String inputEmployeeName() {
		System.out.println("\n사원 이름 입력 : ");
		return sc.nextLine(); // 사용자한테 입력 받아서 바로 리턴 상단 case3으로 리턴해서 userId에 저장
	}
	
	public void displaySuccess(String message) { 
		System.out.println("\n 서비스 요청 성공 : " + message);
	}

	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message);
	}
	
	public void displyNodate(String message) {
		System.out.println("\n"+ message);
	}
	
	public void displayEmployeeList(ArrayList<Employee> list) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
		for(Employee e : list) {
			System.out.println(e);
		}
		
	}
	/*
	 * 사용자에게 변경하려고 하는 사원의 사번을 입력 받은 뒤
       이메일, 전화번호, 급여를 변경하는 UPDATE를 진행한다.
	 */
	public void updateEmployee() {
		System.out.println("\n=== 회원 정보 변경 ===");
		// => 이름 받고, 이메일 전화번호 급여 변경
	
		String empId = inputEmployeeName();
		
		System.out.println("변경할 이메일: ");
		String email =sc.nextLine();
		
		System.out.println("변경할 전화번호 : ");
		String phone = sc.nextLine();
		
		System.out.println("변경할 급여 : ");
		int salary = sc.nextInt();
	
		
		mc.updateEmployee(empId,email,phone,salary);
	
	
	}
	
	
}
