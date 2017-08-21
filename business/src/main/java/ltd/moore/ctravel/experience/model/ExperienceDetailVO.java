package ltd.moore.ctravel.experience.model;

import java.io.Serializable;

/**
 * ExperienceDetail对象
 * @author caicai
 * @version 1.0
 */

public class ExperienceDetailVO implements Serializable {
	/*
	 *体验明细ID
	 */
	private String experienceDetailId;
	
	/*
	 *体验ID
	 */
	private String experienceId;
	
	/*
	 *目的地ID
	 */
	private String destinationId;
	
	/*
	 *标题
	 */
	private String title;
	
	/*
	 *副标题
	 */
	private String subtitle;
	/*
	 *体验描述图片Id
	 */
	private String imageId;
	/*
	 *体验描述图片URL
	 */
	private String imageUrl;
	
	/*
	 *内容描述
	 */
	private String contentDescription;
	
	/*
	 *目的地
	 */
	private String destination;
	
	/*
	 *集合地
	 */
	private String rendezvous;
	
	/*
	 *体验内容明细
	 */
	private String contentDetails;
	
	/*
	 *备注
	 */
	private String comment;
	
	/*
	 *要求
	 */
	private String requirement;
	
	/*
	 *人数
	 */
	private String peopleNumber;
	/*
	 *排序
	 */
	private String sortNo;
	public ExperienceDetailVO(){}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getExperienceDetailId() {
		return  experienceDetailId;
	}

	public void setExperienceDetailId(String  experienceDetailId) {
		 this.experienceDetailId = experienceDetailId;
	}
	
	public String getExperienceId() {
		return  experienceId;
	}

	public void setExperienceId(String  experienceId) {
		 this.experienceId = experienceId;
	}
	
	public String getDestinationId() {
		return  destinationId;
	}

	public void setDestinationId(String  destinationId) {
		 this.destinationId = destinationId;
	}
	
	public String getTitle() {
		return  title;
	}

	public void setTitle(String  title) {
		 this.title = title;
	}
	
	public String getSubtitle() {
		return  subtitle;
	}

	public void setSubtitle(String  subtitle) {
		 this.subtitle = subtitle;
	}
	
	public String getImageUrl() {
		return  imageUrl;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public void setImageUrl(String  imageUrl) {
		 this.imageUrl = imageUrl;
	}
	
	public String getContentDescription() {
		return  contentDescription;
	}

	public void setContentDescription(String  contentDescription) {
		 this.contentDescription = contentDescription;
	}
	
	public String getDestination() {
		return  destination;
	}

	public void setDestination(String  destination) {
		 this.destination = destination;
	}
	
	public String getRendezvous() {
		return  rendezvous;
	}

	public void setRendezvous(String  rendezvous) {
		 this.rendezvous = rendezvous;
	}
	
	public String getContentDetails() {
		return  contentDetails;
	}

	public void setContentDetails(String  contentDetails) {
		 this.contentDetails = contentDetails;
	}
	
	public String getComment() {
		return  comment;
	}

	public void setComment(String  comment) {
		 this.comment = comment;
	}
	
	public String getRequirement() {
		return  requirement;
	}

	public void setRequirement(String  requirement) {
		 this.requirement = requirement;
	}
	
	public String getPeopleNumber() {
		return  peopleNumber;
	}

	public void setPeopleNumber(String  peopleNumber) {
		 this.peopleNumber = peopleNumber;
	}
	
}