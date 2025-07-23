package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
// 			    해당 메소드로 부터 전달된 데이터 ! 가공처리한 후 ! Dao로 전달하면서 호출
// 				Dao로부터 반환받은 결과에 따라 성공인지 실패인지 판단 후 응답화면 결정(view 메소드 호출)
public class MemberController {
	
	
	 
	// 여기서는 어떤 매개변수 형인지 모르니까 적어줘야지
	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName ,String gender, String age, String email, String phone, String address, String hobby) {
		
		// 멤버 메뉴로 받은 값들을 데이터를 직접적으로 처리해주는 DAO로 넘기기!
		// => 어딘가에 주섬주섬 담아서 전달!!(가방에 담기!)
		// 	  어딘가? => Member 객체
		
		// 방법1) 기본생성자로 생성한 후 각 필드에 값을 setter 메소드를 통해 일일이 담는 방법 => 매개변수가 몇 개 없을 때
		
		
		// 방법2) 아싸리 매개변수 생성자를 통해서 생성과 동시에 담는 방법
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby); 
		// 위에 자료형 다 적혀있으니까 뭔지 알잖아 그래서 안 적어도 됨
		
		 //System.out.println(m); // m 뒤에 to String 생략. 
		
		//친한 dao에
		int result = new MemberDao().insertMember(m); // 매개변수로 받은거 m에 저장되어있는데 그거를 result에 저장
		// 마우스 올려보면int로 반환한다고 쓰여있음. -> int result에 넣어주기
		// 수행한 결과가 result에....
		if (result>0) {
			new MemberMenu().displaySuccess("성공적으로 회원이 추가되었습니다"); // 얘네 인수, message로 감
		}else {
			new MemberMenu().displayFail("회원 추가에 실패했습니다."); // 얘네 인수, message로 감
		} // 여기서 new 는뭐지? 새로운 함수 생성자를 쓸 때 처음에 !
		//  
		
		/*
		 * A_practice a = new A_practice();
		 * a. method();
		 * == new A_practic(). method();
		 * menu1 클래스에 있는 값을 menu2에서 사용하려면 menu1의 객체를 생성해야 해.
			그런데 menu2에서는 menu1의 객체를 직접적으로 알 수 없기 때문에,
			new menu1()처럼 객체를 직접 생성해서 그 안의 메서드나 값을 사용하는 거야.
			예를 들어 menu1 m = new menu1(); m.method();처럼 쓰는 대신,
			new menu1().method();처럼 바로 생성해서 호출할 수도 있어.
			즉, 객체를 새로 만들어서 그 안의 기능을 바로 가져다 쓰기 위해서 new를 붙여서 호출하는 거야.
		 */
		
	}
	
	/**
	 *  사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList(); // ArrayList 설명?
		// 조회 결과가 있는지 없는지 판단 한 후 사용자가 보게 될 응답화면 지정
		
//		selectList() 메서드의 반환값이 ArrayList<Member> 타입이기 때문에,
//		list라는 ArrayList<Member> 타입 변수에 그 값을 저장하는 것이다.
		
		if (list.isEmpty()) { // 텅 비어있을 경우 == 조회된 데이터 없었을 경우
			new MemberMenu().displyNodate("전체조회 결과가 없습니다");
		}else { // 뭐라도 조회된 데이터 있을 경우
			new MemberMenu().displayMemberList(list); 
			// 컨트롤러에서 출력 x 그래서 멤버메뉴에서!
		}
	}
	
	
	

}
