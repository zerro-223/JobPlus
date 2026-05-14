package com.jobplus.common.dto;

import java.math.BigDecimal;

public class PositionQuery extends PageRequest {
    private String keyword;
    private String city;
    private BigDecimal salaryMin;
    private String education;
    private Integer categoryId;
    private Integer status;
    private Integer companyId;

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public BigDecimal getSalaryMin() { return salaryMin; }
    public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getCompanyId() { return companyId; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }
}
