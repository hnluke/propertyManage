package com.servlet;

import com.factory.AssetsDaoFactory;
import com.google.gson.Gson;
import com.pojo.AllAssetsItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/CardAjaxServlet")
public class CardAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardId = request.getParameter("cardId");
        try {
            List<AllAssetsItem> list = AssetsDaoFactory.getAssetsServiceImpl().findAssets("ad_cardcode",cardId,"",1);
            Gson gson = new Gson();
            String JSON_TXT = gson.toJson(list);
            PrintWriter out =response.getWriter();
            out.write(JSON_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
