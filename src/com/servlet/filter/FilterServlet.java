package com.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        description = "字符编码",
        filterName = "encodingFilter",
        urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "ENCODING", value = "UTF-8")
        }
)
public class FilterServlet implements Filter {
    private String encodings = "UTF-8";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        req.setCharacterEncoding(encodings);
        resp.setCharacterEncoding(encodings);
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        chain.doFilter(request, response);
//        if(request.getParameter("user") == null) {
//            request.getRequestDispatcher("login.jsp").forward(req, resp);
//        }else{
//           // chain.doFilter(request, response);
//        }

    }

    public void init(FilterConfig config) throws ServletException {
        encodings = config.getInitParameter("ENCODING");
        if (encodings == null || "".equals(encodings)) {
            encodings = "UTF-8";
        }

    }

}
