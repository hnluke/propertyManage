package com.servlet;

import com.factory.AssetsDaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AssetsChangeAjaxServlet")
public class AssetsChangeAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sele = request.getParameter("sele");
        String usr = request.getParameter("usr");
        String arr = request.getParameter("arr");
        String tages = request.getParameter("tages");
        int ad_id = 0;
        int u_id = Integer.parseInt(usr);
        String[] splitSele = sele.split(",");
        boolean flag = true;
        try {
            for(String str : splitSele) {
                ad_id = Integer.parseInt(str);
                if(!AssetsDaoFactory.getAssetsServiceImpl().assertsUse(ad_id, u_id, tages)) {
                    flag = false;
                }
            }
            PrintWriter out =response.getWriter();
            if(flag) {
                out.write(tages + "成功");
            }else {
                out.write(tages + "失败");
            }
            out.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
