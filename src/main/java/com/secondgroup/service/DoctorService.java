package com.secondgroup.service;

import com.github.pagehelper.PageInfo;
import com.secondgroup.bean.Doctor;

import java.util.List;

/**
 * @author LindaBlack
 * @date 2020/5/27
 */
public interface DoctorService {
    List<Doctor> getDoctorList();

    void save(Doctor doctor);

    Doctor getDoctorDetailById(Integer id);

    void update(Doctor doctor);

    PageInfo<Doctor> getDoctorPage(Integer pageNum);

    PageInfo<Doctor> search(String name, Integer department, Integer pageNum);

    void batchDelete(Integer[] idArray);

    List<Doctor> getDoctorByDeptId(Integer id);
}
