package com.cy.core.alumni.entity;
import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 5/3/17.
 */
public class AlumniWaitRegisters extends DataEntity<AlumniWaitRegisters> {

    private String userId;          //校友ID
    private String alumniId;        //校友会ID
    private String isWorked;        //是否生效

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getIsWorked() {
        return isWorked;
    }

    public void setIsWorked(String isWorked) {
        this.isWorked = isWorked;
    }
}
