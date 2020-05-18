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
            $("#submitForm").attr("action", "${pageContext.request.contextPath}/AssetsQueryServlet?page=" + page).submit();
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
<form id="submitForm" name="submitForm" action="${pageContext.request.contextPath}/AssetsQueryServlet?page=1&qSign=Q" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom" style="text-align: left">
                        条件查询
                        <select name="sele" id="fyXq" class="ui_select01" onchange="getFyDhListByFyXqCode();"
                            >
                            <option value=""
                            >--请选择--</option>
                            <option value="ad_code">资产编号</option>
                            <option value="ass_name">资产名称</option>
                            <option value="ass_model">规格型号</option>
                            <option value="av_insttime">入库时间</option>
                            <option value="ad_status">资产状态</option>
                            <option value="u_name">使用人</option>
                        </select>
                        <input type="text" id="fyZldz" name="vals" class="ui_input_txt02" value=""/>
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="所有资产" class="ui_input_btn01" id="importBtn"
                               onclick="location.href='${pageContext.request.contextPath}/AssetsQueryServlet?page=1&qSign=Q'"/>

                        <input type="button" value="导出" class="ui_input_btn01"
                               onclick= "location.href='${pageContext.request.contextPath}/AssetsQueryServlet?page=1&qSign=E'"/>
                        <span id="showTxt">${flag}</span>
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
                        <th>序列号</th>
                        <th>资产名称</th>
                        <th>资产类别</th>
                        <th>规格型号</th>
                        <th>单位</th>
                        <th>单价</th>
                        <th>凭证号</th>
                        <th>财务编码</th>
                        <th>入帐日期</th>
                        <th>入库日期</th>
                        <th>使用人</th>
                        <th>资产状态</th>

                    </tr>
                    <c:if test="${!empty(query)}">
                    <c:forEach var="pur"  items="${query}" begin="${current*10-10}" end="${current*10-1}">

                    <tr>
                        <td><input type="checkbox" name="IDCheck" value="${pur.ad_id}" class="acb" /></td>
                        <td>${pur.ad_code}</td>
                        <td>${pur.ad_cardcode}</td>
                        <td>${pur.ad_serial}</td>
                        <td>${pur.ass_name}</td>
                        <td>${pur.asty_name}</td>
                        <td>${pur.ass_model}</td>
                        <td>${pur.ass_unit}</td>
                        <td>${pur.ad_price}</td>
                        <td>${pur.ad_avno}</td>
                        <td>${pur.ass_fincode}</td>
                        <td>${pur.av_findate}</td>
                        <td>${pur.av_insttime}</td>
                        <td>${pur.u_name}</td>
                        <td>${pur.ad_status}</td>



                    </tr>
                    </c:forEach>

                    </c:if>

                </table>
            </div>
            <div class="ui_tb_h30">
                <div class="ui_flt" style="height: 30px; line-height: 30px;">
                    共有
                    <span class="ui_txt_bold04">${count}</span>
                    条记录，当前第
                    <span class="ui_txt_bold04">${current}
						/
						${pages}</span>
                    页
                </div>
                <div class="ui_frt">
                    <!--    如果是第一页，则只显示下一页、尾页 -->

                    <input type="button" value="首页" class="ui_input_btn01" />
                    <input type="button" value="上一页" class="ui_input_btn01"
                           onclick="jumpNormalPage(${current} - 1);"/>
                    <input type="button" value="下一页" class="ui_input_btn01"
                           onclick="jumpNormalPage(${current} + 1);" />
                    <input type="button" value="尾页" class="ui_input_btn01"
                           onclick="jumpNormalPage(9);" />



                    <!--     如果是最后一页，则只显示首页、上一页 -->

                    转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" />页
                    <input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage(9);" />
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>

