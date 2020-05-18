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
<%--    <script type="text/javascript" src="WEB-INF/js/jquery-3.4.1.js"></script>--%>
    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
    <script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <title>企业资产管理系统</title>
    <style type="text/css">
        #myid {
            text-align: center;
            font-size: larger;
            font-weight: bold;
            height: 50px;
        }
    </style>
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
<script type="text/javascript">
    // 利用Ajax获取资产卡片信息
    function getDatas() {
        var id = $("#fyZldz").val();
        $.post("${pageContext.request.contextPath}/CardAjaxServlet", {"cardId": id}, function (callback) {
            //alert(callback);
            var jsonObj = JSON.parse(callback);
            $("#ad_cardcode").html(jsonObj[0].ad_cardcode);
            $("#ad_serial").html(jsonObj[0].ad_serial);
            $("#ad_code").html(jsonObj[0].ad_code);
            $("#ad_avno").html(jsonObj[0].ad_avno);
            $("#ad_num").html(jsonObj[0].ad_num);
            $("#ad_price").html(jsonObj[0].ad_price + "元");
            $("#ad_status").html(jsonObj[0].ad_status);
            $("#asty_name").html(jsonObj[0].asty_name);
            $("#ass_name").html(jsonObj[0].ass_name);
            $("#ass_model").html(jsonObj[0].ass_model);
            $("#ass_fincode").html(jsonObj[0].ass_fincode);
            $("#ass_unit").html(jsonObj[0].ass_unit);
            $("#av_findate").html(jsonObj[0].av_findate);
            $("#av_insttime").html(jsonObj[0].av_insttime);
            $("#u_name").html(jsonObj[0].u_name);

        });

    }
</script>
<form id="submitForm" name="submitForm" action="${pageContext.request.contextPath}/AssetsQueryServlet?page=1" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom" style="text-align: left">
                        请输入资产卡片编号：
                        <input type="text" id="fyZldz" name="asscard" class="ui_input_txt02" value=""/>
                        <input type="button" value="查询" class="ui_input_btn01" onclick="getDatas()"/>

                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0" id="tbl">
                    <tr id="myid">
                        <td style="width: 100px">卡片编号</td>
                        <td id="ad_cardcode" colspan="5"></td>
                    </tr>


                    <tr id="myid">

                        <td style="width: 100px">资产编号</td>
                        <td id="ad_code"></td>
                        <td style="width: 100px">资产名称</td>
                        <td id="ass_name"></td>
                        <td style="width: 100px">类别</td>
                        <td id="asty_name"></td>
                    </tr>

                    <tr id="myid">
                        <td >序列号</td>
                        <td id="ad_serial"></td>
                        <td style="width: 100px">单位</td>
                        <td id="ass_unit"></td>
                        <td >规格型号</td>
                        <td id="ass_model"></td>
                    </tr>
                    <tr id="myid">
                        <td style="width: 100px">凭证号</td>
                        <td id="ad_avno"></td>
                        <td style="width: 100px">单价</td>
                        <td id="ad_price"></td>
                        <td id="xxx" colspan="2"></td>
                    </tr>

                    <tr id="myid">
                        <td style="width: 100px">财务编码</td>
                        <td id="ass_fincode"></td>
                        <td id="myid" colspan="4"></td>
                    </tr>
                    <tr id="myid">
                        <td >入帐日期</td>
                        <td id="av_findate"></td>
                        <td >出帐日期</td>
                        <td id="av_insttime"></td>
                        <td id="myid" colspan="2"></td>
                    </tr>
                    <tr id="myid">
                        <td style="width: 100px">使用人</td>
                        <td id="u_name"></td>
                        <td style="width: 100px">数量</td>
                        <td id="ad_num"></td>
                        <td style="width: 100px">资产状态</td>
                        <td id="ad_status"></td>
                    </tr>
                </table>
            </div>
            <div class="ui_tb_h30">
                <div class="ui_frt">
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>


