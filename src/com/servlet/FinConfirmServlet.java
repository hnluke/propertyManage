package com.servlet;

import com.dao.IAssets_vouDao;
import com.factory.AssetsDaoFactory;
import com.pojo.AllAssetsItem;
import com.pojo.Asserts_type;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FinConfirmServlet")
public class FinConfirmServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //System.out.println("就绪");
        String confirm = request.getParameter("co");
        String page = request.getParameter("page");


        List<AllAssetsItem>  list = null;
        if("1".equalsIgnoreCase(confirm)) {
            // 显示未确认的资产
            request.setAttribute("current", Integer.parseInt(page));


        }else if("2".equalsIgnoreCase(confirm)) {
            request.setAttribute("current", Integer.parseInt(page));
            // 资产确认
            String vou = request.getParameter("txt");
            String finCon = AssetsDaoFactory.getAssetsServiceImpl().financeConfirm(vou);
//            list = AssetsDaoFactory.getAssetsServiceImpl()
//                    .findAssets("", "", "", 1);
            request.setAttribute("finCon", finCon);
        }
        //list = new ArrayList<AllAssetsItem>();
        list = AssetsDaoFactory.getAssetsServiceImpl()
                .findAssets("ad_status", "未确认", "", Integer.parseInt(page));

        if(list != null) {
            if(list.size() < 1) {
                request.setAttribute("current", Integer.parseInt(page) - 1);
            }
        }

        request.setAttribute("coo", list);
        request.getRequestDispatcher("finconfig.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
