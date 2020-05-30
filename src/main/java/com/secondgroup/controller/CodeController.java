package com.secondgroup.controller;

import com.secondgroup.utils.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LindaBlack
 */
@Controller
@RequestMapping("/code")
public class CodeController {

    @RequestMapping(value="getCode")
    public void getCode(@RequestParam(value = "time") String time, HttpServletRequest request, HttpServletResponse response) {
        ValidateCode code = new ValidateCode(100, 30, 4, 5, 25, "validateCode");
        code.getCode(request, response);

    }

    @RequestMapping(value = "/checkCode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object>checkCode(String code, HttpSession session){
        Map<String,Object> map = new HashMap<String, Object>();
        //取出session中的验证码
        String validateCode = (String) session.getAttribute("validateCode");
        //和用户输入的验证码比较，忽略大小写
        if(validateCode.equalsIgnoreCase(code)){
            map.put("statusCode",200);
            map.put("message","验证通过");
        }else {
            map.put("statusCode",500);
            map.put("message","验证失败");
        }
        return map;
    }
}
