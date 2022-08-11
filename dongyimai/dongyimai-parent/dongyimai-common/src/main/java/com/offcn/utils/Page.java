package com.offcn.utils;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private List<T> list; // 每页查询出来的数据存放的集合
    private int pageSize = 10; // 每页显示的记录数
    private int pageNo; // 当前页，通过用户传入
    //	private int totalPageNo; // 总页数，通过计算得到
    private long totalRecord; // 总记录数，通过查询数据库得到

    private int firstPage;    //分页页码集合起始记录

    private int lastPage;      //分页页码集合结束记录

    private int maxPage;  //分页最大页码

    private boolean firstDot = true;
    private boolean lastDot = true;


    public Page() {

    }

    public Page(long totalRecord,int pageNo,int pageSize) {
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.pageNo = pageNo;
        this.firstPage = 1;
        this.lastPage = (int)this.getTotalPageNo();
        this.maxPage = (int)this.getTotalPageNo();
        setPageLabel();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getPageNo() {
        if (pageNo < 1) {
            // 如果当前页码小于1，直接返回1
            return 1;
        } else if (pageNo > getTotalPageNo()) {
            // 如果当前页码大于总页数，返回总页数
            return getTotalPageNo();
        } else {
            return pageNo;
        }
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    // 总页数是由总记录数和每页显示的条数计算得到
    public long getTotalPageNo() {
        if (totalRecord % pageSize == 0) {
            return totalRecord / pageSize;
        } else {
            return totalRecord / pageSize + 1;
        }
    }


    public void setPageLabel(){
        if(maxPage>5){
            if(pageNo<=3){
                lastPage = 5;
                firstDot = false;
            }else if(pageNo>=maxPage-2){
                firstPage = maxPage-4;
                lastDot = false;
            }else{
                firstPage = pageNo-2;
                lastPage = pageNo+2;
            }
        }else{
            firstDot = false;
            lastDot = false;
        }
    }

    public int getFirstPage(){
        return this.firstPage;
    }
    public int getLastPage(){
        return this.lastPage;
    }

    // public void setTotalPageNo(int totalPageNo) {
    // this.totalPageNo = totalPageNo;
    // }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    // 判断是否有上一页
    public boolean hasPrev() {
        return getPageNo() > 1;
    }

    // 获取上一页
    public long getPrev() {
        return hasPrev() ? getPageNo() - 1 : 1;
    }

    // 判断是否有下一页
    public boolean hasNext() {
        return getPageNo() < getTotalPageNo();
    }

    // 获取下一页
    public long getNext() {
        return hasNext() ? getPageNo() + 1 : getTotalPageNo();
    }

    public boolean isFirstDot(){
        return this.firstDot;
    }
    public boolean isLastDot(){
        return this.lastDot;
    }
}
