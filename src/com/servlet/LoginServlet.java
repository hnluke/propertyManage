package com.servlet;

import com.factory.AssetsDaoFactory;
import com.pojo.Assets;
import com.pojo.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        String login = request.getParameter("login");
        HttpSession session = request.getSession();
        if("1".equals(login))
        {
            if(AssetsDaoFactory.getUsersServiceImpl().verify(uname, pwd)) {
                session.setAttribute("user", uname);
                List<Users> list = AssetsDaoFactory.getUsersServiceImpl().findUsersByUname(uname);
                boolean lock = list.get(0).getU_lock();
                String prio = list.get(0).getU_prio();
                if(lock) {
                    request.getSession().setAttribute("err", "此用户已被锁定，请联系管理员解锁");
                    request.getRequestDispatcher("login.jsp")
                            .forward(request,response);
                }else {
                    session.setAttribute("prio", prio);
                    response.sendRedirect(request.getContextPath() + "/main.jsp");
                }
            }else{
                request.getSession().setAttribute("err", "用户或密码错误");
                request.getRequestDispatcher("login.jsp")
                        .forward(request,response);
            }
        }else if("2".equals(login)) {
            session.invalidate();
            AssetsDaoFactory.closeConnection();
            response.sendRedirect("login.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
