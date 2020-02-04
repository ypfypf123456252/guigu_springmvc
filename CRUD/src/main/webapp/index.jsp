<html>
<head>
    <script type="text/javascript">
        window.onload=function () {
            var a=document.getElementById("testJson");
            a.onclick=function () {
                var request=new XMLHttpRequest();
                var method="GET";
                var url=this.href;
                request.open(method,url);
                request.send(null);
                request.onreadystatechange=function () {
                    if (request.readyState==4){
                        if (request.status==200||request.status==304){
                            var data=request.responseText;
                            data=eval("("+data+")");
                            // alert(data);
                            for (var i=0;i<data.length;i++){
                                var id=data[i].id;
                                var lastName=data[i].lastName;
                                alert(id+":"+lastName);
                            }
                        }
                    }
                }
                return false;
            }
        }
    </script>
</head>
<body>
    <a href="${pageContext.request.contextPath}/emps">List All Employees</a><br>
    <a href="testJson" id="testJson">Test Json</a><br>

    <form action="testHttpMessageConverter" method="post" enctype="multipart/form-data">
        file:<input type="file" name="file">
        desc:<input type="text" name="desc">
        <input type="submit" value="submit">
    </form>

    <a href="testResponseEntity">test ResponseEntity</a>

    <!--
        关于国际化:
            1.在页面上能够根据浏览器的语言配置情况对文本(不是内容),时间,数值进行本地化处理
            2.可以在bean中获取国际化资源文件Locale对应的消息
            3.可以通过超链接切换Locale,而不再依赖于浏览器的语言设置情况
        解决:
            1.使用jstl的fmt标签
            2.在bean中注入ResourceBundleMessageSource的实例,使用其对应的getMessage方法即可
            3.配置LocaleResolver和LocaleChangeInterceptor
    -->
    <a href="i18n">i18n page</a><

    <form action="testFileUpload" method="post" enctype="multipart/form-data">
        file:<input type="file" name="file">
        desc:<input type="text" name="desc">
        <input type="submit" value="submit">
    </form>

    <a href="testExceptionHandlerExceptionResolver?i=10">test ExceptionHandlerExceptionResolver</a><br>

    <a href="testResponseStatusExceptionResolver?i=10">test ResponseStatusExceptionResolver</a><br>

    <a href="testSimpleMappingExceptionResolver?i=2">test SimpleMappingExceptionResolver</a><br>
</body>
</html>
