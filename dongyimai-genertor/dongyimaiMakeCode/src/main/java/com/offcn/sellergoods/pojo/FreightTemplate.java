package com.offcn.sellergoods.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import java.lang.String;
/****
 * @Author:ujiuye
 * @Description:FreightTemplate构建
 * @Date 2021/2/1 14:19
 *****/
@ApiModel(description = "FreightTemplate",value = "FreightTemplate")
@TableName(value="tb_freight_template")
public class FreightTemplate implements Serializable{

	@ApiModelProperty(value = "",required = false)
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
	private Long id;//

	@ApiModelProperty(value = "商家ID",required = false)
    @TableField(value = "seller_id")
	private String sellerId;//商家ID

	@ApiModelProperty(value = "是否默认   （‘Y’是   'N'否）",required = false)
    @TableField(value = "is_default")
	private String isDefault;//是否默认   （‘Y’是   'N'否）

	@ApiModelProperty(value = "模版名称",required = false)
    @TableField(value = "name")
	private String name;//模版名称

	@ApiModelProperty(value = "发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）",required = false)
    @TableField(value = "send_time_type")
	private String sendTimeType;//发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）

	@ApiModelProperty(value = "统一价格",required = false)
    @TableField(value = "price")
	private String price;//统一价格

	@ApiModelProperty(value = "创建时间",required = false)
    @TableField(value = "create_time")
	private Date createTime;//创建时间



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getSellerId() {
		return sellerId;
	}

	//set方法
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	//get方法
	public String getIsDefault() {
		return isDefault;
	}

	//set方法
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public String getSendTimeType() {
		return sendTimeType;
	}

	//set方法
	public void setSendTimeType(String sendTimeType) {
		this.sendTimeType = sendTimeType;
	}
	//get方法
	public String getPrice() {
		return price;
	}

	//set方法
	public void setPrice(String price) {
		this.price = price;
	}
	//get方法
	public Date getCreateTime() {
		return createTime;
	}

	//set方法
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
