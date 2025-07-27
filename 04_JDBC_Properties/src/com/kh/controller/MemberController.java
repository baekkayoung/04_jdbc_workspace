package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
// 			    해당 메소드로 부터 전달된 데이터 ! 가공처리한 후 ! Dao로 전달하면서 호출
// 				Dao로부터 반환받은 결과에 따라 성공인지 실패인지 판단 후 응답화면 결정(view 메소드 호출)
public class MemberController {
	
	// private MemberMenu mm = new MemberMenu();- > 얘로 사용하면 에러가 쭉 남
	// mc하는것처럼 얘도 왜 하면 안되는데?
	// StackOverflowError
	// 런파일에서 만들었었자나
	
	

	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName ,String gender, String age, String email, String phone, String address, String hobby) {
		

		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby); 
		
		int result = new MemberService().insertMember(m); // 매개변수로 받은거 m에 저장되어있는데 그거를 result에 저장
	
		if (result>0) {
			new MemberMenu().displaySuccess("성공적으로 회원이 추가되었습니다"); // 얘네 인수, message로 감
		}else {
			new MemberMenu().displayFail("회원 추가에 실패했습니다."); // 얘네 인수, message로 감
		} 
		
	}
	
	/**
	 *  사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberService().selectList(); // ArrayList 설명?
	
		if (list.isEmpty()) { 
			new MemberMenu().displyNodate("전체조회 결과가 없습니다");
		}else { 
			new MemberMenu().displayMemberList(list); 
		
		}
	}
	
	public void selectByUserId(String userId) { 
		Member m =new MemberService().selectByUserId(userId);  
		
		if( m == null) { // 검색결과가 없을 경우
			new MemberMenu().displyNodate("userId에 해당하는 검색 결과가 없습니다.");
		}else {
			new MemberMenu().displayMember(m); // 
			
		}
		
	}
	
	public void selectByUserName(String keyword) { 
		ArrayList<Member> list =new MemberService().selectByUserName(keyword); 
	
		
		if(list.isEmpty()) { 
			new MemberMenu().displyNodate(keyword + "에 해당하는 검색 결과가 없습니다"); 
		}else {
			new MemberMenu().displayMemberList(list); 
	}
	}
	
	/**
	 * 정보변경 요청 처리해주는 메소드
	 * @param userId : 변경하고자하는 회원 아이디
	 * @param userPwd : 변경할 비번
	 * @param email : 변경할 전번
	 * @param phone : 변경할 전번
	 * @param address : 변경할 주소
	 */
	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		
		Member m = new Member(); 
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address); 

		
		int result = new MemberService().updateMember(m); 
		
		if (result > 0 ) {
			new MemberMenu().displaySuccess("성공적으로 회원 정보가 변경 되었습니다");
		}else {
			new MemberMenu().displayFail("회원 정보 변경 실패");
		}
	}
	
	public void deleteMember(String userId) {
	
		
		int result = new MemberService().deleteMember(userId);
		
		if (result >0) { 
			new MemberMenu().displaySuccess(userId +"의 회원이 성공적으로 탈퇴되었습니다");
		}else {
			new MemberMenu().displayFail(userId + "에 해당하는 회원이 없습니다.");
		}

		
	}
	

	
}
