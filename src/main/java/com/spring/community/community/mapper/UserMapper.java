package com.spring.community.community.mapper;


import com.spring.community.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,avatar_url,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{avatarUrl},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set token=#{token} where account_id=#{accountId}")
    void updateUserToken(User updateUser);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id")Integer id);
}
