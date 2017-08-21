package com.cy.core.project.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.dict.entity.Dict;
import com.cy.core.project.entity.Project;

public interface ProjectMapper {
	void save(Project project);
	
	List<Project> selectList(Map<String, Object> map);
	
	long countProject(Map<String, Object> map);
	
	Project selectByProjectName(String projectName);

	Project selectByFounProject(String founProject);
	
	Project selectById(long id);
	
	void delete(List<Long> list);
	
	Project selectByProjectNameAndProjectId(Project project);
	
	void update(Project project);

//	void updateCommand(Project project);

//	Project selectById1(String id);

	void changeIsCommand(List<String> list);

	void changeIsNotCommand(List<String> list);

	List<Project> selectAll();
	
	void updateDonationMoney(Project project);
	
	List<Project> selectTop6();
	
	List<Project> selectMore(Map<String, Object> map);
	
	long selectTotalCount();

	/**
	 * 方法 getALLDonateType的功能描述：获取所有的捐赠项目类型
	 * @createAuthor niu
	 * @createDate 2017-04-01 10:56:46
	 * @param
	 * @return
	 * @throw
	 *
	*/
	List<Dict> getALLDonateType();

	//基金会修改项目
	void updateByFounProject(Project project);
	//删除基金会项目
	void deleteFounProject(String founProject);
}
