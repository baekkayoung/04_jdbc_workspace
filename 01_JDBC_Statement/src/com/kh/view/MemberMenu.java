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
			case 3: //String userId = inputMemberId(); 
					//mc.selectByUserId(userId); // 한줄 위 userId를 여기로 넘겨줘야 db랑 연결 가능 => 컨트롤러로~
					mc.selectByUserId(inputMemberId()); // 이렇게 줄일 수 있지
					
					break; // 이렇게 먼저 메소드명를 정해두고 나중에 만들면 되는 거임
			case 4: //String keyword = inputMemberName(); // 처음엔 빨간줄 , 메소드 만들러 가야됨 1 3 2번 순이니까... 
				    //그리고 변수의 자료형이 String이니까 을 리턴하게 만들어야됨 
					//mc.selectByUserName(keyword); // keyword를 가지고 controller로 가야됨. 아직 없으니까 컴파일 에러 상태 => 컨트롤러로 가서 메소드 만들기
					
					//이렇게 줄일 수 있음
					mc.selectByUserName(inputMemberName());
					break;
			case 5: updateMember();
					break; 
			case 6: //String userId2 = inputMemberId();
					//mc.deleteMember(userId2); 밑에서 만들면 여기에 안 만들어도 됨
					mc.deleteMember(inputMemberId()); // 이렇게 줄일 수 있음!
					deleteMember();
				    break; // 아이디 입력받아서 삭제하면 됨 delete
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
	
	/** 사용자에게 회원 아이디 입력받은 후 그때 입력된 값을 반환시켜주는 메소드
	 * @return 사용자가 입력한 아이디값이 리턴
	 */
	public String inputMemberId() {
		System.out.println("\n회원 아이디 입력 : ");
		return sc.nextLine(); // 사용자한테 입력 받아서 바로 리턴 상단 case3으로 리턴해서 userId에 저장
	}
	
	/** 
	 * 조회 서비스 요청시 조회결과가 한 행일 경우 사용자가 보게 될 응답화면
	 * @param m
	 */
	public void displayMember(Member m) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		System.out.println(m);
	}
	
	//스트링 반환해야되니까
	public String inputMemberName() { //처음에 빨간줄이라도 당황ㄴ ㄴㄴ
		System.out.println("\n회원 이름(키워드) 입력 : ");
		return sc.nextLine();// 커서 오려보면 먼저 스트링 그러니까 그냥 리턴해주면 됨
	}
	
	/**
	 * 사용자에게 변경할 정보(비번, 이메일, 전화번호,주소)들과 해당 회원의 아이디 입력을 받는 화면
	 */
	public void updateMember() {
		System.out.println("\n === 회원 정보 변경 ===");
		
		// 아이디 => 비번, 이메일, 전번, 주소 순으로 입력 받아보기
		
		/*
		System.out.println("아이디 : ");
		String userId = sc.nextLine(); 
		이 코드 위에서 이미 만들었으니까 그 메소드를 이용하면 됨 => 코드의 재활용성! */
		
		String userId = inputMemberId();
		
		System.out.println("변경할 비번 : ");
		String userPwd =sc.nextLine();
		
		System.out.println("변경할 이메일 : ");
		String email = sc.nextLine();
	
		System.out.println("변경할 전화번호 :");
		String phone = sc.nextLine();
		
		System.out.println("변경할 주소 : ");
		String address = sc.nextLine();
		
		mc.updateMember(userId, userPwd, email, phone, address); // 디비로 옮겨야지.. 컨트롤러 => 다오=>
	}
	
	public void deleteMember() {
		System.out.println("\n=== 회원 탈퇴 ===");
		
		System.out.println("삭제할 아이디 : ");
		String userId = sc.nextLine();
		
		// mc.deleteMember(userId); 
	}
	
}
