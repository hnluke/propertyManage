package com.servlet;

import com.factory.AssetsDaoFactory;
import com.pojo.Purchase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String purchase = request.getParameter("purchase");
        System.out.println(purchase);
        List<Purchase> purchase_list = null;
        // 获取采购单数据表

        if("in".equalsIgnoreCase(purchase)) {
            // 资产入库
            String[] pid = request.getParameterValues("IDCheck");

            boolean flag = true;
            Integer int_id = 1;
            // 多选入库
            for(String id : pid) {
                int_id = Integer.parseInt(id);
                if(!AssetsDaoFactory.getAssetsServiceImpl().inStore(int_id)){
                    flag = false;
                }
            }
            if(flag) {
                request.setAttribute("flag", "入库成功");
            }else {
                request.setAttribute("flag", "入库失败");
            }
            purchase_list = AssetsDaoFactory.getPurchaseServiceImpl().getPurchase("");
        }else if("im".equals(purchase)) {
            // 采购数据导入
            String basePath = PurchaseServlet.class.getClassLoader().getResource("//").getPath();
            AssetsDaoFactory.getPurchaseServiceImpl().importExcelToDB(basePath + "purchase.xls");
            purchase_list = AssetsDaoFactory.getPurchaseServiceImpl().getPurchase("");
        }else if("ex".equals(purchase)) {
            //采购数据导出
            purchase_list = AssetsDaoFactory.getPurchaseServiceImpl().getPurchase("");
            //purchase_list = AssetsDaoFactory.getPurchaseServiceImpl().getPurchase("");
            String filePath = System.getProperty("user.dir");
            filePath = filePath + "purchase.xls";
            boolean flag = AssetsDaoFactory.getPurchaseServiceImpl().exportReport(purchase_list,filePath);
            if(flag) {
                request.setAttribute("flag", "导出成功，请查看文档" + filePath);
            }else{
                request.setAttribute("flag", "导出失败");
            }
        }
        request.setAttribute("purchase_list", purchase_list);
        request.getRequestDispatcher("purchase.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
