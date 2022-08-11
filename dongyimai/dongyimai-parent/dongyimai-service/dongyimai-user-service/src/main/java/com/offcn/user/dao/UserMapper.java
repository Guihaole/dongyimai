package com.offcn.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.offcn.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/****
 * @Author:ujiuye
 * @Description:User的Dao
 * @Date 2021/2/1 14:19
 *****/
public interface UserMapper extends BaseMapper<User> {
    //增加积分
    @Update("UPDATE tb_user SET points=points+#{points} WHERE  username=#{username}")
    int addUserPoints(@Param("username") String username,@Param("points") Integer points);
}
