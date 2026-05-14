package com.jobplus.entity;

/**
 * 教育经历
 */
public class EducationExperience {

    private Integer id;
    private Integer resumeId;
    private String school;
    private String major;
    private String degree;
    private String startDate;
    private String endDate;
    private String description;
    private Integer sortOrder;

    public EducationExperience() {}

    public EducationExperience(Integer id, Integer resumeId, String school, String major,
                               String degree, String startDate, String endDate,
                               String description, Integer sortOrder) {
        this.id = id;
        this.resumeId = resumeId;
        this.school = school;
        this.major = major;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.sortOrder = sortOrder;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getResumeId() { return resumeId; }
    public void setResumeId(Integer resumeId) { this.resumeId = resumeId; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    @Override
    public String toString() {
        return "EducationExperience{" +
                "id=" + id +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}
