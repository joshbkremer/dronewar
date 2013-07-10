<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="org.botgame.game.bot.Bot" %>
<%@ page import="org.botgame.game.bot.parts.Weapon" %>
<%@ page import="org.botgame.game.bot.parts.Armor" %>
<%@ page import="org.botgame.game.bot.parts.Frame" %>
<%@ page import="org.botgame.db.entity.BotEntity" %>
<%@ page import="org.botgame.db.entity.WeaponEntity" %>
<%@ page import="org.botgame.db.entity.ArmorEntity" %>
<%@ page import="org.botgame.db.entity.FrameEntity" %>
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
    BotEntity b = GeneralInfo.getBotEntity(user);
    pageContext.setAttribute("bot", b);
    //pageContext.setAttribute("weapon1", b.getWeapons().get(0));
    //pageContext.setAttribute("weapon2", b.getWeapons().get(1));

%>

<div height="70px" width="100%">
	<img src="/include/DroneWarLogo.png" />
</div>
<div class="centerTable">
	<div style="color:red"><%=request.getParameter("msg")%></div>
	<form action="/applyBot" method="post">
		<table width="800px" height="600px">
			<tr>
				<td colspan="4" style="text-align:center">
					Bot Name: <input type="text" name="name" value="${bot.botName}" />
				</td>
			</tr>
			<tr>
				<td width="25%">
					Weapon 1:<br />
					<select name="weapon">
						<option value="" />
					<%
						List<WeaponEntity> weL = GeneralInfo.getAvailableWeapons(user);
									for (WeaponEntity we : weL) {
					%>
						<option value="<%=we.getKey()%>" <% if(b.getWeaponKeys().get(0).equals(we.getKey())) { %>selected<% } %> ><%=we.getWeapon().getWeaponName()%></option>
					<%
						}
					%>
					</select>
				</td>
				<td colspan="2" width="50%" style="text-align:center">
					Armor: <br />
					<select name="armor">
						<option value="" />
					<%
						List<ArmorEntity> aeL = GeneralInfo.getAvailableArmor(user);
									for (ArmorEntity ae : aeL) {
					%>
						<option value="<%=ae.getKey()%>"  <% if(b.getArmorKey().equals(ae.getKey())){%>selected<%} %>><%=ae.getArmor().getArmorName()%></option>
					<%
						}
					%>
					</select>
				</td>
				<td width="25%">
					Weapon 2:<br />
					<select name="weapon">
						<option value="" />
					<%
						for (WeaponEntity we : weL) {
					%>
						<option value="<%=we.getKey()%>" <% if(b.getWeaponKeys().get(0).equals(we.getKey())) { %>selected<% } %> ><%=we.getWeapon().getWeaponName()%></option>
					<%
						}
					%>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align:center">
					Frame: <br />
					<select name="frame">
					<%
						List<FrameEntity> feL = GeneralInfo.getAvailableFrame(user);
									for (FrameEntity fe : feL) {
					%>
						<option value="<%=fe.getKey()%>"  <% if(b.getFrameKey().equals(fe.getKey())){%>selected<%} %>><%=fe.getFrame().getFrameName()%></option>
					<% 	}	%>
					</select>
				</td>				
			</tr>
		</table>
		<div width="800px" style="text-align:right">
			<input type="submit" value="Apply Changes" />
		</div>
	</form>
</div> 	 
</body>
</html>