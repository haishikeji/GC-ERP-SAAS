/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.shop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品类型Entity
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_category")
public class Category extends TreeEntity<Category> {

	private static final long serialVersionUID = 1L;

	/**
     * 备注信息
     */
	private String remarks;

}
