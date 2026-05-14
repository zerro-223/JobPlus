package com.jobplus.entity;

/**
 * 项目经历
 */
public class ProjectExperience {

    private Integer id;
    private Integer resumeId;
    private String name;
    private String role;
    private String startDate;
    private String endDate;
    private String description;
    private String url;
    private Integer sortOrder;

    public ProjectExperience() {}

    public ProjectExperience(Integer id, Integer resumeId, String name, String role,
                             String startDate, String endDate, String description,
                             String url, Integer sortOrder) {
        this.id = id;
        this.resumeId = resumeId;
        this.name = name;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.url = url;
        this.sortOrder = sortOrder;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getResumeId() { return resumeId; }
    public void setResumeId(Integer resumeId) { this.resumeId = resumeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    @Override
    public String toString() {
        return "ProjectExperience{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
