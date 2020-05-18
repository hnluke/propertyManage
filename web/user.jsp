<%--
  Created by IntelliJ IDEA.
  User: Luke
  Date: 2020/3/17
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
    <script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <title>信息管理系统</title>
    <script type="text/javascript">
        $(document).ready(function(){
            /** 新增   **/
            $("#addBtn").fancybox({
                'href'  : 'house_edit.html',
                'width' : 733,
                'height' : 530,
                'type' : 'iframe',
                'hideOnOverlayClick' : false,
                'showCloseButton' : false,
                'onClosed' : function() {
                    window.location.href = 'house_list.html';
                }
            });

            /** 导入  **/
            $("#importBtn").fancybox({
                'href'  : '/xngzf/archives/importFangyuan.action',
                'width' : 633,
                'height' : 260,
                'type' : 'iframe',
                'hideOnOverlayClick' : false,
                'showCloseButton' : false,
                'onClosed' : function() {
                    window.location.href = 'house_list.html';
                }
            });

            /**编辑   **/
            $("a.edit").fancybox({
                'width' : 733,
                'height' : 530,
                'type' : 'iframe',
                'hideOnOverlayClick' : false,
                'showCloseButton' : false,
                'onClosed' : function() {
                    window.location.href = 'house_list.html';
                }
            });
        });
        /** 用户角色   **/
        var userRole = '';

        /** 模糊查询来电用户  **/
        function search(){
            $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
        }

        /** 新增   **/
        function add(){
            $("#submitForm").attr("action", "/xngzf/archives/luruFangyuan.action").submit();
        }

        /** Excel导出  **/
        function exportExcel(){
            if( confirm('您确定要导出吗？') ){
                var fyXqCode = $("#fyXq").val();
                var fyXqName = $('#fyXq option:selected').text();
//	 		alert(fyXqCode);
                if(fyXqCode=="" || fyXqCode==null){
                    $("#fyXqName").val("");
                }else{
//	 			alert(fyXqCode);
                    $("#fyXqName").val(fyXqName);
                }
                $("#submitForm").attr("action", "/xngzf/archives/exportExcelFangyuan.action").submit();
            }
        }

        /** 删除 **/
        function del(fyID){
            // 非空判断
            if(fyID == '') return;
            if(confirm("您确定要删除吗？")){
                $("#submitForm").attr("action", "/xngzf/archives/delFangyuan.action?fyID=" + fyID).submit();
            }
        }

        /** 批量删除 **/
        function batchDel(){
            if($("input[name='IDCheck']:checked").size()<=0){
                art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'至少选择一条', ok:true,});
                return;
            }
            // 1）取出用户选中的checkbox放入字符串传给后台,form提交
            var allIDCheck = "";
            $("input[name='IDCheck']:checked").each(function(index, domEle){
                bjText = $(domEle).parent("td").parent("tr").last().children("td").last().prev().text();
// 			alert(bjText);
                // 用户选择的checkbox, 过滤掉“已审核”的，记住哦
                if($.trim(bjText)=="已审核"){
// 				$(domEle).removeAttr("checked");
                    $(domEle).parent("td").parent("tr").css({color:"red"});
                    $("#resultInfo").html("已审核的是不允许您删除的，请联系管理员删除！！！");
// 				return;
                }else{
                    allIDCheck += $(domEle).val() + ",";
                }
            });
            // 截掉最后一个","
            if(allIDCheck.length>0) {
                allIDCheck = allIDCheck.substring(0, allIDCheck.length-1);
                // 赋给隐藏域
                $("#allIDCheck").val(allIDCheck);
                if(confirm("您确定要批量删除这些记录吗？")){
                    // 提交form
                    $("#submitForm").attr("action", "/xngzf/archives/batchDelFangyuan.action").submit();
                }
            }
        }

        /** 普通跳转 **/
        function jumpNormalPage(page){
            $("#submitForm").attr("action", "house_list.html?page=" + page).submit();
        }

        /** 输入页跳转 **/
        function jumpInputPage(totalPage){
            // 如果“跳转页数”不为空
            if($("#jumpNumTxt").val() != ''){
                var pageNum = parseInt($("#jumpNumTxt").val());
                // 如果跳转页数在不合理范围内，则置为1
                if(pageNum<1 | pageNum>totalPage){
                    art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
                    pageNum = 1;
                }
                $("#submitForm").attr("action", "house_list.html?page=" + pageNum).submit();
            }else{
                // “跳转页数”为空
                art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
                $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
            }
        }
    </script>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="submitForm" name="submitForm" action="" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">用户管理</div>
                    <div id="box_center" style="text-align: center;font-weight: bold;font-size: large;">
                        用户管理
                        <!-- 小区
                        <select name="fangyuanEntity.fyXqCode" id="fyXq" class="ui_select01" onchange="getFyDhListByFyXqCode();">
                            <option value=""
                            >--请选择--</option>
                            <option value="6">瑞景河畔</option>
                            <option value="77">蔚蓝小区</option>
                            <option value="83">和盛园小区</option>
                        </select> -->

                        <!-- 栋号
                        <select name="fangyuanEntity.fyDhCode" id="fyDh" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select> -->
                        <!-- 	户型
                            <select name="fangyuanEntity.fyHxCode" id="fyHx" class="ui_select01">
                                <option value="">--请选择--</option>
                                <option value="76">一室一厅一卫</option>
                                <option value="10">两室一厅一卫</option>
                                <option value="14">三室一厅一卫</option>
                                <option value="71">三室两厅一卫</option>
                            </select> -->
                        <!-- 状态
                        <select name="fangyuanEntity.fyStatus" id="fyStatus" class="ui_select01">
                            <option value="">--请选择--</option>
                            <option value="26">在建</option>
                            <option value="25">建成待租</option>
                            <option value="13">已配租</option>
                            <option value="12">已租赁</option>
                            <option value="24">腾退待租</option>
                            <option value="23">欠费</option>
                            <option value="27">其他</option>
                        </select> -->

                        <!-- 座落&nbsp;&nbsp;<input type="text" id="fyZldz" name="fangyuanEntity.fyZldz" class="ui_input_txt02" /> -->
                    </div>
                    <div id="box_bottom" style="text-align: left;">
                        <input type="button" value="查询" class="ui_input_btn01" onclick="showData('Q');" />
                        <input type="button" value="复位密码" class="ui_input_btn01" id="resetBtn"  onclick="resetPwd();"/>
                        <span id="showTxt"></span>
                        <!-- <input type="button" value="删除" class="ui_input_btn01" onclick="batchDel();" />
                        <input type="button" value="导入" class="ui_input_btn01" id="importBtn" />
                        <input type="button" value="导出" class="ui_input_btn01" onclick="exportExcel();" /> -->
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <span id="alteruser">
                    用户名：<lable id = "u_name"></lable>
                    密码：<input type="text" id="u_pwd" class="ui_input_txt02"/>
                    电话：<input type="text" id="u_tele" class="ui_input_txt02"/>
                    权限：<select name="text" id="u_prio" class="ui_select01">
                            <option value="0">--请选择--</option>
                            <option value="C">普通用户</option>
                            <option value="S">系统用户</option>
                        </select>
                    &nbsp;&nbsp;锁定:&nbsp;&nbsp;<input type="checkbox" id="u_lock" />
                    &nbsp;&nbsp;<input type="button" class = "ui_input_btn01" value="提交"
                                       onclick="alterUser2();"/>

                </span>

                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" />
                        </th>
                        <th>用户名</th>
                        <th>密码</th>
                        <th>权限</th>
                        <th>电话号码</th>
                        <th>是否锁定</th>
                        <th>操作</th>
                    </tr>
<%--                    <tr>--%>
<%--                        <td><input type="checkbox" name="IDCheck" value="14458579642011" class="acb" /></td>--%>
<%--                        <td>城中区</td>--%>
<%--                        <td>瑞景河畔16号楼1-111</td>--%>
<%--                        <td>65.97㎡</td>--%>
<%--                        <td>65.97㎡</td>--%>
<%--                        <td>一室一厅一卫</td>--%>

<%--                        <td>--%>
<%--                            <a href="house_edit.html?fyID=14458579642011" class="edit">修改</a>--%>
<%--                            <a href="javascript:del('14458579642011');">删除</a>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
                </table>
            </div>
<%--            <div class="ui_tb_h30">--%>
<%--                <div class="ui_flt" style="height: 30px; line-height: 30px;">--%>
<%--                    共有--%>
<%--                    <span class="ui_txt_bold04">90</span>--%>
<%--                    条记录，当前第--%>
<%--                    <span class="ui_txt_bold04">1--%>
<%--						/--%>
<%--						9</span>--%>
<%--                    页--%>
<%--                </div>--%>
<%--                <div class="ui_frt">--%>
<%--                    <!--    如果是第一页，则只显示下一页、尾页 -->--%>

<%--                    <input type="button" value="首页" class="ui_input_btn01" />--%>
<%--                    <input type="button" value="上一页" class="ui_input_btn01" />--%>
<%--                    <input type="button" value="下一页" class="ui_input_btn01"--%>
<%--                           onclick="jumpNormalPage(2);" />--%>
<%--                    <input type="button" value="尾页" class="ui_input_btn01"--%>
<%--                           onclick="jumpNormalPage(9);" />--%>
<%--                    <!--     如果是最后一页，则只显示首页、上一页 -->--%>
<%--                    转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" />页--%>
<%--                    <input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage(9);" />--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
    </div>
</form>

</body>
<script type="text/javascript">
    function showData(tages) {
       //alert(tages);
        $.post("${pageContext.request.contextPath}/UserAjaxServlet", {"tages" : tages}, function (callback) {
            var jsonObj = JSON.parse(callback);

            var html = "";
            html = "<tr>" +
                "<th width=\"30\"><input type=\"checkbox\" id=\"all\" onclick=\"selectOrClearAllCheckbox(this);\" /></th>" +
                "<th>用户名</th>" +
                "<th>密码</th>" +
                "<th>权限</th>" +
                "<th>电话号码</th>" +
                "<th>是否锁定</th>" +
                "<th>操作</th></tr>"
            var html2 = "";
            for(var i= 0; i < jsonObj.length; i++) {
                html2 = html2 + "<tr>"
                html2 = html2 +  "<td><input type=\"checkbox\" name=\"IDCheck\" value=\"" + jsonObj[i].u_id + "\" class=\"acb\" /></td>"
                html2 = html2 + "<td>" + jsonObj[i].u_name + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].u_pwd + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].u_prio + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].u_tele + "</td>";
                html2 = html2 + "<td>" + (jsonObj[i].u_lock ? '是' : '否') + "</td>";
                html2 = html2 +
                    "<td><a href=\"javascript:alterUser(" + jsonObj[i].u_id + ");\" class=\"edit\">修改</a>&nbsp;&nbsp;" +
                    "<a href=\"javascript:deleUser(" + jsonObj[i].u_id + ");\">删除</a>" +
                    "</td>";
                html2 = html2 + "</tr>";
            }

            $("table").html(html + html2);
        });
    }
    //复位密码
    function resetPwd(){
        var tages = "R";
        var str = "";
        $("input[name='IDCheck']:checked").each(function () {
            str = str + $(this).val() + ",";
        });
        if(str == "") {
            alert("您没有选择任何用户");
        }else{
            if(confirm("确实要复位此用户的密码吗?")) {
                $.post("${pageContext.request.contextPath}/UserAjaxServlet", {"tages" : tages, "sele" : str}, function (callback) {
                    $("#showTxt").text(callback);
                });
            }
        }
    }
    // 删除用户
    function deleUser(u_id) {
        var tages = "D";
        if(confirm("确实要删除此用户吗?")) {
            $.post("${pageContext.request.contextPath}/UserAjaxServlet", {"tages" : tages, "u_id" : u_id}, function (callback) {
                $("#showTxt").text(callback);
                showData("Q");
            });
        }
    }
    // 修改用户信息

    function alterUser(u_id) {
        var tages = "U";
        $.post("${pageContext.request.contextPath}/UserAjaxServlet", {"tages" : tages, "u_id" : u_id}, function (callback) {
            // $("#showTxt").text(callback);
            var jsonObj = JSON.parse(callback);
            $("#u_name").text(jsonObj.u_name);
            // $("#u_pwd").val(jsonObj.u_pwd);
            $("#u_tele").val(jsonObj.u_tele);
            $("#u_prio").val(jsonObj.u_prio);
            document.getElementById("u_lock").checked = jsonObj.u_lock;

            //showData("Q");
        });

    }

    function alterUser2() {
        var tages = "U2";
        if($.trim($("#u_name").text()) == "" || $.trim($("#u_pwd").val()) == "") {
            alert("用户或密码不能为空");
        } else{
            $.post("${pageContext.request.contextPath}/UserAjaxServlet",
                {"tages" : tages, "u_name" : $("#u_name").text(),
                 "u_pwd" : $("#u_pwd").val(), "u_tele" : $("#u_tele").val(),
                    "u_prio" : $("#u_prio").val(), "u_lock" : (document.getElementById("u_lock").checked ? "1" : "0")
                },
                function (callback) {
                    $("#showTxt").text(callback);
                    showData("Q");

                });
        }

    }
    


</script>
</html>

