/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.dailytem.service.dto;

import javax.validation.constraints.NotNull;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 日报类型DTO
 * @author 品讯科技
 * @version 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DailyTypeDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
	//(message="名称不能为空")
	private String name;
	/**
     * 日报ID
     */
	//(message="日报ID不能为空")
	@Query(tableColumn = "a.daily_ten_id", javaField = "dailyTen.id", type = QueryType.LIKE)
	private DailyTemDTO dailyTen;

}
