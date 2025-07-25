package com.kh.run;

import com.kh.view.MemberMenu;

public class Run {

	public static void main(String[] args) { //
		/*
		 * * MVC 패턴
		 * 
		 * M(Model) : 데이터 처리 담당 (데이터들을 담기 위한 클래스(VO), 데이터들이 보관된 공간(DB)과 직접적으로 접근을 해서 데이터를 주고 받기 (DAO))
		 * V(View) : 화면담당 (지금은 콘솔창! 사용자가 보게되는 시작적인 요소, 출력 및 입력)
		 * C(Controller) : 사용자의 요청을 처리해주는 담당 (사용자의 요청을 처리한 후 그에 해당하는 응답화면을 지정)
		 *
		 */
		
		// Run : 프로그램 실행만 담당(사용자가 보게 될 첫 화면만 띄워주고 끝!)
		new MemberMenu().mainMenu(); // 처음!
	}

}