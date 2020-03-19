package my.blog.myblog.service;

import my.blog.myblog.entity.Poke;
import my.blog.myblog.mapper.PokeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokeService {
    @Autowired
    private PokeMapper pokeMapper;

    public int insertPoke(Poke poke){
        return pokeMapper.insertPoke(poke);
    }

    public List<Poke> getAll(){
        return pokeMapper.getAll();
    }

    public List<Integer> getMyLikes(String user_name){
        return pokeMapper.getAllArticleIDByUsername(user_name);
    }

    public int delOneOfMyPoke(Poke poke){
        return pokeMapper.delPoke(poke);
    }
}
