package com.cy.core.project.dao;

import com.cy.core.project.entity.ProjectCost;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 2/14/17.
 */
public interface ProjectCostMapper {
    List<ProjectCost> selectList(Map<String, Object> map);
    long countProjectCost(Map<String, Object> map);
    ProjectCost  selectById(String id);
    void save(ProjectCost projectCost);
    void update(ProjectCost projectCost);
    void delete(List<String> list);
}
