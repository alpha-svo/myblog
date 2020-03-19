package my.blog.myblog.service;

import my.blog.myblog.entity.Article;
import my.blog.myblog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Article> getAll(){
        int total = articleMapper.getTotal();
        String key = String.valueOf(total);
        ListOperations operations = redisTemplate.opsForList();
        Boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            List<Article> articles = operations.range(key,0,-1);
            System.out.println("从缓存拿数据");
            return articles;
        }else{
            List<Article> articles = articleMapper.getAll();
            System.out.println("从数据库拿数据");
            Set<String> keys = redisTemplate.keys("*");
            for(String k:keys){
                if(k != key){
                    redisTemplate.delete(k);
                }
            }
            operations.leftPushAll(key,articles);
            return articles;
        }
    }

    public int update(Article article){
        return articleMapper.updateOne(article);
    }

    public int delete(int id){
        return articleMapper.deleteOne(id);
    }

    public String getMain_id(int id){
        return articleMapper.getMainById(id);
    }

    public List<Article> getMyArticles_author(String author){
        return articleMapper.getAllByAuthor(author);
    }

    public int saveWrite(Article article){
        return articleMapper.insertOne(article);
    }

    public int like(int id){
        return articleMapper.updatePoke(id);
    }

    public Article getOneById(int id){
        return articleMapper.getOneById(id);
    }

    public int unlike(int id){
        return articleMapper.updatePoke_p(id);
    }

    public List<Article> search(String title){
        return articleMapper.findByTitle(title);
    }
}
