package com.servlet;

import com.factory.AssetsDaoFactory;
import com.google.gson.Gson;
import com.pojo.AllAssetsItem;
import com.pojo.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/AssetsAjaxServlet")
public class AssetsAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tages = request.getParameter("tages");


        String JSON_TXT= "";
        Gson gson = null;
        PrintWriter out =response.getWriter();
        // 如果tages="users"代表是用户数据，其它的tages的值都是资产数据
        if("users".equalsIgnoreCase(tages)) {
            List<Users> list2 = AssetsDaoFactory.getUsersServiceImpl().findUsersByUname("");

            gson = new Gson();
            JSON_TXT = gson.toJson(list2);
            out.write(JSON_TXT);


        }else {
            List<AllAssetsItem> list = null;
            if("领用".equalsIgnoreCase(tages)) {
                list = AssetsDaoFactory.getAssetsServiceImpl().findAssets("ad_status", "已确认", "", 1);

            }else if("归还".equalsIgnoreCase(tages) || "返修".equalsIgnoreCase(tages) ||"报废".equalsIgnoreCase(tages) ||
                "销帐".equalsIgnoreCase(tages))  {
                // tages="R"代表领用， 返修， 报废， 销帐
                list = AssetsDaoFactory.getAssetsServiceImpl().findAssets("ad_status", "领用", "", 1);
            }

            try {
                gson= new Gson();

                JSON_TXT = gson.toJson(list);

                out.write(JSON_TXT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
