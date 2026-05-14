package com.jobplus.entity;

import java.util.Date;

/**
 * 面试
 */
public class Interview {

    private Integer id;
    private Integer deliveryId;
    private Integer companyId;
    private Integer userId;
    private Date interviewTime;
    private String location;
    private String contact;
    private String contactPhone;
    private String remark;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;

    public Interview() {}

    public Interview(Integer id, Integer deliveryId, Integer companyId, Integer userId,
                     Date interviewTime, String location, String contact, String contactPhone,
                     String remark, Integer status, Date createdAt, Date updatedAt) {
        this.id = id;
        this.deliveryId = deliveryId;
        this.companyId = companyId;
        this.userId = userId;
        this.interviewTime = interviewTime;
        this.location = location;
        this.contact = contact;
        this.contactPhone = contactPhone;
        this.remark = remark;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getDeliveryId() { return deliveryId; }
    public void setDeliveryId(Integer deliveryId) { this.deliveryId = deliveryId; }

    public Integer getCompanyId() { return companyId; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Date getInterviewTime() { return interviewTime; }
    public void setInterviewTime(Date interviewTime) { this.interviewTime = interviewTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", deliveryId=" + deliveryId +
                ", interviewTime=" + interviewTime +
                ", status=" + status +
                '}';
    }
}
