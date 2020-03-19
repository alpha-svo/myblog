package my.blog.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class test {

    @RequestMapping("/test")
    public String test1(Model m){
        System.out.println("test1...");
        m.addAttribute("text","秦璨是个大坏蛋");
        return "test";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String test2(@RequestParam String first,@RequestParam  String second){
        System.out.println("test2...");
        System.out.println(first);
        System.out.println(second);
        return "我是返回值哈哈哈";
    }

   /* @ResponseBody
    @RequestMapping("/test3")
    public String test3(@RequestParam String main){
        System.out.println("test3...");
        System.out.println(main);
        return "我是test3返回值";
    }*/
}
