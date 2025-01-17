/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.wh.materialinto.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import com.px.purchasing.order.service.dto.PurchaseOrderDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
/**
 * 物料入库管理Entity
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialIntoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 物料入库编号
     */
    @Query(tableColumn = "a.material_code", javaField = "materialCode", type = QueryType.LIKE)
	private String materialCode;
	/**
     * 采购订单
     */
    @Query(tableColumn = "a.material_order_id", javaField = "materialOrder.id", type = QueryType.EQ)
	private PurchaseOrderDTO materialOrder;
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Query(tableColumn = "a.create_date", javaField = "materialBegin", type = QueryType.GE)
	private Date materialBegin;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Query(tableColumn = "a.create_date", javaField = "materialEnd", type = QueryType.LE)
	private Date materialEnd;
	/**
     * 流程实例id
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String procInsResult;
	/**
     * 物料入库状态
     */
    @Query(tableColumn = "a.material_state", javaField = "materialState", type = QueryType.EQ)
	private String materialState;
	/**
     * 物料入库类型
     */
    @Query(tableColumn = "a.material_type", javaField = "materialType", type = QueryType.EQ)
	private String materialType;
    /**
     *子表列表
     */
	private List<MaterialIntoDetailDTO> materialIntoDetailDTOList = Lists.newArrayList();

}
