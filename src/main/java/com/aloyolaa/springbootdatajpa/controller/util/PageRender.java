package com.aloyolaa.springbootdatajpa.controller.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int size;
    private int pages;
    private int currentPage;
    private List<PageItem> pageItems;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pageItems = new ArrayList<>();
        this.size = page.getSize();
        this.pages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        int from;
        int to;
        if (this.pages <= this.size) {
            from = 1;
            to = this.pages;
        } else {
            if (this.currentPage <= this.size / 2) {
                from = 1;
                to = this.size;
            } else if (this.currentPage >= this.pages - size / 2) {
                from = this.pages - this.size + 1;
                to = this.size;
            } else {
                from = this.currentPage - this.size / 2;
                to = this.size;
            }
        }
        for (int i = 0; i < to; i++) {
            this.pageItems.add(new PageItem(from + i, this.currentPage == from + i));
        }
    }

    public boolean isFirst() {
        return this.page.isFirst();
    }

    public boolean isLast() {
        return this.page.isLast();
    }

    public boolean isHasNext() {
        return this.page.hasNext();
    }

    public boolean isHasPrevious() {
        return this.page.hasPrevious();
    }

}
