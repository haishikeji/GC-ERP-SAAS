/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.codingrules.service.dto;

import javax.validation.constraints.NotNull;

import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 编码规则DTO
 * @author 品讯科技
 * @version 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CodingRulesDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 规则名称
     */
	//(message="规则名称不能为空")
    @Query(type = QueryType.EQ)
	private String name;
	/**
     * 前缀
     */
	//(message="前缀不能为空")
	private String prefix;
	/**
     * 日期规则(yyyyMMdd)
     */
	//(message="日期规则(yyyyMMdd)不能为空")
	private String dateMiddle;
	/**
     * 后缀位数
     */
	//(message="后缀位数不能为空")
	private Integer suffix;
	/**
     * 后缀重置规则(年/月)
     */
	//(message="后缀重置规则(年/月)不能为空")
	private String suffixRules;
	/**
     * 连接符号
     */
	private String connectionSymbol;
	/**
     * 后缀实时编号
     */
	private Integer num;
	/**
     * 当前最新编号
     */
	private String currentLatestNumber;

}
