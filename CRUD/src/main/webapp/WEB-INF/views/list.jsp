<%--
  Created by IntelliJ IDEA.
  User: 25406
  Date: 2020/2/1
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".delete").click(function () {
            var href=$(this).attr("href");
            $("form").attr("action",href).submit();
            return false;
            alert("hello");
        });
    })
</script>
</head>
<body>
    <form action="" method="post">
        <input type="hidden" name="_method" value="DELETE">
    </form>
    <c:if test="${empty requestScope.employees}">
        没有任何员工信息.
    </c:if>
    <c:if test="${!empty requestScope.employees}">
        <table border="1" cellpadding="10" cellspacing="0">
            <tr>
                <th>id</th>
                <th>lastName</th>
                <th>Email</th>
                <th>gender</th>
                <th>department</th>
                <th>edit</th>
                <th>delete</th>
            </tr>
            <c:forEach var="emp" items="${requestScope.employees}">
                <tr>
                    <td>${emp.id}</td>
                    <td>${emp.lastName}</td>
                    <td>${emp.email}</td>
                    <td>${emp.gender==0?'female':'male'}</td>
                    <td>${emp.department.departmentName}</td>
                    <td><a href="emp/${emp.id}">edit</a></td>
                    <td><a class="delete" href="emp/${emp.id}">delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br><br>
    <a href="emp">add new employee</a>
</body>
</html>
