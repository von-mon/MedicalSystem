package com.secondgroup.service.impl;

import com.secondgroup.bean.User;
import com.secondgroup.bean.UserExample;
import com.secondgroup.dao.UserMapper;
import com.secondgroup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author LindaBlack
 * @date 2020/5/27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;


    @Override
    public User getUserInfoByUserName(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = mapper.selectByExample(userExample);

        if(users!=null&& users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User checkEmail(String email) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> users = mapper.selectByExample(userExample);
        if(users!=null&& users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public boolean regist(User user) {
        user.setStatus(1);
        user.setModifytime(new Date());
        int insert = mapper.insert(user);
        return insert > 0;
    }

    @Override
    public User login(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> users = mapper.selectByExample(userExample);
        if(users!=null&& users.size()>0){
            return users.get(0);
        }
        return null;
    }

}
