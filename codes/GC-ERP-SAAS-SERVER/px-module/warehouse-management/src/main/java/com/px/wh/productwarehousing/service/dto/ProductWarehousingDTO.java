/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.wh.productwarehousing.service.dto;

import com.px.produce.produceplan.service.dto.ProducePlanDTO;
import com.px.sales.salesorder.service.dto.SalesorderDTO;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 产品入库单Entity
 * @author 品讯科技
 * @version 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductWarehousingDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 入库单编号
     */
    @Query(tableColumn = "a.warehousing_order_no", javaField = "warehousingOrderNo", type = QueryType.LIKE)
	private String warehousingOrderNo;
	/**
     * 销售订单
     */
	private SalesorderDTO salesorder;
	/**
	 * 生产计划单
	 */
	private ProducePlanDTO produceplan;
	/**
     * 入库单状态
     */
	//(message="入库单状态不能为空")
	private String warehousingStatus;
	/**
	 * 入库单类型
	 */
	//(message="入库单类型不能为空")
	@Query(tableColumn = "a.warehousing_type", javaField = "warehousingType", type = QueryType.EQ)
	private String warehousingType;
	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String result;
    /**
     *子表列表
     */
	private List<ProductWarehousingDetailsDTO> productWarehousingDetailsDTOList = Lists.newArrayList();

	/**
	 * 销售订单编号
	 */
	@Query(tableColumn = "salesorder.salesorder_code", javaField = "salesorderCode", type = QueryType.LIKE)
	private String salesorderCode;
	/**
	 * 生产计划单编号
	 */
	@Query(tableColumn = "spp.produceplan_code", javaField = "produceplanCode", type = QueryType.LIKE)
	private String produceplanCode;
}
