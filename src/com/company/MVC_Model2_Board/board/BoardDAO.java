package com.company.MVC_Model2_Board.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.company.MVC_Model2_Board.common.JDBCUtil;

public class BoardDAO {
	//JDBC 관련변수 선언
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	//CRUD 메소드에서 사용할 SQL 명령어 객체 생성
	private final String BOARD_LIST = "select * from board order by seq desc";
	//Oracle or H2 데이터베이스인 경우
	/*private final String BOARD_INSERT 
	   = "insert into board(seq,title,writer,content) "
	   		+ "values((select nvl(max(seq),0)+1 from board),?,?,?)";*/
	
	//MySQL 데이터베이스인 경우
	private final String BOARD_INSERT 
	   = "insert into board(title,writer,content,regdate) values(?,?,?,now())";
	
	
	
	
	private final String BOARD_GET = "select * from board where seq=?";
	private final String BOARD_UPDATE = "update board set title=?, content=? where seq=?";
	private final String BOARD_DELETE = "delete from board where seq=?";
	
	//CRUD 메소드 구현
	public List<BoardVO> getBoardList(BoardVO vo){
		System.out.println("===> JDBC로 getBoardList() 기능 처리");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(BOARD_LIST);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				BoardVO board = new BoardVO();
				board.setSeq(rs.getInt("SEQ"));
				board.setTitle(rs.getString("TITLE"));
				board.setWriter(rs.getString("WRITER"));
				board.setRegDate(rs.getDate("REGDATE"));
				board.setCnt(rs.getInt("CNT"));
				
				boardList.add(board);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, stmt, conn);
		}
		return boardList;
	}
	//글 등록 메소드 구현
	public void insertBoard(BoardVO vo){
		System.out.println("===> JDBC로 insertBoard() 기능 처리");
		
		try{
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(BOARD_INSERT);
			stmt.setString(1, vo.getTitle());
			stmt.setString(2, vo.getWriter());
			stmt.setString(3, vo.getContent());
			stmt.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtil.close(stmt, conn);
		}
	}
	
	public BoardVO getBoard(BoardVO vo){
		System.out.println("===> JDBC로 getBoard() 기능 처리");
		BoardVO board=null;
		try{
			conn = JDBCUtil.getConnection();
			//조회수(cnt) 1 증가
			String UPDATE_CNT = "update board set cnt=cnt+1 where seq=?";
			stmt = conn.prepareStatement(UPDATE_CNT);
			stmt.setInt(1, vo.getSeq());
			stmt.executeUpdate();
			
			//해당 게시글 가져오기
			stmt = conn.prepareStatement(BOARD_GET);
			stmt.setInt(1, vo.getSeq());
			rs = stmt.executeQuery();			
			if(rs.next()){
				board = new BoardVO();
				board.setSeq(rs.getInt("SEQ"));
				board.setTitle(rs.getString("TITLE"));
				board.setWriter(rs.getString("WRITER"));
				board.setContent(rs.getString("CONTENT"));
				board.setRegDate(rs.getDate("REGDATE"));
				board.setCnt(rs.getInt("CNT"));				
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs, stmt, conn);
		}
		return board;
	}
	
	// 글 수정
    public void updateBoard(BoardVO vo){
        System.out.println("===> JDBC로 updateBoard() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(BOARD_UPDATE);
            stmt.setString(1, vo.getTitle());
            stmt.setString(2, vo.getContent());
            stmt.setInt(3, vo.getSeq());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }    
    
    // 글 삭제
    public void deleteBoard(BoardVO vo){
        System.out.println("===> JDBC로 deleteBoard() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(BOARD_DELETE);
            stmt.setInt(1, vo.getSeq());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}











