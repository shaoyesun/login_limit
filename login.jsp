<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 16-10-8
  Time: 下午1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="/other/login" method="post">
        user name:<input type="text" name="userName"><span id="mess">${message}</span><br><br>
        user pass:<input type="text" name="password"><br><br>
        <input type="submit" value="Login">${userName}
    </form>
    <input type="hidden" name="userName" value="${userName}" id="un">

<script src="http://js.biocloud.cn/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var message='${mess}';
        if (message != "") {
            $.ajax({
                       type: 'GET',
                       async: false,
                       cache: false,
                       url: '/other/clearUserSession',
                       dataType: '',
                       data: {},
                       success: function (data) {
                       }
                   });
            $('#mess').html(message);
        }
    });
</script>
</body>
</html>
