package com.secondgroup.controller;

import com.secondgroup.bean.User;
import com.secondgroup.bean.UserExample;
import com.secondgroup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LindaBlack
 * @date 2020/5/27
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    /**
     登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        //销毁当前用户的session
        session.invalidate();
        //重定向到登录界面
        return "redirect:/login.jsp";
    }

    /**
     登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User login = service.login(user);
        if (login == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "请输入正确的信息");
            return "redirect:/login";
        } else {
            session.setAttribute("loginUser", login);
            return "redirect:/index.jsp";
        }
    }

    /**
     注册
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String regist(User user) {
        boolean regist = service.regist(user);
        if (regist) {
            return "redirect:/login.jsp";
        } else {
            return "redirect:/register.jsp";
        }
    }

    /**
     检查邮箱
     */
    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkEmail(String email) {
        User user = service.checkEmail(email);
        Map<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            //邮箱存在，不能注册
            map.put("statusCode", 500);
            map.put("message", "邮箱已被注册");

        } else {
            //邮箱不存在，可以注册
            map.put("statusCode", 200);
            map.put("message", "邮箱可以注册");
        }
        return map;
    }


    /**
        用户检查
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, Object> getUserInfoByUserName(String username) {
        User user = service.getUserInfoByUserName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            map.put("statusCode", 500);
            map.put("message", "该用户名已被注册");
        } else {
            //用户不存在，可以注册
            map.put("statusCode", 200);
            map.put("message", "该用户名可以注册");
            System.out.println(map.toString());
        }
        return map;
    }

}
