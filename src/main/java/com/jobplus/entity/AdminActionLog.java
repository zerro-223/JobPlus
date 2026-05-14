package com.jobplus.entity;

import java.util.Date;

/**
 * 管理员操作日志
 */
public class AdminActionLog {

    private Integer id;
    private Integer adminId;
    private String actionType;
    private Integer targetId;
    private String targetType;
    private String detail;
    private String ipAddress;
    private Date createdAt;

    public AdminActionLog() {}

    public AdminActionLog(Integer id, Integer adminId, String actionType, Integer targetId,
                          String targetType, String detail, String ipAddress, Date createdAt) {
        this.id = id;
        this.adminId = adminId;
        this.actionType = actionType;
        this.targetId = targetId;
        this.targetType = targetType;
        this.detail = detail;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public Integer getTargetId() { return targetId; }
    public void setTargetId(Integer targetId) { this.targetId = targetId; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "AdminActionLog{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", actionType='" + actionType + '\'' +
                ", targetType='" + targetType + '\'' +
                '}';
    }
}
