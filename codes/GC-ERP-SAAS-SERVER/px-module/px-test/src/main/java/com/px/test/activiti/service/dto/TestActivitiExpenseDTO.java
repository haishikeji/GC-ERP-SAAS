/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
package com.px.test.activiti.service.dto;

import com.px.sys.service.dto.UserDTO;
import javax.validation.constraints.NotNull;
import com.px.sys.service.dto.OfficeDTO;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 报销申请Entity
 * @author 品讯科技
 * @version 2022-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestActivitiExpenseDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 员工姓名
     */
	//(message="员工姓名不能为空")
    @Query(tableColumn = "a.name", javaField = "user.id", type = QueryType.LIKE)
	private UserDTO user;
	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 报销费用
     */
	//(message="报销费用不能为空")
	private Integer cost;
	/**
     * 归属部门
     */
	//(message="归属部门不能为空")
	private OfficeDTO office;
	/**
     * 报销事由
     */
	//(message="报销事由不能为空")
	private String reason;
	/**
     * 备注信息
     */
	private String remarks;

}
