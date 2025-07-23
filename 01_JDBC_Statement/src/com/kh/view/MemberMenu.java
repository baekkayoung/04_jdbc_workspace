package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
//View : 사용자가 보게 될 시각적인 요소 출력 및 입력
import com.kh.model.vo.Member;

public class MemberMenu {
	
	// 필드가 2개 : 메소드 바깥, 클래스 안에 선언된 변수를 👉 필드(field) 또는 멤버 변수라고 해요.
	
	// Scanner 객체 생성 (전역으로 다 쓸 수 있도록)
	private Scanner sc = new Scanner(System.in);
	
	// MembaeController 객체 생성
	private MemberController mc = new MemberController();

	
	
	/**
	 *  사용자가 보게 될 첫 화면(메인메뉴)
	 */
	public void mainMenu() {
		while(true) {
			System.out.println("\n==회원관리 프로그램 ==");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디 검색");
			System.out.println("4. 회원 이름으로 키워드 검색 ");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원탈퇴");
			System.out.println("0. 프로그램 종료");
			
			System.out.println(">> 메뉴선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu){
			case 1: inputMember(); break; // 멤버메뉴의 메소드인거임
			case 2: mc.selectList(); break; // 얘는 왜 여기에 바로??????????????????
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
			case 0: System.out.println("이용해주셔서 감사합니다."); return;
			default : System.err.println("메뉴를 잘못입력했습니다. 다시 입력하세요.");
			}
		}
	}
	
	/**
	 *  회원 추가 창(서브화면)
	 *  즉, 추가하고자 하는 회원의 정보를 입력받아서 회원 추가요청하는 창
	 */
	public void inputMember() {
		System.out.println("\n=== 회원추가 ===");
		//아이디~취미 입력 받기
		System.out.println("아이디: ");
		String userId = sc.nextLine();
		
		System.out.println("비밀번호 : ");
		String userPwd = sc.nextLine();
		
		System.out.println("이름 : ");
		String userName = sc.nextLine();
		
		System.out.println("성별(M/F): ");
		String gender = sc.nextLine();
		
		System.out.println("나이 : ");
		String age = sc.nextLine(); //숫자로 입력해도 문자열로 인식하기때문에 그냥 스트링
		
		System.out.println("이메일 : ");
		String email = sc.nextLine();
		
		System.out.println("전화번호(- 빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.println("주소 : ");
		String address = sc.nextLine();
		
		System.out.println("취미(,로 연이어서 입력) : ");
		String hobby = sc.nextLine();
		
		
		// 회원 추가 요청 == Controller 메소드 호출
		mc.insertMember(userId, userPwd, userName, gender, age, email, phone, address, hobby); // 위에서 입력받은걸 mc로
	}	// 입력받은 걸 mc의 insertMember로 !

	
	//-------------------------------------응답화면------------------------------------------------
	/**
	 * 서비스 요청 처리 후 성공했을 경우 사용자가 보게 될 응답 화면
	 * @param message 	성공메세지
	 */
	public void displaySuccess(String message) { 
		System.out.println("\n 서비스 요청 성공 : " + message);
		
	}
	
	/**
	 * 서비스 요청 처리 후 실패했을 경우 사용자가 보게 될 응답 화면
	 * @param message	실패메시지
	 */
	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message);
		
	}
	
	/** 조회 서비스 요청시 조회결과가 없을 경우 사용자가 보게 될 응답화면
	 * @param message
	 */
	public void displyNodate(String message) {
		System.out.println("\n"+ message);
	}
	
	/** 조회 서비스 요청시 조회결과가 여러행일 경우 사용자가 보게 될 응답화면
	 * @param list
	 */
	public void displayMemberList(ArrayList<Member> list) {

		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
		/* 단순 for문
		for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		*/
		
		
		
		// 향상된 for문
		// list의 자료형은 Member, Arr~는 아님
 		for(Member m : list) { // m=list.get(0) -> m=list.get(1), ...
			System.out.println(m);
		}
	}
	
}
