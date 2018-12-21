package com.apiserver.kernel.page;

import java.io.Serializable;

/**
 *
 * @author jarvis
 * @date 2018/9/17
 */
public class Pager implements Serializable {

    //每页数据个数
    private int pageSize = 10;
    //总数
    private int total;
    //当前页
    private int pageIndex = 0;
    //共有多少页
    private int pages;

    public Pager(){

    }
    public Pager(int pageIndex, int pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



    public int getPages() {
        return (int) Math.ceil(((double)total)/pageSize);
    }
    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
