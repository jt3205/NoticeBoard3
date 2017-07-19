<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!-- �߰� -->  
<%@ page import="com.company.MVC_Model2_Board.board.BoardVO" %>  
<%@ page import="com.company.MVC_Model2_Board.board.BoardDAO" %>

<%
	String seq = request.getParameter("seq");

	BoardVO vo = new BoardVO();
	vo.setSeq(Integer.parseInt(seq));
	
	BoardDAO boardDAO = new BoardDAO();
	BoardVO board = boardDAO.getBoard(vo);
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<center>
	<h1>�� �� ����	</h1>
	<a href="logout.do">�α׾ƿ�</a>
	<hr>
	<form method="POST" action="updateBoard.do">
	<input name="seq" type="hidden" value="<%= board.getSeq() %>"/>
	<table border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td bgcolor="orange" width="70">����</td>
			<td align="left"><input name="title" type="text" 
			    value="<%= board.getTitle() %>"/></td>
		</tr>
		<tr>
			<td bgcolor="orange">�ۼ���</td>
			<td align="left"><%= board.getWriter() %></td>
		</tr>
		<tr>
			<td bgcolor="orange">����</td>
			<td align="left"><textarea name="content" cols="40" 
			   rows="10"><%= board.getContent() %></textarea></td>
		</tr>
		<tr>
			<td bgcolor="orange">�����</td>
			<td align="left"><%= board.getRegDate() %></td>
		</tr>
		<tr>
			<td bgcolor="orange">��ȸ��</td>
			<td align="left"><%= board.getCnt() %></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="�� ����"/>
		</tr>
	</table>
	</form>
	<hr>
	<a href="insertBoard.do">�� ���</a>&nbsp;&nbsp;&nbsp;
	<a href="deleteBoard.do?seq=<%= board.getSeq() %>">�ۻ���</a>&nbsp;&nbsp;&nbsp;
	<a href="getBoardList.do">�۸��</a>
</center>
</body>
</html>







