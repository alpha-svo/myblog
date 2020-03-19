package my.blog.myblog.mapper;


import my.blog.myblog.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    User getOne(int id);

    User findByNameAndPassword(String username, String password);

    int insertOne(User user);

    List<User> getAll();

    String getUsernameFromUsername(String username);

    int getRoleByUsername(String username);

    int deleteOne(int id);

    int updatePassword(String username,String password);

    String getEmailByUsername(String username);
}
