<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 25406
  Date: 2020/1/30
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Title</title>
</head>
<body>
    success<br>
    time:${requestScope.time}<br>
    names:${requestScope.names}<br>
    request user:${requestScope.user}<br>
    session user:${sessionScope.user}<br>

    <fmt:message key="username"></fmt:message><br>
    <fmt:message key="password"></fmt:message><br>
</body>
</html>
