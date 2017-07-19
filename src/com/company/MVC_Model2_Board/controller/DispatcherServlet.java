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
	
	private static final String viewPath = "/WEB-INF/views/";  // ���θ� �����صΰ�.
       
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
			System.out.println("�ش� ��� �������� �ʽ��ϴ�."); //�� ���� ������.
			e.printStackTrace();
		}
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//1. Ŭ���̾�Ʈ�� ��û path ������ �����Ѵ�.
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println(path);
		
		//2. Ŭ���̾�Ʈ�� ��û path�� ���� ������ �б�ó�� �Ѵ�.
		if(path.equals("/login.do")){
			if(request.getMethod().equalsIgnoreCase("post")){
				System.out.println("�α��� ó��"); //����Ʈ ������ ��� �α��� ó��.
				
				//1. ����� �Է� ���� ����
				String id = request.getParameter("id");
				String password = request.getParameter("password");
				
				//2. DB ����ó��
				UserVO vo = new UserVO();
				vo.setId(id);
				vo.setPassword(password);
				
				UserDAO userDAO = new UserDAO();
				UserVO user = userDAO.getUser(vo);
				
				//3. ȭ�� �׺���̼�
				if(user != null){
					request.getSession().setAttribute("user", user); //���ǿ� ���� �Ҵ���.
					response.sendRedirect("/getBoardList.do");
				}else{
					//�α��� ������ ���
					response.sendRedirect("/login.do"); //������ ��� login.do�� �����̷�Ʈ ��Ŵ.
				}
			} else {
				dispatcher(request, response, "login.jsp");				
			}
			return;
		} else if(path.equals("/logout.do")){
			System.out.println("�α׾ƿ� ó��");  //�α׾ƿ��� �� ��û�̴� �ٷ� �α׾ƿ� ó��.
			
			//1. �������� ����� ���� ��ü�� ���� �����Ѵ�.
			HttpSession session = request.getSession();
			session.removeAttribute("user"); //�α��ο� ���õ� user ��ü�� ������.

			//2. ���� ���� ��, ����ȭ������ �̵��Ѵ�.
			response.sendRedirect("/login.do");  //����ȭ�� �̵��� do�� ó����.
			return;
		} 
		//��������� �α��� ���̵� ó���� �� �ִ� �κ� �� ���ĺ��ʹ� �α��� �� ����ڸ��� ������ �� �ִ� �κ�.
		
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		
		//�α��εǾ� ���� �ʴٸ� �α��� �������� ������.
		if(user == null){
			System.out.println(user);
			response.sendRedirect("/login.do");
			return;
		}
		
		//���ϰ� �ش� ���̵�� �۹�ȣ ��ġ ���ο� ���� ���� ������ ��Ÿ������ �ϴ� �κ��� ���� �ʾ���.
		if(path.equals("/insertBoard.do")){
			if(request.getMethod().equalsIgnoreCase("POST")){
				//����Ʈ ��û�� ��� ���� ����ϵ��� ����Ʈ ó��.
				
				//1. ����� �Է� ���� ����
				request.setCharacterEncoding("UTF-8");
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");	
	
				//2. DB ���� ó��
				BoardVO vo = new BoardVO();
				vo.setTitle(title);
				vo.setWriter(writer);
				vo.setContent(content);
				
				BoardDAO boardDAO = new BoardDAO();
				boardDAO.insertBoard(vo);
				
				// 3. ȭ�� �׺���̼�
				response.sendRedirect("/getBoardList.do");
			} else {
				dispatcher(request, response, "insertBoard.jsp"); //GET��û�� ��� �۾��� �������� ������.
			}
		} else if(path.equals("/updateBoard.do")){
			if(request.getMethod().equalsIgnoreCase("POST")){
				System.out.println("�� ���� ó��");
				
				//1. ����� �Է� ���� ����
				request.setCharacterEncoding("UTF-8");
				String title = request.getParameter("title");
				String content = request.getParameter("content");	
				String seq = request.getParameter("seq");
	
				//2. DB ���� ó��
				BoardVO vo = new BoardVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setSeq(Integer.parseInt(seq));
				
				BoardDAO boardDAO = new BoardDAO();
				boardDAO.updateBoard(vo);
				
				//3. ȭ�� �׺���̼� 
				//�����Ŀ��� ����Ʈ�� ���ư���.
				response.sendRedirect("/getBoardList.do");
			} else { //�� ������ get ��û���� �´ٸ� �߸��� ��û
				response.sendRedirect("/getBoardList.do");
			}
			
		} else if(path.equals("/deleteBoard.do")){
			//������ get��û���� ���� �ȴ�. ���� ���� ó�� �ʿ� ����.
			System.out.println("�� ���� ó��");
			
			//1. ����� �Է� ���� ����	
			String seq = request.getParameter("seq");

			//2. DB ���� ó��
			BoardVO vo = new BoardVO();	
			vo.setSeq(Integer.parseInt(seq));
			
			BoardDAO boardDAO = new BoardDAO();
			boardDAO.deleteBoard(vo);
			
			//3. ȭ�� �׺���̼�
			//���� �Ϸ� �� �����̷�Ʈ
			response.sendRedirect("getBoardList.do");
			
		} else if(path.equals("/getBoard.do")){
			System.out.println("�� �� ��ȸ ó��");
			
			//�˻��� �Խñ� ��ȣ ����
			String seq = request.getParameter("seq");

			//DB ���� ó��
			BoardVO vo = new BoardVO();
			vo.setSeq(Integer.parseInt(seq));
			
			BoardDAO boardDAO = new BoardDAO();
			BoardVO board = boardDAO.getBoard(vo);
			
			
			//����ó�� Ȱ���Ͽ� ���ۼ�.
			request.setAttribute("board", board);
			dispatcher(request, response, "getBoard.jsp");
			
		} else if(path.equals("/getBoardList.do")){
			//����Ʈ ��û�� ���� get���� ���� ������ �и� �ʿ� ����.
			System.out.println("�� ��� �˻� ó��");
			
			//DB ���� ó��
			BoardVO vo = new BoardVO();
			BoardDAO boardDAO = new BoardDAO();
			List<BoardVO> boardList = boardDAO.getBoardList(vo);
			
			//����ó�� Ȱ���Ͽ� ���ۼ�.
			
			request.setAttribute("boardList", boardList);
			dispatcher(request, response, "getBoardList.jsp");

		}
	}
}

