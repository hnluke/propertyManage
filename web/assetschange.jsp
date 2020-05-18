<%--
  Created by IntelliJ IDEA.
  User: Luke
  Date: 2020/3/13
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>企业资产管理系统</title>
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
            indexs = ${current};
            if(page == 2){
                indexs = ${current} + 1;

            }else{
                if(indexs > 1) {
                    indexs =  indexs - 1;
                }
            }

            $("#submitForm").attr("action", "${pageContext.request.contextPath}/AssetsQueryServlet?page=" + indexs).submit();
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
                    <div id="box_top">资产异动</div>
                    </div>
                    <div id="box_bottom" style="text-align: left">
                        选择操作：
                        <select name="sele" id="fyXq" class="ui_select01" onchange="getChDatas();">
                            <option value=""
                            >--请选择--</option>
                            <option value="领用">领用</option>
                            <option value="归还">归还</option>
                            <option value="返修">返修</option>
                            <option value="报废">报废</option>
                            <option value="销帐">销帐</option>
                        </select>
                        用户名：
                        <select name="seleuser" id="fyXq2" class="ui_select01">
                            <option value="0"
                            >--请选择--</option>

                        </select>
                        <input type="button" value="确定" class="ui_input_btn01" id="btn" onclick="getResult();"/>
                        <span id="showTxt"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" />
                        </th>
                        <th>资产编号</th>
                        <th>卡片编号</th>
                        <th>资产名称</th>
                        <th>资产类别</th>
                        <th>规格型号</th>
                        <th>数量</th>
                        <th>使用人</th>
                        <th>资产状态</th>

                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>

</body>
<script type = "text/javascript">
    $(function () {

        $("#fyXq").click(function () {
            var id = "users";
            $.post("${pageContext.request.contextPath}/AssetsAjaxServlet", {"tages" : id}, function (callback) {
                var jsonObj = JSON.parse(callback);
                //alert(callback);
                $("#fyXq2").empty();
                var option = $("<option />");
                option.html("请选择");
                option.val(0);
                $("#fyXq2").append(option);
                for(var i = 0; i < jsonObj.length; i++) {
                    option = $("<option />");
                    option.html(jsonObj[i].u_name);
                    option.val(jsonObj[i].u_id);
                    $("#fyXq2").append(option);
                }

            });

        });
    });

    // 资产异动操作
    function getResult() {

        var tages = $("#fyXq").val();
        var str = "";
        $("input[name='IDCheck']:checked").each(function () {
            str = str + $(this).val() + ",";
        });
        var u_id = $("#fyXq2").val();
        if (str == "") {
            alert("您还没有选择任何资产");
        } else if(u_id == 0) {
            alert("请选择用户");
        } else{
            $.post("${pageContext.request.contextPath}/AssetsChangeAjaxServlet",
            {"tages" : tages, "sele" : str, "usr" : u_id}, function (callback) {
                $("#showTxt").text(callback);
                showData(tages);
            });
        }
    }

    /**
     * 显示数据
     * @param tages
     */
    function showData(tages) {
        $.post("${pageContext.request.contextPath}/AssetsAjaxServlet", {"tages" : tages}, function (callback) {
            jsonObj = JSON.parse(callback);
            var html = "";
            html = "<tr>\n" +
                "<th width=\"30\"><input type=\"checkbox\" id=\"all\" onclick=\"selectOrClearAllCheckbox(this);\" /></th>" +
                "<th>资产编号</th>" +
                "<th>卡片编号</th>" +
                "<th>资产名称</th>" +
                "<th>资产类别</th>" +
                "<th>规格型号</th>" +
                "<th>数量</th>" +
                "<th>使用人</th>" +
                "<th>资产状态</th></tr>"

            var html2 = "";

            for(var i= 0; i < jsonObj.length; i++) {
                html2 = html2 + "<tr>"
                html2 = html2 +  "<td><input type=\"checkbox\" name=\"IDCheck\" value=\"" + jsonObj[i].ad_id + "\" class=\"acb\" /></td>"
                html2 = html2 + "<td>" + jsonObj[i].ad_code + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].ad_cardcode + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].ass_name + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].asty_name + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].ass_model + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].ad_num + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].u_name + "</td>";
                html2 = html2 + "<td>" + jsonObj[i].ad_status + "</td>";
                html2 = html2 + "</tr>";
            }

            $("table").html(html + html2);
        });

    }

    function getChDatas() {
        var tages = $("#fyXq").val();
        showData(tages);
        $("#showTxt").text("");
        $("#btn").val($("#fyXq").val());

    }
</script>
</html>

