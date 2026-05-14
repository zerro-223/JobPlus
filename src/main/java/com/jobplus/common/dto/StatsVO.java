package com.jobplus.common.dto;

/**
 * 统计数据视图对象
 */
public class StatsVO {

    private long userCount;
    private long companyCount;
    private long positionCount;
    private long deliveryCount;
    private long pendingCompanyCount;
    private long pendingPositionCount;

    public StatsVO() {}

    public StatsVO(long userCount, long companyCount, long positionCount, long deliveryCount,
                   long pendingCompanyCount, long pendingPositionCount) {
        this.userCount = userCount;
        this.companyCount = companyCount;
        this.positionCount = positionCount;
        this.deliveryCount = deliveryCount;
        this.pendingCompanyCount = pendingCompanyCount;
        this.pendingPositionCount = pendingPositionCount;
    }

    public long getUserCount() { return userCount; }
    public void setUserCount(long userCount) { this.userCount = userCount; }

    public long getCompanyCount() { return companyCount; }
    public void setCompanyCount(long companyCount) { this.companyCount = companyCount; }

    public long getPositionCount() { return positionCount; }
    public void setPositionCount(long positionCount) { this.positionCount = positionCount; }

    public long getDeliveryCount() { return deliveryCount; }
    public void setDeliveryCount(long deliveryCount) { this.deliveryCount = deliveryCount; }

    public long getPendingCompanyCount() { return pendingCompanyCount; }
    public void setPendingCompanyCount(long pendingCompanyCount) { this.pendingCompanyCount = pendingCompanyCount; }

    public long getPendingPositionCount() { return pendingPositionCount; }
    public void setPendingPositionCount(long pendingPositionCount) { this.pendingPositionCount = pendingPositionCount; }

    @Override
    public String toString() {
        return "StatsVO{" +
                "userCount=" + userCount +
                ", companyCount=" + companyCount +
                ", positionCount=" + positionCount +
                ", deliveryCount=" + deliveryCount +
                ", pendingCompanyCount=" + pendingCompanyCount +
                ", pendingPositionCount=" + pendingPositionCount +
                '}';
    }
}
