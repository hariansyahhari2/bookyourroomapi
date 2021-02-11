package com.hariansyah.bookyourrooms.api.models.pagination;

import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.Max;

public class PageSearch {

    private Integer page = 0;

    private Integer size = 10;

    private Direction sort = Direction.ASC;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Direction getSort() {
        return sort;
    }

    public void setSort(Direction sort) {
        this.sort = sort;
    }

}
