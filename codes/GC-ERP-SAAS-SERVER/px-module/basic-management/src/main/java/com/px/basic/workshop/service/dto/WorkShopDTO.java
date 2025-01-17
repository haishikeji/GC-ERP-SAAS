/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.workshop.service.dto;

import javax.validation.constraints.NotNull;
import com.px.sys.service.dto.UserDTO;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 生产车间DTO
 * @author 品讯科技
 * @version 2022-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkShopDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 车间编码
     */
	//(message="车间编码不能为空")
    @Query(tableColumn = "a.code", javaField = "code", type = QueryType.LIKE)
	private String code;
	/**
     * 车间名称
     */
	//(message="车间名称不能为空")
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	private String name;
	/**
     * 车间负责人
     */
	//(message="车间负责人不能为空")
    @Query(tableColumn = "a.charge_id", javaField = "charge.id", type = QueryType.EQ)
	private UserDTO charge;
	/**
     * 车间地址
     */
	private String address;

}
