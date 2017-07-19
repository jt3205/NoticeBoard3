package com.company.MVC_Model2_Board.user;

public class UserVO {
	
	/*
	 * VO(Value Object) => 순수한 데이터 객체
	 * => 계층간 데이터 교환을 위한 자바빈즈를 말한다.
	 *    즉 계층간의 데이터 전달에 사용하는 데이터 객체들이다.
	 *    VO 패턴은 데이터 전달을 위한 가장 효율적인 방법이다.
	 */
	private String id;
	private String password;
	private String name;
	private String role;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
