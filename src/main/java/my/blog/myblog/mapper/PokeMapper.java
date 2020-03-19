package my.blog.myblog.mapper;

import my.blog.myblog.entity.Poke;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokeMapper {
    int insertPoke(Poke poke);

    List<Poke> getAll();

    List<Integer> getAllArticleIDByUsername(String user_name);

    int delPoke(Poke poke);
}
