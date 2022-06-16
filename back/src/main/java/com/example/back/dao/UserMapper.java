package com.example.back.dao;

import com.example.back.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User login( String linkNum, String password);

    List<User> getUserAll();

    int insertUser(User user);

    Integer getUserId();
    int updateUser(User user);

    int deleteUserById(Integer id);

    List<User>getUser();

    User getUserById(Integer userId);

    List<User> queryUserByConditions(String username, String linknum);
    //分页查询
    public List<User> getUserAllByPage(@Param("Page") int Page, @Param("size") int size);
    //查出用户数量
    int selectCount();

}
