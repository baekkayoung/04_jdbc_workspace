package com.kh.controller;

import java.lang.reflect.Member;
import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;
import com.kh.view.ProductMenu;
import com.kh.view.ProductMenu;
import com.kh.view.ProductMenu;

public class ProductController {
	
	
	// 상품 전체 조회
	public void selectList() {
		ArrayList<Product> list = new ProductService().selectList();
		
		if (list.isEmpty()){
			new ProductMenu().displyNodate("전체조회 결과가 없습니다");
		}else {
			new ProductMenu().displayProductList(list); 
		}
	}
	
	// 상품 추가
	
	public void insertProduct(String productId, String pName, int price, String description, int stock) {
		Product p = new Product(productId, pName, price, description, stock);
		
		int result = new ProductService().insertProduct(p);
		
		if (result>0) {
			new ProductMenu().displaySuccess("성공적으로 상품이 추가되었습니다");
		} else {
			new ProductMenu().displayFail("상품 추가에 실패했습니다.");
		}
	}
	
	
	// 상품 수정
	
	public void updateProduct(String productId, String pName, int price, String description, int stock) {
		
		Product p = new Product();
		p.setProductId(productId);
		p.setpName(pName);
		p.setPrice(price);
		p.setDescription(description);
		p.setStock(stock);
		
		int result = new ProductService().updateProduct(p);
		
		if (result > 0) {
			new ProductMenu().displaySuccess("성공적으로 상품 정보가 변경되었습니다");
		}else {
			new ProductMenu().displayFail("상품 정보 변경에 실패했습니다");
		}
		
	}
	
	// 상품 삭제
	
	public void deleteProduct(String productId) {
		
		int result = new ProductService().deleteProduct(productId);
		
		if (result >0) { // 1행이 삭제되었습니다 => 1이 리턴이 됨
			new ProductMenu().displaySuccess("상품이 성공적으로 삭제되었습니다");
		}else {
			new ProductMenu().displayFail("상품 삭제에 실패했습니다");
		}
		
	}
	
	public void selectBypName(String keyword) {
		ArrayList<Product> list = new ProductService().selectBypName(keyword);
		
		if(list.isEmpty()) { // 반환값 확인 트루 혹은 폴스
			new ProductMenu().displyNodate(keyword + "에 해당하는 검색 결과가 없습니다"); // 우리 이거 만ㄷ르었으니까 그냥 호출하먄됨
		}else {
			new ProductMenu().displayProductList(list); 
	}
	}

}
