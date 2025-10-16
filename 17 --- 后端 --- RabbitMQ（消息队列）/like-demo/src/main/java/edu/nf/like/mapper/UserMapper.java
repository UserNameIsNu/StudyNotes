package edu.nf.like.mapper;

import edu.nf.like.model.entity.User;

/**
 * @author wangl
 * @date 2023/12/6
 */
public interface UserMapper {

    /**
     * 根据用户名获取用户信息
     * @param userName 用户名
     * @return User
     */
    User getUserByName(String userName);
}