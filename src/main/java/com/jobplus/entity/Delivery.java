package com.jobplus.entity;

import java.util.Date;

/**
 * 投递记录
 */
public class Delivery {

    private Integer id;
    private Integer userId;
    private Integer positionId;
    private Integer resumeId;
    private Integer status;
    private Boolean companyRead;
    private Date readAt;
    private Date createdAt;
    private Date updatedAt;

    public Delivery() {}

    public Delivery(Integer id, Integer userId, Integer positionId, Integer resumeId,
                    Integer status, Boolean companyRead, Date readAt, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.positionId = positionId;
        this.resumeId = resumeId;
        this.status = status;
        this.companyRead = companyRead;
        this.readAt = readAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }

    public Integer getResumeId() { return resumeId; }
    public void setResumeId(Integer resumeId) { this.resumeId = resumeId; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Boolean getCompanyRead() { return companyRead; }
    public void setCompanyRead(Boolean companyRead) { this.companyRead = companyRead; }

    public Date getReadAt() { return readAt; }
    public void setReadAt(Date readAt) { this.readAt = readAt; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", userId=" + userId +
                ", positionId=" + positionId +
                ", status=" + status +
                '}';
    }
}
