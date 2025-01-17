/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.warehousing.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 产品入库明细详情Entity
 * @author 品讯科技
 * @version 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_wh_product_warehousing_details")
public class WarehousingReportDetails extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 产品入库明细Id
     */
	private String warehousingId;
	/**
     * 产品id
     */
	private String inventoryId;
	/**
     * 当前入库数量
     */
	private String warehousingQuantity;
	/**
     * 出库仓库
     */
    @TableField("ex_warehouse")
	private String exWarehouseId;
	/**
     * 备注
     */
	private String remark;

}
