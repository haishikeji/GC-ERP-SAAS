/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.pic.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 图片管理Entity
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_pic")
public class TestPic extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 标题
     */
	private String title;
	/**
     * 图片路径
     */
	private String pic;
	/**
     * 备注信息
     */
	private String remarks;

}
