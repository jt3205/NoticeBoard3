<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>Insert title here</title>
</head>
<body>
	<center>
	<h1>�� ���</h1>
	<a href="logout_proc.do">�α׾ƿ�</a>
	<hr>
	<form method="POST" action="insertBoard.do">
	<table border="1" cellspacing="0" cellpadding="0">
		<tr>
			<td bgcolor="orange" width="70">����</td>
			<td align="left"><input name="title" type="text"/></td>
		</tr>
		<tr>
			<td bgcolor="orange">�ۼ���</td>
			<td align="left"><input name="writer" type="text"/></td>
		</tr>
		<tr>
			<td bgcolor="orange">����</td>
			<td align="left"><textarea name="content" cols="40" rows="10"></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="�� ���"/>		
		</tr>
	</table>
	</form>
	<hr>
	<a href="getBoardList.do">�� ��� ����</a>
	</center>
</body>
</html>











