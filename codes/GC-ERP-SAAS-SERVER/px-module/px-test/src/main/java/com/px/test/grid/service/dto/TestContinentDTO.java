/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.grid.service.dto;

import javax.validation.constraints.NotNull;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 洲DTO
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestContinentDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 洲名
     */
	//(message="洲名不能为空")
    @Query(type = QueryType.LIKE)
	private String name;
	/**
     * 备注信息
     */
	private String remarks;

}
