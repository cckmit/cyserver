package com.cy.core.alumni.dao;

import com.cy.core.alumni.entity.AlumniWaitRegisters;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 5/3/17.
 */
public interface AlumniWaitRegistersMapper {
    // 查询列表
    List<AlumniWaitRegisters> selectList(AlumniWaitRegisters alumniWaitRegisters);

    // 保存临时数据
    void save(AlumniWaitRegisters alumniWaitRegisters);

    // 更新数据
    void update(AlumniWaitRegisters alumniWaitRegisters);

    //删除数据
    void delete(AlumniWaitRegisters alumniWaitRegisters);
}
