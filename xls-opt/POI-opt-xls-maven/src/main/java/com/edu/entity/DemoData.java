package com.edu.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DemoData {
//    @ExcelProperty("字符串类型")
    private String string;
    private Date date;
    private Double doubleData;
//    @ExcelIgnore
//    private String ignore;
}
