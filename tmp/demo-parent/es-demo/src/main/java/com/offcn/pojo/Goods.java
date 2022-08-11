package com.offcn.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author qf_meng
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Goods extends Model {

    private static final long serialVersionUID = 1L;

    private Double id;

    private String title;

    private BigDecimal price;

    private Double stock;

    @TableField("saleNum")
    private Double salenum;

    @TableField("createTime")
    private Date createtime;

    @TableField("categoryName")
    private String categoryname;

    @TableField("brandName")
    private String brandname;

    private String spec;
    @TableField(exist = false)
    private Map map;


}
