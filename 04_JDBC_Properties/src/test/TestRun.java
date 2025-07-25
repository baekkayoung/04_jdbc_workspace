package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class TestRun {

	public static void main(String[] args) {
	
		// Properties 복습
		/*
		 * Properties 특징 
		 * - Map 계열 컬렉션 (Key + Value 세트로 담는 특징)
		 * - key, value 모두 String(문자열)으로 담기
		 * 	 setProperty (String key, String value)
		 * 	 getProperty(String key) : String value
		 *   
		 * - 주로 외부파일(.properties, .xml)로 입출력 할 때 사용 => 환경설정 파일 같은 거... 개발자 아닌 사람이 보는 거
		 * 
		 * 
		 */
		
		// 맵이기때문에 순서를 유지하지 않고 막 담긴다
		
		/*
		Properties prop = new Properties();
		prop.setProperty("C", "INSERT");
		prop.setProperty("R", "SELECT");
		prop.setProperty("U", "UPDATE");
		prop.setProperty("D", "DELETE");
		
		
		try {
			//prop.store(new FileOutputStream("resources/test.properties"), "properties Test");
			// FileNotFoundException
			
			prop.storeToXML(new FileOutputStream("resources/test.xml"), "properties test");
			
		} catch (IOException e) {// 다형성
			e.printStackTrace();
		}
		*/
		
		Properties prop = new Properties(); // 텅 비어있는 상태..
		// 파일을 읽어들여서 채워보기
		
		// 리소스 파일 채워옴
		// 나 지금 읽어서 채울거야!
		try {
			prop.load(new FileInputStream("resources/driver.properties")); // 이제 텅 비어있는 상태가 아님!
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop.getProperty("driver")); // oracle.jdbc.drivet.OracleDriver
		System.out.println(prop.getProperty("url")); //jdbc:oracle:thin:@localhost:1521:xe
		System.out.println(prop.getProperty("username"));//jdbc
		System.out.println(prop.getProperty("password"));//jdbc
		System.out.println(prop.getProperty("password1"));//null : 존재하지 않는 키 값 제시시 
		
		
		
	}
}
