/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.treetable.service.dto;

import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 车辆DTO
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 品牌
     */
	private String name;
	/**
     * 车系
     */
	@Query(tableColumn = "a.kind_id", javaField = "kind.id", type = QueryType.EQ)
	private CarKindDTO kind;
	/**
     * 简介
     */
	private String remarks;

}
