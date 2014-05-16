<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form name="actionForm" action="LoginServlet" method ="POST">
<table>
<tr><td>Enter your Username: </td><td><input type="text" name="uname"/></td></tr>
<tr><td>Enter your Password: </td><td><input type="password" name="passwd"/></td></tr>
<tr><td>Select your Group: </td><td><select name="group">
  <option value="Admin">Admin</option>
  <option value="People">People</option>
</select></td></tr>
<tr><td colspan="2" align="center"><input type="submit" value="submit"> </td></tr>
</table>
</form>
</body>
</html>