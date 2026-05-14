package com.jobplus.entity;

import java.util.Date;

/**
 * 职位分类表实体
 */
public class JobCategory {

    private Integer id;
    private String name;
    private Integer parentId;
    private Integer sortOrder;
    private Date createdAt;

    public JobCategory() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
