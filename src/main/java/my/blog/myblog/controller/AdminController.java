package my.blog.myblog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import my.blog.myblog.entity.Article;
import my.blog.myblog.entity.User;
import my.blog.myblog.service.ArticleService;
import my.blog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping("/loginForAdmin")
    public String loginForAdmin(@RequestParam String username, @RequestParam String password, HttpServletRequest req, HttpServletResponse rep) throws IOException {
        User loginUser = userService.login(username, password);
        if(loginUser != null){
            int role = userService.getRole(username);
            if(role == 1){
                User user = new User();
                user.setUsername(username);
                req.getSession().setAttribute("session_admin",user);
                rep.sendRedirect(req.getContextPath()+"/admin");
            }
        }else{
            return "错误";
        }
        return "";
    }

    @RequestMapping("/admin")
    public String admin(Model model,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) {
        PageHelper.startPage(start,size,"id desc");

        List<User> users = userService.getAll();
        PageInfo<User> user_page = new PageInfo<>(users);
        model.addAttribute("user_page",user_page);

        List<Article> articles = articleService.getAll();
        PageInfo<Article> article_page = new PageInfo<>(articles);
        model.addAttribute("article_page",article_page);

        return "admin";
    }

    @RequestMapping("/toAdminLogin")
    public String toAdminLogin() {
        return "admin_login";
    }

    @ResponseBody
    @RequestMapping("/delOneUser")
    public String delOneUser(@RequestParam int id,HttpServletRequest req,HttpServletResponse rep) throws IOException {
        int delete = userService.delete(id);
        if(delete == 1){
            rep.sendRedirect(req.getContextPath()+"/admin");
            return "ok";
        }
        return "no";
    }

    @ResponseBody
    @RequestMapping("/delOneArticle")
    public String delOneArticle(@RequestParam int id,HttpServletRequest req,HttpServletResponse rep) throws IOException {
        int delete = articleService.delete(id);
        if(delete == 1){
            rep.sendRedirect(req.getContextPath()+"/admin");
            return "ok";
        }
        return "no";
    }

    @ResponseBody
    @RequestMapping("/logOutForAdmin")
    public void logOutForAdmin(HttpServletRequest req,HttpServletResponse rep) throws IOException {
        req.getSession().removeAttribute("session_admin");
        rep.sendRedirect(req.getContextPath()+"/toAdminLogin");
    }
}
