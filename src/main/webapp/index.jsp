<%@ taglib uri="path_tag" prefix="path" %>
<html>
<head>
    <meta http-equiv="refresh" content="0;url=<path:contextpath/>/other/toLogin">
</head>
<body onload="findAll()">
<form action="/user/register" method="post" id="form1">
    name:<input type="text" name="userName" id="name"><br><br>
    pass:<input type="text" name="password" id="pass"><br><br>
    <input type="button" value="add" onclick="add()">
</form>
&nbsp;&nbsp;<input type="button" value="findAll" onclick="findAll()">
<div id="alluser"></div>
<div id="edituser"></div>

<script src="http://js.biocloud.cn/jquery/1.11.3/jquery.min.js"></script>
<script>
    function add() {
        findAll();
        var userName = $("#name").val();
        var password = $("#pass").val();
        if (userName == "" || password == "") {
            alert("please put infomation!");
        } else {
            window.document.getElementById("form1").submit();
            findAll();
        }
    }
    
    function findAll(){
        $.ajax({
           type: "POST",
           url: "/user/findAll",
           success: function (data) {
               $("#alluser").html("");
               var str = "<br><h1>---------------------------------------------------------</h1>";
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
        var str = "<br><h1>---------------------------------------------------------</h1>"+
                  "name:<input type=\"text\" name=\"userName\" id=\"userName\" value="+ userName +"><br><br>"+
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
                   alert(data);
                   $("#edituser").html("");
                   findAll();
               }
           });
        }


    }

</script>
</body>
</html>
