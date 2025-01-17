/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.warehousing.service.dto;

import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 产品入库明细详情Entity
 * @author 品讯科技
 * @version 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WarehousingReportDetailsDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	//入库单ID
	private String warehousingId;

	//入库单编号
	private String warehousingOrderNo;

	//入库类型
	private String warehousingType;

	//入库数量
	private String warehousingQuantity;

	//入库仓库
	private String warehouseName;

	//备注
	private String remark;

	//销售订单编号
	private String salesorderCode;

	//入库人
	private String createName;

}
