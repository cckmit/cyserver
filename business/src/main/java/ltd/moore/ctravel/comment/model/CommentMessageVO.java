package ltd.moore.ctravel.comment.model;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * CommentMessage对象
 * @author caicai
 * @version 1.0
 */

public class CommentMessageVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 *评论ID
	 */
	private String commentId;
	
	/*
	 *客户ID
	 */
	private String customerId;
	/*
 	*评论人账号
	 */
	private String account;
	
	/*
	 *体验ID
	 */
	private String experienceId;
	
	/*
	 *评价内容
	 */
	private String commentContent;
	
	/*
	 *审核状态(0:未审核 1:审核通过 2:审核失败)
	 */
	private int checkStatus;
	
	/*
	 *审核人
	 */
	private String checkPeople;
	
	/*
	 *备注
	 */
	private String remark;
	/*
 	*创建时间
 	*/

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	private Timestamp createTime;
	/*
	 *修改时间
	 */
	private Timestamp updateTime;
	public CommentMessageVO(){}
	
	public String getCommentId() {
		return  commentId;
	}

	public void setCommentId(String  commentId) {
		 this.commentId = commentId;
	}
	
	public String getCustomerId() {
		return  customerId;
	}

	public void setCustomerId(String  customerId) {
		 this.customerId = customerId;
	}
	
	public String getExperienceId() {
		return  experienceId;
	}

	public void setExperienceId(String  experienceId) {
		 this.experienceId = experienceId;
	}
	
	public String getCommentContent() {
		return  commentContent;
	}

	public void setCommentContent(String  commentContent) {
		 this.commentContent = commentContent;
	}
	
	public int getCheckStatus() {
		return  checkStatus;
	}

	public void setCheckStatus(int  checkStatus) {
		 this.checkStatus = checkStatus;
	}
	
	public String getCheckPeople() {
		return  checkPeople;
	}

	public void setCheckPeople(String  checkPeople) {
		 this.checkPeople = checkPeople;
	}
	
	public String getRemark() {
		return  remark;
	}

	public void setRemark(String  remark) {
		 this.remark = remark;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}