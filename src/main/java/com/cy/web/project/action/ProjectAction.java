package com.cy.web.project.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.WebBaseAction;
import com.cy.core.donation.entity.Donation;
import com.cy.core.donation.service.DonationService;
import com.cy.core.project.entity.Project;
import com.cy.core.project.service.ProjectService;

@Namespace("/_project")
@Action(value = "_projectAction", results = { @Result(name = "donationIndex", location = "/web/donateIndex.jsp"),
		@Result(name = "donationMIndex", location = "/mobile/jz/index.jsp"),
		@Result(name = "donationMore", location = "/web/donateList.jsp"), @Result(name = "donationDetail", location = "/web/donateDetail.jsp"),
		@Result(name = "donationStep1", location = "/web/donateStep1.jsp") })
public class ProjectAction extends WebBaseAction {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DonationService donationService;

	private List<Project> projects;

	private Project project;

	private long totalCount;

	private long totalPage;

	private List<Donation> donations;

	public String donationIndex() {
		projects = projectService.selectTop6();
		return "donationIndex";
	}
	
	public String donationMIndex(){
		projects = projectService.selectAll();
		return "donationMIndex";
	}

	public String donationMore() {
		totalCount = projectService.selectTotalCount();
		if (totalCount % rows == 0) {
			totalPage = totalCount / rows;
		} else {
			totalPage = totalCount / rows + 1;
		}
		projects = projectService.selectMore(page, rows);
		return "donationMore";
	}

	public String donationDetail() {
		project = projectService.selectById(id);
		donations = donationService.selectRandom50();
		return "donationDetail";
	}

	public String donationStep1() {
		projects = projectService.selectAll();
		return "donationStep1";
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}

}
