<html>
<body>
    <a href="helloWorld">Hello World</a><br><br>

    <a href="springmvc/testMethod">test method</a><br><br>

    <form action="springmvc/testMethod" method="post">
        <input type="submit" value="submit">
    </form>

    <a href="springmvc/testParamsAndHeaders?username=atguigu&age=11">test ParamsAndHeaders</a><br><br>

    <a href="springmvc/testAndPath/xxxx/abc">test AndPath</a><br><br>

    <a href="springmvc/testPathVariable/1">test PathVariable</a><br><br>


    <a href="springmvc/testRest/1">testRest get</a><br>
    <form action="springmvc/testRest" method="post">
        <input type="submit" value="testRest post">
    </form>
    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="testRest delete">
    </form>
    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="testRest put">
    </form>


    <a href="springmvc/testRequestParam?username=atguigu&age=11">test RequestParam</a><br>
    <a href="springmvc/testRequestHeader">test RequestHeader</a><br>
    <a href="springmvc/testCookieValue">testCookieValue</a><br>

    <form action="springmvc/testPojo" method="post">
        username:<input type="text" name="username"><br>
        password:<input type="password" name="password"><br>
        email:<input type="text" name="email"><br>
        age:<input type="text" name="age"><br>
        city:<input type="text" name="address.city"/><br>
        province:<input type="text" name="address.province"/><br>
        <input type="submit" value="submit"/>
    </form>

    <a href="springmvc/testServletApi">test ServletApi</a><br>
    <a href="springmvc/testModelAndView">test ModelAndView</a><br>
    <a href="springmvc/testMap">test Map</a><br>
    <a href="springmvc/testSessionAttributes">test SessionAttributes</a><br>

    <%--
        模拟修改操作
        1.原始数据为:1,tom,123,tom@atguigu.com,12
        2.密码不能被修改
        3.表单回显,模拟操作直接在表单填写对应的属性值
    --%>
    <form action="springmvc/testModelAttribute" method="post">
        <input type="hidden" name="id" value="1">
        username:<input type="text" name="username" value="tom"><br>
        email:<input type="text" name="email" value="tom@atguigu.com"><br>
        age:<input type="text" name="age" value="15"><br>
        <input type="submit" value="submit"><br>
    </form>

    <a href="springmvc/testViewAndViewResolver">test ViewAndViewResolver</a><br>
    <a href="springmvc/testView">test View</a><br>

    <a href="springmvc/testRedirect">test Redirect</a>
</body>
</html>
