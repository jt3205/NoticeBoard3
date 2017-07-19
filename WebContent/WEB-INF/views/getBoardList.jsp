<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!-- �߰� -->   
<%@ page import="com.company.MVC_Model2_Board.board.BoardVO" %> 
<%@ page import="com.company.MVC_Model2_Board.board.BoardDAO" %> 
<%@ page import="java.util.List" %>

<%
	BoardVO vo = new BoardVO();
	BoardDAO boardDAO = new BoardDAO();
	List<BoardVO> boardList = boardDAO.getBoardList(vo);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>board ���̺� ��ü ���ڵ� �����ֱ�</title>
</head>
<body>
	<center>
	<h1>�Խñ� ���</h1>
	<h3>�׽��� ȯ���մϴ�.<a href="logout.do">�α׾ƿ�</a></h3>
	<form method="POST" action="getBoardList.do">
	<table border="1" cellspacing="0" cellpadding="0" width="700">
		<tr>
			<td align="right">
				<select name="searchCondition">
					<option value="WRITER">�ۼ���
					<option value="TITLE">����
				</select>
				<input name="searchKeyword" type="text"/>
				<input type="submit" value="�˻�"/>
			</td>	
		</tr>
	</table>
	</form>
	<table border="1" cellspacing="0" cellpadding="0" width="700">
		<tr>
			<th bgcolor="orange" width="100">��ȣ</th>
			<th bgcolor="orange" width="200">����</th>
			<th bgcolor="orange" width="150">�ۼ���</th>
			<th bgcolor="orange" width="150">�����</th>
			<th bgcolor="orange" width="100">��ȸ��</th>			
		</tr>
		<% for(BoardVO board : boardList) { %>
		<tr>
			<td align="center"><%= board.getSeq() %></td>
			<td align="left"><a href="getBoard.do?seq=<%=  board.getSeq()  %>"><%= board.getTitle() %></a></td>
			<td align="center"><%= board.getWriter() %></td>
			<td align="center"><%= board.getRegDate() %></td>
			<td align="center"><%= board.getCnt() %></td>			
		</tr>
		<% } %>
	</table>
	<hr>
	<a href="insertBoard.do">���� ���</a>
	</center>
</body>
</html>

















