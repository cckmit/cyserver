package ltd.moore.ctravel.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
public class ScoreVO implements Serializable {
    //评分人Id
    private String customerId;
    private String account;
    private String scoreId;
    private String score;
    //被评分人ID
    private String rateredId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRateredId() {
        return rateredId;
    }

    public void setRateredId(String rateredId) {
        this.rateredId = rateredId;
    }
}
