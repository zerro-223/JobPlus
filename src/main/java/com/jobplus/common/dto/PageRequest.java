package com.jobplus.common.dto;

public class PageRequest {
    private int page = 1;
    private int size = 20;

    public PageRequest() {}
    public int getPage() { return page; }
    public void setPage(int page) { this.page = Math.max(page, 1); }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = Math.min(size, 100); }
    public int getOffset() { return (page - 1) * size; }
}
