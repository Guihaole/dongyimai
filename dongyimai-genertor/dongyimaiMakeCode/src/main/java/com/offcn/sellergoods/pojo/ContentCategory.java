package com.offcn.sellergoods.pojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
/****
 * @Author:ujiuye
 * @Description:ContentCategory构建
 * @Date 2021/2/1 14:19
 *****/
@ApiModel(description = "ContentCategory",value = "ContentCategory")
@TableName(value="tb_content_category")
public class ContentCategory implements Serializable{

	@ApiModelProperty(value = "类目ID",required = false)
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
	private Long id;//类目ID

	@ApiModelProperty(value = "分类名称",required = false)
    @TableField(value = "name")
	private String name;//分类名称



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}


}
