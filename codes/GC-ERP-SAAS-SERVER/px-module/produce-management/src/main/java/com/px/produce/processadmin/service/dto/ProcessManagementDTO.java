/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.processadmin.service.dto;

import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
/**
 * 工序管理DTO
 * @author 品讯科技
 * @version 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessManagementDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 工序名称
     */
	//(message="工序名称不能为空")
    @Query(type = QueryType.LIKE)
	private String processName;
	/**
     * 前置工序
     */
	private String processLead ;

	/**
	 * 前置工序名称
	 */
	private String processLeadName ;
}
