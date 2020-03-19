package my.blog.myblog.service;


import my.blog.myblog.entity.User;
import my.blog.myblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getOne(int id){
        return userMapper.getOne(id);
    }

    public User login(String userName, String passWord) {
        return userMapper.findByNameAndPassword(userName,passWord);
    }

    @Transactional
    public int register(User user) {
        return userMapper.insertOne(user);
    }

    public String ifUsernameExist(String username){
        return userMapper.getUsernameFromUsername(username);
    }

    public int getRole(String username){
        return userMapper.getRoleByUsername(username);
    }

    public List<User> getAll(){
        return userMapper.getAll();
    }

    public int delete(int id){
        return userMapper.deleteOne(id);
    }

    public int changPassword(String username,String password){
        return userMapper.updatePassword(username,password);
    }

    public String findEmail(String username){
        return userMapper.getEmailByUsername(username);
    }
}