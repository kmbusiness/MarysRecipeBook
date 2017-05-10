<%-- 
    Document   : logout
    Created on : Apr 10, 2017, 10:11:44 AM
    Author     : johnkmnguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logout</title>
</head>
<body>
<% session.invalidate(); 
String redirectURL = "faces/index.xhtml";
//String redirectURL = "http://www.google.com";
response.sendRedirect(redirectURL);

%>

</body>
</html>
