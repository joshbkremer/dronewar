<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
	<form action="/submitID" method="post">
    <div><input type="submit" value="Play Bots" /></div>
 	 </form>
 	 
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>"> Sign out</a>
<%
    } else {
%>
<p>Please 
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to bots!</p>
<%
    }
%>



  </body>
</html>