<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: 25406
  Date: 2020/2/1
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="for" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!--
        1.为什么使用form标签呢?
        可以更快速的开发出表单页面,而且可以更方便的进行表单值的回显
        2.注意:
        可以通过modelAttribute属性指定绑定的模型属性
        若没有指定该属性,则默认从request域对象中读取command的表单bean
        如果该属性值也不存在,则会发生错误
    -->
    <form:form action="${pageContext.request.contextPath}/emp" method="post" modelAttribute="employee">
<%--        <form:errors path="*"></form:errors>--%>
        <c:if test="${employee.id==null}">
            <!--path属性对应HTML表单标签的name属性值-->
            lastName:<form:input path="lastName"/><br>
<%--            <form:errors path="lastName"></form:errors>--%>
        </c:if>
        <c:if test="${employee.id!=null}">
            <form:hidden path="id"/>
            <%--对于_method不能使用form:hidden标签,因为modelAttribute对应的bean中没有_method这个属性--%>
            <input type="hidden" name="_method" value="PUT" />
        </c:if>
        email:<form:input path="email"/><br>
        <%
            Map<String,String> genders=new HashMap<>();
            genders.put("1","male");
            genders.put("0","female");
            request.setAttribute("genders",genders);
        %>

        gender:<br>
        <form:radiobuttons path="gender" items="${genders}"/><br>
        <!--itemLabel:指定radio的label值,itemValue:指定radio的value值-->
        department:<form:select path="department.id" items="${departments}"
                        itemLabel="departmentName" itemValue="id"/><br>
        <!--
            1.数据类型转换
            2.数据类型格式化
            3.数据校验
             1).如何校验?注解
                ①.使用JSR 303验证标准
                ②.加入hibernate Validator验证框架的jar包
                ③.在springmvc上下文中添加<mvc:annotation-driven/>注解
                ④.需要在bean的属性上添加对应的注解
                ⑤.在目标方法bean类型的前面添加@Valid注解
             2).验证出错转向哪一个页面
                注意:需要校验的bean对象和其绑定结果对象或错误对象是成对出现的,他们之间不允许声明其他入参
             3).错误消息?如何显示,如何把错误消息国际化
        -->
        birth:<form:input path="birth"/><br>
<%--        <form:errors path="birth"></form:errors>--%>
        salary:<form:input path="salary"/><br>
<%--        <form:errors path="salary"></form:errors>--%>
        <input type="submit" value="submit">
    </form:form>

    <form action="${pageContext.request.contextPath}/testConversionServiceConverer" method="post">
        <%--lastName-email-gender-department.id 例如:gg-gg@atguigu.com-0-105--%>
        employee:<input type="text" name="employee">
        <input type="submit" value="submit">
    </form>
</body>
</html>