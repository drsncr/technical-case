package com.op.technicalcase.model;

import org.springframework.data.domain.Pageable;

public class PageInfo {
    private Long totalElementCount;
    private Integer totalPageCount;
    private Integer currentPageSize;
    private Integer currentPageNumber;
    private Integer previousPageNumber;
    private Integer nextPageNumber;

    public PageInfo(Long totalElementCount, Integer totalPageCount, Pageable pageable) {
        this.totalElementCount = totalElementCount;
        this.totalPageCount = totalPageCount;
        this.currentPageSize = pageable.getPageSize();
        this.currentPageNumber = pageable.getPageNumber();

        if(this.currentPageNumber > 0)
            this.previousPageNumber = this.currentPageNumber - 1;

        if(this.currentPageNumber < this.totalPageCount - 1)
            this.nextPageNumber = this.currentPageNumber + 1;
    }

    public Long getTotalElementCount() {
        return totalElementCount;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public Integer getCurrentPageSize() {
        return currentPageSize;
    }

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public Integer getPreviousPageNumber() {
        return previousPageNumber;
    }

    public Integer getNextPageNumber() {
        return nextPageNumber;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "totalElementCount=" + totalElementCount +
                ", totalPageCount=" + totalPageCount +
                ", currentPageSize=" + currentPageSize +
                ", currentPageNumber=" + currentPageNumber +
                ", previousPageNumber=" + previousPageNumber +
                ", nextPageNumber=" + nextPageNumber +
                '}';
    }
}
