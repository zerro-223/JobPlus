package com.jobplus.entity;

import java.util.Date;

/**
 * 企业认证资料表实体
 */
public class CompanyCertification {

    private Integer id;
    private Integer companyId;
    private String licenseUrl;
    private String licenseNumber;
    private String legalPerson;
    private String rejectReason;
    private Integer status;     // 0=待审核 1=通过 2=驳回
    private Integer auditorId;
    private Date auditedAt;
    private Date createdAt;
    private Date updatedAt;

    public CompanyCertification() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCompanyId() { return companyId; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }
    public String getLicenseUrl() { return licenseUrl; }
    public void setLicenseUrl(String licenseUrl) { this.licenseUrl = licenseUrl; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public String getLegalPerson() { return legalPerson; }
    public void setLegalPerson(String legalPerson) { this.legalPerson = legalPerson; }
    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getAuditorId() { return auditorId; }
    public void setAuditorId(Integer auditorId) { this.auditorId = auditorId; }
    public Date getAuditedAt() { return auditedAt; }
    public void setAuditedAt(Date auditedAt) { this.auditedAt = auditedAt; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
