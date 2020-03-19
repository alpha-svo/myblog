package my.blog.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import my.blog.myblog.entity.Article;
import my.blog.myblog.entity.Poke;
import my.blog.myblog.service.ArticleService;
import my.blog.myblog.service.PokeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PokeController {
    @Autowired
    private PokeService pokeService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/myPoke")
    public String myPoke(Model model,@RequestParam  String u_name,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "8") int size){
        PageHelper.startPage(start,size);
        List<Integer> myLikes = pokeService.getMyLikes(u_name);
        System.out.println(myLikes);
        List<Article> articles = new ArrayList<>();
        for(Integer i:myLikes){
            articles.add(articleService.getOneById(i));
        }
        System.out.println(articles);
        PageInfo<Article> page = new PageInfo<>(articles);
        model.addAttribute("page",page);
        model.addAttribute("username",u_name);
        return "poke";
    }

    @ResponseBody
    @RequestMapping("/delMyPoke")
    public void delMyPoke(Poke poke, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pokeService.delOneOfMyPoke(poke);
        articleService.unlike(poke.getArticle_id());
        String user_name = poke.getUser_name();
        request.getRequestDispatcher("/myPoke?u_name="+user_name).forward(request,response);
    }
}
