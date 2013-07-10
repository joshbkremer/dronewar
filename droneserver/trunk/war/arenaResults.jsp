<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.botgame.db.entity.ArenaResultEntity" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<body>
<table>
Arena Results
<tr>
	<%
    	List<ArenaResultEntity> areList = (List<ArenaResultEntity>) 
    	request.getAttribute("ArenaResultEntitys");
		for (ArenaResultEntity are : areList)
		{
	%>
	<td>
		Winner: <%=are.getWinner() %> 
	</td>
	<%  } %>
</tr>
</table>
</body>
</html>