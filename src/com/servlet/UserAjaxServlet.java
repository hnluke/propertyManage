package com.servlet;

import com.factory.AssetsDaoFactory;
import com.google.gson.Gson;
import com.pojo.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/UserAjaxServlet")
public class UserAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tages = request.getParameter("tages");
        String u_name = request.getParameter("u_name");
        String u_id = request.getParameter("u_id");
        String u_pwd = request.getParameter("u_pwd");
        String u_prio = request.getParameter("u_prio");
        String u_tele = request.getParameter("u_tele");
        String u_lock = request.getParameter("u_lock");
        Users users = null;
        String outStr = "";
        List<Users> list = null;
        System.out.println(tages);
        if("I".equals(tages)) {
            // 新增用户
            if ("0".equals(u_prio)) {
                u_prio = "C";
            }
            users = new Users();
            users.setU_name(u_name);
            users.setU_pwd(u_pwd);
            users.setU_tele(u_tele);
            users.setU_prio(u_prio);
            users.setU_lock(false);
            list = AssetsDaoFactory.getUsersServiceImpl().findUsersByUname(u_name);
            if(list.size() > 0) {
                outStr = "此用户已经存在";
            }else{
                if(AssetsDaoFactory.getUsersServiceImpl().addUser(users)) {
                    outStr = "新增用户成功";
                }else{
                    outStr = "新增用户失败";
                }
            }
        }else if("Q".equals(tages)) {
            // 查询用户
            list = AssetsDaoFactory.getUsersServiceImpl().findUsersByUname("");
            request.setAttribute("allUser", list);
            Gson gson = new Gson();
            outStr = gson.toJson(list);

        }else if("R".equals(tages)) {
            // 复位用户密码
            String sele = request.getParameter("sele");
            String[] seleArr = sele.split(",");
            for(String str : seleArr) {
                int u_ids = Integer.parseInt(str);
                outStr = AssetsDaoFactory.getUsersServiceImpl().resetUserPwd(u_ids);
            }
        }else if("D".equals(tages)) {
            // 删除用户
            int u_idInt = Integer.parseInt(u_id);
            outStr = AssetsDaoFactory.getUsersServiceImpl().deleteUserById(u_idInt);

        }else if("U".equals(tages)) {
            // 修改用户：显示要修改的用户
            int u_idInt = Integer.parseInt(u_id);
            users = AssetsDaoFactory.getUsersServiceImpl().findUserById(u_idInt);
            Gson gson = new Gson();
            outStr = gson.toJson(users);
        }else if("U2".equals(tages)) {
            // 修改用户
            users = new Users();
            users.setU_pwd(u_pwd);
            users.setU_prio(u_prio);
            users.setU_tele(u_tele);
            users.setU_lock(Boolean.parseBoolean(u_lock));
            outStr = AssetsDaoFactory.getUsersServiceImpl().updateUsersByUserName(u_name, users);


        }
        PrintWriter out= response.getWriter();
        out.write(outStr);
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
