/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.shop.service.dto;

import com.px.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品类型DTO
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryDTO extends TreeDTO<CategoryDTO> {

	private static final long serialVersionUID = 1L;

	/**
     * 备注信息
     */
	private String remarks;


}
