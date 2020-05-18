<%--
  Created by IntelliJ IDEA.
  User: Luke
  Date: 2020/3/12
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>SCT-后台系统</title>
    <link href="style/authority/login_css.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <script type="text/javascript">



        function EnterPress(e){ //传入 event
            var e = e || window.event;
            if(e.keyCode == 13){
                $("#submitForm").attr("action", "index.html").submit();
            }
        }
    </script>
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form action ="${pageContext.request.contextPath}/LoginServlet?login=1"  method="post">
                    <div id="login_tip">
                        <span id="login_err" class="sty_txt2"></span>
                    </div>
                    <div>
                        用户名：<input type="text" name="uname" class="username" id="name">
                    </div>
                    <div>
                        密&nbsp;&nbsp;码：<input type="password" name="pwd" class="pwd" id="pwd">

                    </div>
                    <div id="btn_area">

                        <input type="submit" class="login_btn" id="login_sub" value="登 录" />
                        <input type="reset" class="login_btn" id="login_ret" value="重 置" />
                    </div>
                    <br/>

                </form>
                <p>${err}</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>
