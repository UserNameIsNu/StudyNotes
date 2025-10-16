package edu.nf.like.service;

import edu.nf.like.model.entity.User;

/**
 * @author wangl
 * @date 2023/12/6
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return User
     */
    User getUser(String userName);
}