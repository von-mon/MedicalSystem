package com.secondgroup.service;

import com.secondgroup.bean.User;

import java.util.List;

/**
 * @author LindaBlack
 * @date 2020/5/27
 */

public interface UserService {



    User getUserInfoByUserName(String username);

    User checkEmail(String email);


    boolean regist(User user);

    User login(User user);
}
