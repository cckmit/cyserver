package com.cy.core.project.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.dict.entity.Dict;
import com.cy.core.project.entity.Project;
import com.cy.core.project.entity.ProjectCost;
import com.cy.core.project.entity.ProjectItem;
import com.cy.util.PairUtil;

public interface ProjectService {
	void save(Project project);
	
	void update(Project project);

	int updateCommand(String ids);

	int updateNotCommand(String ids);
	
	void delete(String ids);
	
	DataGrid<Project> dataGrid(Map<String, Object> map);
	
	Project selectById(long id);

	//Project selectById1(String id);
	
	List<Project> selectAll();
	
	List<Project> selectTop6();
	
	List<Project> selectMore(int page,int rows);
	
	long selectTotalCount();
	
	Project selectByProjectName(String projectName);
	
	Project selectByProjectNameAndProjectId(Project project);
	
	ProjectItem listAll(int page,int rows,String accountNum);

	/**
	 * 查询捐赠项目列表
	 * @param message
	 * @param content
	 */
	void findProjectList(Message message, String content) ;

	/**
	 * 查询捐赠项目详情接口
	 * @param message
	 * @param content
     */
	void findProject(Message message, String content);

	DataGrid<ProjectCost> dataGridCost(Map<String, Object> map);

	ProjectCost selectCostById(String id);

	void saveCost(ProjectCost projectCost);

	void updateCost(ProjectCost projectCost);

	void deleteCost(String ids);

	void findProjectInfo(Message message, String content);

	void findProjectCost(Message message, String content);

	List<PairUtil<String,String>> getALLDonateType();
	void saveFounProject(Message message, String content);
	void updateFounProject(Message message, String content);
	void deleteByFounProject(Message message, String content);
}
