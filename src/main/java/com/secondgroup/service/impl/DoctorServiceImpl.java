package com.secondgroup.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secondgroup.bean.Doctor;
import com.secondgroup.bean.DoctorExample;
import com.secondgroup.dao.DoctorMapper;
import com.secondgroup.service.DoctorService;
import com.secondgroup.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author LindaBlack
 * @date 2020/5/27
 */
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper mapper;

    @Override
    public List<Doctor> getDoctorList() {
        DoctorExample example = new DoctorExample();
        return mapper.selectByExample(example);
    }

    @Override
    public void save(Doctor doctor) {
        mapper.insert(doctor);
    }

    @Override
    public Doctor getDoctorDetailById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Doctor doctor) {
        mapper.updateByPrimaryKeySelective(doctor);
    }

    @Override
    public PageInfo<Doctor> getDoctorPage(Integer pageNum) {
        DoctorExample doctorExample = new DoctorExample();
        PageHelper.startPage(pageNum, PageUtils.PAGE_SIZE);

        List<Doctor> doctors = mapper.selectByExample(doctorExample);
        return new PageInfo<>(doctors);
    }

    /**
     *
     * @param name
     * @param department
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<Doctor> search(String name, Integer department, Integer pageNum) {
        DoctorExample doctorExample = new DoctorExample();
        DoctorExample.Criteria criteria = doctorExample.createCriteria();
        if(name != null && !"".equals(name.trim())){
            criteria.andNameLike("%"+name+"%");
        }
        if(department != null && department != 0){
            criteria.andDepartmentEqualTo(department);
        }
        PageHelper.startPage(pageNum,PageUtils.PAGE_SIZE);
        List<Doctor> doctors = mapper.selectByExample(doctorExample);
        return new PageInfo<>(doctors);
    }

    @Override
    public void batchDelete(Integer[] idArray) {
        for (Integer id :
                idArray) {
            mapper.deleteByPrimaryKey(id);
        }
//        DoctorExample doctorExample = new DoctorExample();
//        DoctorExample.Criteria criteria = doctorExample.createCriteria();
//        List<Integer> integers = Arrays.asList(idArray);
//        criteria.andDidIn(integers);
//        mapper.deleteByExample(doctorExample);

    }

    @Override
    public List<Doctor> getDoctorByDeptId(Integer id) {
        DoctorExample doctorExample = new DoctorExample();
        DoctorExample.Criteria criteria = doctorExample.createCriteria();
        criteria.andDepartmentEqualTo(id);
        return mapper.selectByExample(doctorExample);
    }
}
