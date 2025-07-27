package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductMenu {
	
	private Scanner sc = new Scanner(System.in);
	private ProductController mc = new ProductController();
	
	public void mainMenu() {
		
		/*
		 * 1. 전체 조회하기
		 * 2. 상품 추가하기.
		 * 3. 상품 수정하기 - id로 조회후 수정
		 * 4. 상품 삭제하기 - id로 조회후 삭제
		 * 5. 상품 검색하기 - 상품 이름으로 키워드 검색
		 * 0. 프로그램 종료하기
		 */
		while(true) {
			System.out.println("\n===상품관리 프로그램===");
			System.out.println("1. 전체 조회하기");
			System.out.println("2. 상품 추가하기");
			System.out.println("3. 상품 수정하기");
			System.out.println("4. 상품 삭제하기");
			System.out.println("5. 상품 검색하기");
			System.out.println("0. 프로그램 종료");
			
			System.out.println(">> 메뉴선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu){
			case 1 : mc.selectList(); break;
			case 2 : insertProduct();break;
			case 3 : updateProduct();break;
			case 4 : mc.deleteProduct(inputProductId());break;
			case 5 : mc.selectBypName(inputProductName());break;
			case 0 : System.out.println("이용해주셔서 감사합니다."); return;
			default : System.err.println("메뉴를 잘못입력했습니다. 다시 입력하세요.");
			
			}
		}
		
		
		
	}
	
	///////////////////////////////////////////////////////////////////////
	
	public void displayProductList(ArrayList<Product> list) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
		for(Product p : list) { 
			System.out.println(p);
		}
	}
	
	public void displyNodate(String message) {
		System.out.println("\n" + message);
	}
	
	public void displaySuccess(String message) { 
		System.out.println("\n 서비스 요청 성공 : " + message);
		
	}
	
	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message);
		
	}
	
	public String inputProductId() {
		System.out.println("\n 상품 아이디 입력 : ");
		return sc.nextLine(); // 사용자한테 입력 받아서 바로 리턴 상단 case3으로 리턴해서 userId에 저장
	}
	
	public String inputProductName() { //처음에 빨간줄이라도 당황ㄴ ㄴㄴ
		System.out.println("\n상품 이름(키워드) 입력 : ");
		return sc.nextLine();// 커서 오려보면 먼저 스트링 그러니까 그냥 리턴해주면 됨
	}
	
	
	
	public void insertProduct() {
		System.out.println("\n=== 상품추가 ===");
	 
		System.out.println("상품 아이디 : ");
		String productId = sc.nextLine();
		
		System.out.println("상품명 : ");
		String pName = sc.nextLine();
		
		System.out.println("가격 : ");
		String price = sc.nextLine();
		sc.nextLine();
		
		System.out.println("상세 설명 : ");
		String description =sc.nextLine();
		
		System.out.println("재고 : ");
		String stock = sc.nextLine();
		
		mc.insertProduct(productId, pName, price, description, stock);
		
	}
	
	public void updateProduct() {
		System.out.println("\n === 상품 정보 변경 ===");
		
		String productId = inputProductId();
		
		System.out.println("변경할 상품명 : ");
		String pName = sc.nextLine();
		
		System.out.println("변경할 가격 : ");
		String price = sc.nextLine();
		
		System.out.println("변경할 상세 설명 : ");
		String description = sc.nextLine();
		
		System.out.println("변경할 재고 수량 : ");
		String stock = sc.nextLine();
		
		mc.updateProduct(productId, pName, price, description, stock);
		
	}
	
	public void deleteMember() {
		System.out.println("\n=== 상품 삭제 ===");
		
		System.out.println("삭제할 상품 아이디 : ");
		String productId =sc.nextLine();
		
	}
	
	

}
