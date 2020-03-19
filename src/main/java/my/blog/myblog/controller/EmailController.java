package my.blog.myblog.controller;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;

@Controller
public class EmailController {

    @ResponseBody
    @RequestMapping("/sendEmail")
    public String sendEmail(@RequestParam String address,HttpServletResponse response){
        if("".equals(address)){
            return "no";
        }
        int random_code = (int) Math.round(Math.random() *10000);
        Cookie cookie = new Cookie("random_code",String.valueOf(random_code));
        cookie.setMaxAge(60*5);
        response.addCookie(cookie);

        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost("smtp.yeah.net");
        mailAccount.setPort(25);
        mailAccount.setAuth(true);
        mailAccount.setFrom("signinpro@yeah.net");
        mailAccount.setUser("signinpro@yeah.net");
        mailAccount.setPass("SBWLCDRLUKCDTQJD");
        MailUtil.send(mailAccount,address,"Hi 🎈Flow","您的验证码为:"+random_code+",请在5分钟内使用!",false);
        return "ok";
    }
}
