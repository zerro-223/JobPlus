package com.jobplus.common.dto;

import java.util.List;

public class PageResult<T> {
    private List<T> list;
    private int page;
    private int size;
    private long total;
    private int totalPages;

    public PageResult() {}
    public PageResult(List<T> list, int page, int size, long total) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
    }

    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
}
