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
	
	
	 
	// 여기서는 어떤 매개변수 형인지 모르니까 적어줘야지
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
		ArrayList<Member> list = new MemberService().selectList(); // ArrayList 설명?
		// 조회 결과가 있는지 없는지 판단 한 후 사용자가 보게 될 응답화면 지정
		
//		selectList() 메서드의 반환값이 ArrayList<Member> 타입이기 때문에,
//		list라는 ArrayList<Member> 타입 변수에 그 값을 저장하는 것이다.
		
		if (list.isEmpty()) { // 텅 비어있을 경우 == 조회된 데이터 없었을 경우
			new MemberMenu().displyNodate("전체조회 결과가 없습니다");
		}else { // 뭐라도 조회된 데이터 있을 경우
			new MemberMenu().displayMemberList(list); 
			// 컨트롤러에서는 출력하지마셈 그래서 멤버메뉴에서!
		}
	}
	
	public void selectByUserId(String userId) { // 멤버메뉴 case3에서 받아온게 어떤 자료형인지 명시해줘야하니까 ! 
		Member m =new MemberService().selectByUserId(userId); // 여기서 바로 db로는 안되고 dao 호출 => dao로 이동해서 만들어주기 ! selectByUserID에 커서 올려보면 Member형
		// = 뒤로는 미리 적어두고, Dao에 가서 selectByUserId() 메소드 생성해서 얻어온 return값을 여기로 가지고 오려면 Member m에 저장해둬야지
		
		if( m == null) { // 검색결과가 없을 경우
			new MemberMenu().displyNodate("userId에 해당하는 검색 결과가 없습니다.");
		}else {
			new MemberMenu().displayMember(m); // 
			
		}
		
	}
	
	public void selectByUserName(String keyword) { // 메뉴에서 보내온 자료형 변수를 매개변수로 넣어두기
		ArrayList<Member> list =new MemberService().selectByUserName(keyword); // 이게 있어야 디비로 => dao로 가자 => 하고 와서 커서 올려서 반환형 확인! 어레이리스트 제네릭 멤버형이다 => 그 자료형의 list에 저장해줘
		// list : 텅비었거나 뭐라도 있는 리스트 상태
		//사용자에게 list 상태 알려줘야지 그게 컨틀롤러의 역할
		
		if(list.isEmpty()) { // 반환값 확인 트루 혹은 폴스
			new MemberMenu().displyNodate(keyword + "에 해당하는 검색 결과가 없습니다"); // 우리 이거 만ㄷ르었으니까 그냥 호출하먄됨
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
		
		Member m = new Member(); // 기본으로 만드는 방법, 매개변수 생성자로 만들어 주는 방법 두 개 있는데 이거는 기본생성자로 만듦. 만드는 건 본인 맘
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address); // 적어도 이 다섯개는 내가 원하는 값으로 변화 다른 건 초기값이 있음.
		
//		new MemberDao().updateMember(m); // m에 담은 것들 다오로 넘긴다.
		
		int result = new MemberService().updateMember(m); // 담아온 것들을 result에 넣음. int형. 1이상이면 밑에 
		
		if (result > 0 ) {
			new MemberMenu().displaySuccess("성공적으로 회원 정보가 변경 되었습니다");
		}else {
			new MemberMenu().displayFail("회원 정보 변경 실패");
		}
	}
	
	public void deleteMember(String userId) {
		// 하나만 담을 거니까 m에 담을 필요가 없음 ! 값일 많을 때... 값이 3개정도 이상일 때
		
		int result = new MemberService().deleteMember(userId);
		
		if (result >0) { // 1행이 삭제되었습니다 => 1이 리턴이 됨
			new MemberMenu().displaySuccess(userId +"의 회원이 성공적으로 탈퇴되었습니다");
		}else {
			new MemberMenu().displayFail(userId + "에 해당하는 회원이 없습니다.");
		}

		
	}
	

	
}
