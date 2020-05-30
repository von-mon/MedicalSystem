package com.secondgroup.controller;

import com.github.pagehelper.PageInfo;
import com.secondgroup.bean.Doctor;
import com.secondgroup.service.DoctorService;
import com.secondgroup.utils.ResultEntity;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LindaBlack
 * @date 2020/5/27
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService service;

    @RequestMapping(value = "/doctors/{deptId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultEntity getDoctorByDeptId(@PathVariable("deptId") Integer id){
        List<Doctor> doctorByDeptId = service.getDoctorByDeptId(id);
        return ResultEntity.success("查询成功").put("list",doctorByDeptId);
    }

    @RequestMapping(value = "/batchDelete",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultEntity batchDelete(@RequestParam("idArray[]") Integer[] idArray){
        service.batchDelete(idArray);
        return ResultEntity.success("删除成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.PUT)
    public String update(Doctor doctor){
        service.update(doctor);

        return "redirect:/doctor/search";
    }

    @RequestMapping(value = "/edit/{did}",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("did") Integer id){
        Doctor doctor = service.getDoctorDetailById(id);
        ModelAndView modelAndView = new ModelAndView("/doctor/edit");
        modelAndView.addObject("doctor",doctor);
        return modelAndView;
    }

    @RequestMapping(value = "/detail/{did}",method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("did") Integer id){
        Doctor doctor = service.getDoctorDetailById(id);
        ModelAndView modelAndView = new ModelAndView("/doctor/look");
        modelAndView.addObject("doctor",doctor);
        return modelAndView;
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Doctor doctor){
        service.save(doctor);
        return "redirect:/doctor/search";
    }

    /**
     * 获取医生列表
     * @return ModelAndView 页面
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ModelAndView search(String name, Integer department,@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum ){
        PageInfo<Doctor> doctorPage = service.search(name, department, pageNum);
        String queryString = parseParam(name,department);
        ModelAndView modelAndView = new ModelAndView("/doctor/index");
        modelAndView.addObject("doctorPage",doctorPage);
        modelAndView.addObject("queryString",queryString);
        modelAndView.addObject("name",name);
        modelAndView.addObject("department",department);
        return modelAndView;
    }

    private String parseParam(String name, Integer department) {
        StringBuilder stringBuilder = new StringBuilder();
        if(name != null && !"".equals(name.trim())){
            stringBuilder.append("&name=").append(name);
        }
        if(department != null && department != 0){
            stringBuilder.append("&department=").append(department);
        }
        return stringBuilder.toString();
    }

}
