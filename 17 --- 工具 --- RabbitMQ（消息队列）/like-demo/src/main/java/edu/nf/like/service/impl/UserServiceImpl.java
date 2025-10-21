package edu.nf.like.service.impl;

import edu.nf.like.model.entity.User;
import edu.nf.like.common.exception.LoginException;
import edu.nf.like.mapper.UserMapper;
import edu.nf.like.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public User getUser(String userName) {
        User user = userMapper.getUserByName(userName);
        if(user == null) {
            throw new LoginException(10000, "用户不存在");
        }
        return user;
    }
}