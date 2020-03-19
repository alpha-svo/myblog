package my.blog.myblog.interceptor;


import my.blog.myblog.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("preHandle...");
        System.out.println(handler);
        User user = (User) request.getSession().getAttribute("session_admin");
        if (user==null){
            response.sendRedirect(request.getContextPath()+"/toAdminLogin");
            return false;
        }
        System.out.println("放行");
        return true;
    }
}