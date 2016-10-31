<!--国际化-->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%--<meta http-equiv="refresh" content="0;url=/test1">--%>
</head>
<body onload="findAll()">
<form action="/user/register" method="post" id="form1">
    name:<input type="text" name="userName" id="name"><span id="message">${message}</span><br><br>
    pass:<input type="text" name="password" id="pass"><br><br>
    <input type="button" value="add" onclick="add()"><br><br>
</form>
<h5>---------------------------log out------------------------------</h5>
&nbsp;&nbsp;<a href="/user/loginOut">login out</a><br><br>
<h5>-------------------------all user--------------------------------</h5>
&nbsp;&nbsp;<input type="button" value="findAll" onclick="findAll()"><br>
<div id="alluser"></div>
<div id="edituser"></div>
<h5>----------------------------------login redirect test--------------------------------</h5>
<form action="/user/loginRedirect">
    <input type="submit" value="login redirect">
</form>
<script src="http://js.biocloud.cn/jquery/1.11.3/jquery.min.js"></script>
<script>
    function add() {
        findAll();
        var userName = $("#name").val();
        var password = $("#pass").val();
        if (userName == "" || password == "") {
            $("#message").html("please put infomation!");
        } else {
            $.ajax({
               type: "POST",
               data:{userName : userName, password : password},
               url: "/user/register",
               success: function (data) {
                   if(data == "existed"){
                       $("#message").html("existed!");
                   } else {
                       $("#message").html("add success!");
                   }
                   findAll();
               }
           });
        }
    }

    function findAll(){
        $.ajax({
           type: "POST",
           url: "/user/findAll",
           success: function (data) {
               $("#alluser").html("");
               var str = "";
               $.each(data, function (index, item) {
                    str += "<table><tr><td>"+item.userName+"</td>"+
                           "<td>"+item.password+"</td>"+
                           "<td><input type=\"button\" value=\"delete\" onclick=\"del("+item.id+")\"></td>"+
                           "<td><input type=\"button\" value=\"edit\" onclick=\"editShow("+item.id+",'"+item.userName+"','"+item.password+"')\"></td></tr></table>";
               });
               $("#alluser").html(str);
           }
       });
    }

    function del(id){
        $.ajax({
           type: "POST",
           data:{id: id},
           url: "/user/del",
           success: function (data) {
               alert(data);
               findAll();
           }
       });
    }

    function editShow(id, userName, password){
        $("#edituser").html("");
        var str = "<br><h5>---------------------------edit------------------------------</h5>"+
                  "name:<input type=\"text\" name=\"userName\" id=\"userName\" value="+ userName +"><label id=\"exist\"></label><br><br>"+
                  "pass:<input type=\"text\" name=\"password\" id=\"password\"  value="+ password +"><br><br>"+
                  "<input type=\"button\" value=\"edit\" onclick=\"edit("+id+")\">";
        $("#edituser").html(str);
    }

    function edit(id){
        var userName = $("#userName").val();
        var password = $("#password").val();
        if (userName == "" || password == "") {
            alert("please put infomation!");
        } else {
            $.ajax({
               type: "POST",
               data:{id: id, userName:userName, password:password},
               url: "/user/edit",
               success: function (data) {
                   if(data == "existed"){
                       $("#exist").html("existed");
                   }else{
                       $("#exist").html("");
                       alert(data);
                       $("#edituser").html("");
                       findAll();
                   }
               }
           });
        }
    }
</script>
</body>
</html>
