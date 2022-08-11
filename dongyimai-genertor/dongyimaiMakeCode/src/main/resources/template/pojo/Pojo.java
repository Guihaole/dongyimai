package ${package_pojo};
<#if swagger==true>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
<#list typeSet as set>
import ${set};
</#list>
/****
 * @Author:ujiuye
 * @Description:${Table}构建
 * @Date 2021/2/1 14:19
 *****/
<#if swagger==true>
@ApiModel(description = "${Table}",value = "${Table}")
</#if>
@TableName(value="${TableName}")
public class ${Table} implements Serializable{

<#list models as model>
	<#if swagger==true>
	@ApiModelProperty(value = "${model.desc!""}",required = false)
	</#if>
	<#if model.id==true>
    @TableId(type = IdType.AUTO)
	</#if>
    @TableField(value = "${model.column}")
	private ${model.simpleType} ${model.name};//${model.desc!""}

</#list>


<#list models as model>
	//get方法
	public ${model.simpleType} get${model.upperName}() {
		return ${model.name};
	}

	//set方法
	public void set${model.upperName}(${model.simpleType} ${model.name}) {
		this.${model.name} = ${model.name};
	}
</#list>


}
