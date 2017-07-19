package com.company.MVC_Model2_Board.user;

import java.sql.Connection;
import java.sql.ResultSet;

import com.company.MVC_Model2_Board.common.JDBCUtil;

import java.sql.PreparedStatement;

public class UserDAO {
	//DAO(Data Access Object)
	//=> ���������� DB�� �����ϴ� ��ü
	
	//JDBC ���ú��� ����
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	//SQL ��ɾ�
	private final String USER_GET = "select * from users where id=? and password=?";
	
	//������ �α��� ��ȸ
	public UserVO getUser(UserVO vo){
		UserVO user = null;
		
		try{
			System.out.println("===> JDBC�� getUser() ���ó��");
			
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_GET);
			stmt.setString(1,  vo.getId());
			stmt.setString(2, vo.getPassword());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				user = new UserVO();
				user.setId(rs.getString("ID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setName(rs.getString("NAME"));
				user.setRole(rs.getString("ROLE"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, stmt, conn);
		}
		return user;
	}
}












