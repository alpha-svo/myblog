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
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private PokeService pokeService;


    @RequestMapping("/articles_visitor")
    public String articles_visitor(Model model,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "8") int size) {
        PageHelper.startPage(start,size,"id desc");
        List<Article> articles = articleService.getAll();
        PageInfo<Article> page = new PageInfo<>(articles);
        model.addAttribute("page",page);
        return "visitorPage";
    }

    @RequestMapping(value = "/read_visitor",method = RequestMethod.GET)
    public String read_visitor(@RequestParam int id,Model model,@RequestParam String title) {
        String main = articleService.getMain_id(id);
        model.addAttribute("main",main);
        model.addAttribute("title",title);
        return "visitorRead";
    }

    @RequestMapping("/articles")
    public String articles(Model model,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "8") int size) {
        PageHelper.startPage(start,size,"id desc");
        List<Article> articles = articleService.getAll();
        PageInfo<Article> page = new PageInfo<>(articles);
        model.addAttribute("page",page);
        return "home";
    }


    @RequestMapping(value = "/read",method = RequestMethod.GET)
    public String read(@RequestParam int id,Model model,@RequestParam String title,@RequestParam String username) {
        String main = articleService.getMain_id(id);
        model.addAttribute("main",main);
        model.addAttribute("title",title);
        model.addAttribute("id",id);
        model.addAttribute("username",username);
        return "read";
    }

    @RequestMapping("/my_articles")
    public String my_articles(Model model,@RequestParam String username,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "8") int size) {
        PageHelper.startPage(start,size,"id desc");
        List<Article> my_articles = articleService.getMyArticles_author(username);
        PageInfo<Article> page = new PageInfo<>(my_articles);
        model.addAttribute("my_username",username);
        model.addAttribute("page",page);
        return "my_account";
    }

    @RequestMapping("/to_article_edit")
    public String to_article_edit(Model model,@RequestParam String title,@RequestParam int id) {
        String edit_main = articleService.getMain_id(id);
        model.addAttribute("edit_main",edit_main);
        model.addAttribute("title",title);
        model.addAttribute("id",id);
        return "article_edit";
    }

    @ResponseBody
    @RequestMapping("/after_edit_save")
    public String after_edit_save(@RequestParam String main, @RequestParam String title,@RequestParam int id) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setMain(main);
        article.setDate(format);
        int update = articleService.update(article);
        if(update == 1){
            return "保存成功";
        }
        return "保存失败";
    }

    @ResponseBody
    @RequestMapping("/edit_delete")
    public String edit_delete(@RequestParam int id) {
        int delete = articleService.delete(id);
        if(delete == 1){
            return "删除成功";
        }
        return "删除失败";
    }

    @ResponseBody
    @RequestMapping("/write_save")
    public String write_save(@RequestParam String main, @RequestParam String title,@RequestParam String user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        Article article = new Article();
        article.setTitle(title);
        article.setMain(main);
        article.setDate(format);
        article.setAuthor(user);
        int insert = articleService.saveWrite(article);
        if(insert == 1){
            return "保存成功^_^";
        }
        return "保存失败！";
    }

    @RequestMapping("/toWrite")
    public String toWrite(@RequestParam String user,Model model) {
        model.addAttribute("username",user);
        return "write";
    }

    @ResponseBody
    @RequestMapping("/like")
    public String like(Poke poke){
        Integer article_id = poke.getArticle_id();
        int like = articleService.like(article_id);
        int i = pokeService.insertPoke(poke);
        if(like == 1 && i == 1){
            return "Thanks";
        }
        return "no";
    }

    @ResponseBody
    @RequestMapping("/isLiked")
    public String isLiked(Poke poke){
        List<Poke> pokes = pokeService.getAll();
        int article_id = poke.getArticle_id();
        String user_name = poke.getUser_name();
        for(Poke p : pokes){
            int p_article_id = p.getArticle_id();
            String p_user_name = p.getUser_name();
            if(p_user_name.equals(user_name) && p_article_id == article_id){
                return "ok";
            }
        }
        return "no";
    }


    @RequestMapping("/search")
    public String search_controller(Model model,@RequestParam String title,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size){
        PageHelper.startPage(start,size);
        List<Article> search_articles = articleService.search(title);
        PageInfo<Article> page = new PageInfo<>(search_articles);
        model.addAttribute("title",title);
        model.addAttribute("page",page);
        return "search";
    }
}
