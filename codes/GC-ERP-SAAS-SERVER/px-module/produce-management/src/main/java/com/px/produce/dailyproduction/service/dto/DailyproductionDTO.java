/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.produce.dailyproduction.service.dto;

import com.px.produce.produceplan.service.dto.ProducePlanDTO;
import java.util.List;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import com.px.sales.salesorder.service.dto.SalesorderDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 生产日报Entity
 * @author 品讯科技
 * @version 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DailyproductionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 生产任务计划单
     */
    @Query(tableColumn = "a.produceplan_id", javaField = "produceplanCode.id", type = QueryType.LIKE)
	private ProducePlanDTO produceplanCode;
	/**
     * 当日完成情况
     */
	private String completion;
	/**
     * 销售订单
     */
	private String salesorderId;

	private SalesorderDTO salesorder;
    /**
     *子表列表
     */
	private List<DailyproductionDetailsDTO> dailyproductionDetailsDTOList = Lists.newArrayList();

}
