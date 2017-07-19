package com.company.MVC_Model2_Board.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.PreparedStatement;

public class JDBCUtil {
	//Oracle DB 연동인 경우
	/*static final String driver = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";*/
	
	//H2 DB 연동인 경우
	/*static final String driver = "org.he.Driver";
	static final String url = "jdbc:h2:tcp://localhost/~/test";*/
	
	//MySQL DB 연동인 경우
	static final String driver = "com.mysql.cj.jdbc.Driver";
	static final String url 
	      = "jdbc:mysql://?useSSL=false&serverTimezone=Asia/Seoul";
	
	public static Connection getConnection() throws Exception{
		try{
			Class.forName(driver);
			
			//Oracle DB 연동인 경우
			//Connection con = DriverManager.getConnection(url, "testdb", "testdb123");
			
			//H2 DB 연동인 경우
			//Connection con = DriverManager.getConnection(url, "sa", "");
			
			//MySQL DB 연동인 경우
			Connection con = DriverManager.getConnection(url, "", "");
			return con;			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static void close(PreparedStatement stmt, Connection conn){
		if(stmt != null){
			try{
				if(!stmt.isClosed()) stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				stmt = null;
			}
		}
		if(conn != null){
			try{
				if(!conn.isClosed()) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
	public static void close(ResultSet rs, PreparedStatement stmt, Connection conn){
		if(rs != null){
			try{
				if(!rs.isClosed()) rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}
		if(stmt != null){
			try{
				if(!stmt.isClosed()) stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				stmt = null;
			}
		}
		if(conn != null){
			try{
				if(!conn.isClosed()) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
	
}








