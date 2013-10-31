<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作成功</title>
</head>
<body>
	操作成功<Br>
	<a href="./register.jsp">返回</a><br>
	<form action="queryUser.do" method="post">
		<input type="text" name="userName">
		<input type="submit" value="查询">
	</form>
	<form action="deleteUser.do" method="post">
		<input type="text" name="userName">
		<input type="submit" value="删除">
	</form>
	<form action="getData.do">
		<input type="submit" value="查询所有">
	</form>
</body>
</html>