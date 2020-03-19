package my.blog.myblog.mapper;

import my.blog.myblog.entity.Article;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleMapper {

    Article getOneById(int id);

    int insertOne(Article article);

    List<Article> getAll();

    int deleteOne(int id);

    int updateOne(Article article);

    String getMainById(int id);

    List<Article> getAllByAuthor(String author);

    int updatePoke(int id);

    int updatePoke_p(int id);

    List<Article> findByTitle(String title);

    int getTotal();

}
