package com.op.technicalcase.model;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageableConversionListObject {
    private PageInfo pageInfo;
    private List<Conversion> conversionList;

    public PageableConversionListObject(Page<Conversion> pageableConversionList){
        this.pageInfo = new PageInfo(pageableConversionList.getTotalElements(),
                                     pageableConversionList.getTotalPages(),
                                     pageableConversionList.getPageable());
        this.conversionList = pageableConversionList.getContent();
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public List<Conversion> getConversionList() {
        return conversionList;
    }

    @Override
    public String toString() {
        return "PageableConversionListObject{" +
                "pageInfo=" + pageInfo +
                ", conversionList=" + conversionList +
                '}';
    }
}
