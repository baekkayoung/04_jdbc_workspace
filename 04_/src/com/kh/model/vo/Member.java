package com.kh.model.vo;

import java.sql.Date;

/**
 * @author user1
 *
 */
public class Member { // 테이블의 이름을 따라서!
	// 필드
	// 필든느 기본적으로 DB컬럼명과 유사하게 작업
	
	private int userNo; 
	private String userId; 
	private String userPwd; 
	private String userName;
	private String gender; 
	private int age;
	private String email; 
	private String phone; 
	private String address; 
	private String hobby; 
	private Date enrollDate ;
	
	// 생성자부
	// 기본생성자, 매개변수 생성자
	public Member() {}
	// alt+shift + s + o
	
	public Member(int userNo, String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrollDate) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}


	public Member(String userId, String userPwd, String userName, String gender, int age, String email, String phone,
			String address, String hobby) {
		super(); // userNO, hobby
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}

	
	// 메소드부
	//게터세터투스트링
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAGE(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", gender=" + gender + ", age=" + age + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", hobby=" + hobby + ", enrollDate=" + enrollDate + "]";
	}
	
	
	
	
	
}
