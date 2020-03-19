package my.blog.myblog.controller;


import my.blog.myblog.entity.User;
import my.blog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String show(){
        return "login";
    }

    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "changePassword";
    }

    @ResponseBody
    @RequestMapping("/toVisitor")
    public String toVisitor(HttpServletResponse response,HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath()+"/articles_visitor");
        return "success";
    }

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/toHome")
    @ResponseBody
    public String toHome(HttpServletResponse response,HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath()+"/articles");
        return "success";
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = user.getUsername();
        String passWord = user.getPassword();
        String id = String.valueOf(user.getId());
        User loginUser =userService.login(userName,passWord);
        if (loginUser==null){
           return "用户名或密码错误";
        }else{
            request.getSession().setAttribute("session_user",user);
            request.setAttribute("user",loginUser);
            response.sendRedirect(request.getContextPath()+"/toHome");
            return "";
        }
    }

    @ResponseBody
    @RequestMapping("/get_session_user")
    public String get_session_user(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("session_user");
        if(user == null){
            throw new RuntimeException("null");
        }
        return "username="+user.getUsername();
    }


    @ResponseBody
    @RequestMapping("/register")
    public String register(@RequestParam String username,@RequestParam String password,@RequestParam String email,@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if("".equals(username) || "".equals(password) || "".equals(email) || "".equals(code)){
            response.sendRedirect(request.getContextPath()+"/toRegister");
        }

        Cookie[] cookies = request.getCookies();
        String value = null;
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            if("random_code".equals(name)){
                value = cookie.getValue();
                break;
            }
        }
        if(code.equals(value)){
            Date date = new Date();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setJoin_time(date);
            int register = userService.register(user);
            if(register == 1){
                response.sendRedirect(request.getContextPath()+"/toLogin");
                return "ok";
            }
        }
        return "no";
    }


    @RequestMapping("/logout")
    public void outUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("session_user");
        response.sendRedirect("/toLogin");
    }

    @ResponseBody
    @RequestMapping("/ifAccountExist")
    public String ifAccountExist(@RequestParam String username) {
        String s = userService.ifUsernameExist(username);
        if(s != null){
            return "yes";
        }
        return "no";
    }

    @ResponseBody
    @RequestMapping("/changePassword")
    public String changePassword(@RequestParam String username,@RequestParam String email,@RequestParam String code,@RequestParam String password,HttpServletRequest req,HttpServletResponse rep) throws IOException {
        if("".equals(username) || "".equals(email) || "".equals(code)){
            return "请输入完整数据";
        }
        if("no".equals(ifAccountExist(username))){
            return "用户不存在";
        }
        if(!email.equals(userService.findEmail(username))){
            return "邮箱不匹配";
        }

        Cookie[] cookies = req.getCookies();
        String value = null;
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            if("random_code".equals(name)){
                value = cookie.getValue();
                break;
            }
        }

        if(code.equals(value)) {
            int update = userService.changPassword(username, password);
            if(update == 1){
                rep.sendRedirect(req.getContextPath()+"/toLogin");
                return "修改成功";
            }
        }
        return "修改失败";
    }

}