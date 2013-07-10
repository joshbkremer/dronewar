<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="org.botgame.game.bot.Bot" %>
<%@ page import="org.botgame.servlet.GeneralInfo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="uri" value="${req.requestURI}" />
<html>
<head>
	<title>Drone Wars - Bot Controls</title>
    <base href="${fn:replace(req.requestURL, fn:substring(uri, 1, fn:length(uri)), req.contextPath)}" />
	<link rel="stylesheet" type="text/css" href="/include/main.css">
	<style>
		.centerTable {
			margin-left: auto;
			margin-right: auto;
			width: 800px;
		}
		
		.centerTable table {
			border: 1px solid white;
		}
		
		.centerTable td {
			border: 1px solid white;
		}
		
		body {
			color: white;
		}
	</style>
</head>
<body>
<%
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
    pageContext.setAttribute("user", user);
%>
<div height="70px" width="100%">
	<div style="float:left">
		<img src="/include/DroneWarLogo.png" />
	</div>
	<div style="float:right">
		<form action="<%= userService.createLogoutURL(request.getRequestURI()) %>" method="post">
    		${fn:escapeXml(user.nickname)}<input type="submit" value="Logout" style="margin-left:10px"/>
 		</form>
	</div>
	<div style="clear:both"></div>	
</div>
	<form action="/botcontrol/botcontrols.jsp" method="post">
    	<div><input type="submit" value="Modify Your Bot" /></div>
 	</form>
	<form action="/botgame" method="post">
    	<div><input type="submit" value="Run Bot Game" /></div>
 	</form>
	<form action="/arenaResults" method="post">
    	<div><input type="submit" value="View Arena Results" /></div>
 	</form>
 	 
</body>
</html>