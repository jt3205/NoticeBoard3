package com.company.MVC_Model2_Board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.MVC_Model2_Board.board.BoardDAO;
import com.company.MVC_Model2_Board.board.BoardVO;
import com.company.MVC_Model2_Board.user.UserDAO;
import com.company.MVC_Model2_Board.user.UserVO;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String viewPath = "/WEB-INF/views/";  // 뷰경로를 저장해두고.
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    					throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		request.setCharacterEncoding("EUC-KR");
		process(request, response);		
	}
	
	private void dispatcher(HttpServletRequest request, HttpServletResponse response, String view){
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath + view);
		try {
			dispatcher.forward(request, response);
		}catch (Exception e){
			System.out.println("해당 뷰는 존재하지 않습니다."); //뷰 존재 에러시.
			e.printStackTrace();
		}
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//1. 클라이언트의 요청 path 정보를 추출한다.
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println(path);
		
		//2. 클라이언트의 요청 path에 따라 적절히 분기처리 한다.
		if(path.equals("/login.do")){
			if(request.getMethod().equalsIgnoreCase("post")){
				System.out.println("로그인 처리"); //포스트 전송일 경우 로그인 처리.
				
				//1. 사용자 입력 정보 추출
				String id = request.getParameter("id");
				String password = request.getParameter("password");
				
				//2. DB 연동처리
				UserVO vo = new UserVO();
				vo.setId(id);
				vo.setPassword(password);
				
				UserDAO userDAO = new UserDAO();
				UserVO user = userDAO.getUser(vo);
				
				//3. 화면 네비게이션
				if(user != null){
					request.getSession().setAttribute("user", user); //세션에 유저 할당함.
					response.sendRedirect("/getBoardList.do");
				}else{
					//로그인 실패한 경우
					response.sendRedirect("/login.do"); //실패한 경우 login.do로 리다이렉트 시킴.
				}
			} else {
				dispatcher(request, response, "login.jsp");				
			}
			return;
		} else if(path.equals("/logout.do")){
			System.out.println("로그아웃 처리");  //로그아웃은 겟 요청이니 바로 로그아웃 처리.
			
			//1. 브라우저와 연결된 세션 객체를 강제 종료한다.
			HttpSession session = request.getSession();
			session.removeAttribute("user"); //로그인에 관련된 user 객체만 지워줌.

			//2. 세션 종료 후, 메인화면으로 이동한다.
			response.sendRedirect("/login.do");  //메인화면 이동은 do로 처리함.
			return;
		} 
		//여기까지는 로그인 없이도 처리할 수 있는 부분 이 이후부터는 로그인 한 사용자만이 접근할 수 있는 부분.
		
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		//로그인되어 있지 않다면 로그인 페이지로 보낸다.
		if(user == null){
			System.out.println(user);
			response.sendRedirect("/login.do");
			return;
		}
		
		//상세하게 해당 아이디와 글번호 일치 여부에 따라 수정 삭제가 나타나도록 하는 부분은 하지 않았음.
		if(path.equals("/insertBoard.do")){
			if(request.getMethod().equalsIgnoreCase("POST")){
				//포스트 요청일 경우 글을 등록하도록 포스트 처리.
				
				//1. 사용자 입력 정보 추출
				request.setCharacterEncoding("UTF-8");
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");	
	
				//2. DB 연동 처리
				BoardVO vo = new BoardVO();
				vo.setTitle(title);
				vo.setWriter(writer);
				vo.setContent(content);
				
				BoardDAO boardDAO = new BoardDAO();
				boardDAO.insertBoard(vo);
				
				// 3. 화면 네비게이션
				response.sendRedirect("/getBoardList.do");
			} else {
				dispatcher(request, response, "insertBoard.jsp"); //GET요청일 경우 글쓰기 페이지를 보여줌.
			}
		} else if(path.equals("/updateBoard.do")){
			if(request.getMethod().equalsIgnoreCase("POST")){
				System.out.println("글 수정 처리");
				
				//1. 사용자 입력 정보 추출
				request.setCharacterEncoding("UTF-8");
				String title = request.getParameter("title");
				String content = request.getParameter("content");	
				String seq = request.getParameter("seq");
	
				//2. DB 연동 처리
				BoardVO vo = new BoardVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setSeq(Integer.parseInt(seq));
				
				BoardDAO boardDAO = new BoardDAO();
				boardDAO.updateBoard(vo);
				
				//3. 화면 네비게이션 
				//수정후에는 리스트로 돌아간다.
				response.sendRedirect("/getBoardList.do");
			} else { //글 수정을 get 요청으로 온다면 잘못된 요청
				response.sendRedirect("/getBoardList.do");
			}
			
		} else if(path.equals("/deleteBoard.do")){
			//삭제는 get요청으로 오게 된다. 따라서 별도 처리 필요 없음.
			System.out.println("글 삭제 처리");
			
			//1. 사용자 입력 정보 추출	
			String seq = request.getParameter("seq");

			//2. DB 연동 처리
			BoardVO vo = new BoardVO();	
			vo.setSeq(Integer.parseInt(seq));
			
			BoardDAO boardDAO = new BoardDAO();
			boardDAO.deleteBoard(vo);
			
			//3. 화면 네비게이션
			//삭제 완료 후 리다이렉트
			response.sendRedirect("getBoardList.do");
			
		} else if(path.equals("/getBoard.do")){
			System.out.println("글 상세 조회 처리");
			
			//검색할 게시글 번호 추출
			String seq = request.getParameter("seq");

			//DB 연동 처리
			BoardVO vo = new BoardVO();
			vo.setSeq(Integer.parseInt(seq));
			
			BoardDAO boardDAO = new BoardDAO();
			BoardVO board = boardDAO.getBoard(vo);
			
			
			//디스패처를 활용하여 재작성.
			request.setAttribute("board", board);
			dispatcher(request, response, "getBoard.jsp");
			
		} else if(path.equals("/getBoardList.do")){
			//리스트 요청은 전부 get으로 오기 때문에 분리 필요 없음.
			System.out.println("글 목록 검색 처리");
			
			//DB 연동 처리
			BoardVO vo = new BoardVO();
			BoardDAO boardDAO = new BoardDAO();
			List<BoardVO> boardList = boardDAO.getBoardList(vo);
			
			//디스패처를 활용하여 재작성.
			
			request.setAttribute("boardList", boardList);
			dispatcher(request, response, "getBoardList.jsp");

		}
	}
}

