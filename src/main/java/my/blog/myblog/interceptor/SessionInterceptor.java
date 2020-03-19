package my.blog.myblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SessionInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        //排除登录注册发邮件查询存在
        list.add("/toLogin");
        list.add("/toRegister");
        list.add("/articles_visitor");
        list.add("/read_visitor");
        list.add("/toVisitor");
        list.add("/login");
        list.add("/register");
        list.add("/get_session_user");
        list.add("/sendEmail");
        list.add("/ifAccountExist");
        //排除静态资源
        list.add("/**/*.html");
        list.add("/**/*.js");
        list.add("/**/*.css");
        list.add("/**/*.jpg");
        list.add("/**/*.svg");
        list.add("/**/*.ttf");
        list.add("/**/*.woff");
        list.add("/**/*.woff2");
        list.add("/**/*.eot");
        list.add("/**/*.map");
        list.add("/**/*.ico");
        //排除后台页面
        list.add("/loginForAdmin");
        list.add("/admin");
        list.add("/toAdminLogin");
        list.add("/delOneUser");
        list.add("/delOneArticle");
        list.add("/logOutForAdmin");
        list.add("/getArticle");
        //排除修改密码
        list.add("/toChangePassword");
        list.add("/changePassword");
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns(list);
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin","/delOneUser","/delOneArticle");
    }
}
