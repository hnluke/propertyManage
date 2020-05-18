package com.servlet;

import com.factory.AssetsDaoFactory;
import com.pojo.AllAssetsItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AssetsQueryServlet")
public class AssetsQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 得到选择下拉框和条件值
        String sele = request.getParameter("sele");
        String vals = request.getParameter("vals");
        String page = request.getParameter("page");
        String qSign = request.getParameter("qSign");
        Integer pages = (Integer) request.getSession().getAttribute("pages");
        int recordCount = 0;
        List<AllAssetsItem> list;
        if(pages == null) {
            pages = 1;
        }
        if(page == null) {
            page = "1";
        }
        if(sele == null ) {
            sele = "";
            vals = "";
        }

        //int pages_int = Integer.parseInt(pages);
        int page_int = Integer.parseInt(page);


        if("Q".equals(qSign)) {
            // 查询符合条件的数据
            list = new ArrayList<AllAssetsItem>();
            list = AssetsDaoFactory.getAssetsServiceImpl().findAssets(sele, vals, "", page_int);
            request.getSession().setAttribute("query", list);
            recordCount = AssetsDaoFactory.getAssetsServiceImpl().getAssetsCount(sele, vals, "", page_int);

            if((recordCount % 10) == 0) {
                pages = recordCount / 10;
            }else{
                pages= recordCount /10 + 1;
            }
            request.getSession().setAttribute("count", recordCount);
            request.getSession().setAttribute("pages", pages);

        }else if ("E".equals(qSign)) {
            List<AllAssetsItem> list2 = (List<AllAssetsItem>) request.getSession().getAttribute("query");
            String notice = AssetsDaoFactory.getAssetsServiceImpl().exportAssetsToExcel(list2, "assets.xls");
            request.setAttribute("flag", notice);
//            request.getRequestDispatcher("assetsquery.jsp").forward(request, response);
//            return;
            // 导出数据到Excel
        }
        if(page_int < 1) {
            page_int = 1;
        }else if(page_int > pages) {
            page_int = pages;
        }
        request.setAttribute("current", page_int);
        request.getRequestDispatcher("assetsquery.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
