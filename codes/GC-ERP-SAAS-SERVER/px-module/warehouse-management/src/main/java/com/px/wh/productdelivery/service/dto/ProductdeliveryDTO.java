/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.productdelivery.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.px.basic.customer.service.dto.ReceivingAddrDTO;
import com.px.sales.salesorder.service.dto.SalesorderDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品出库单Entity
 * @author 品讯科技
 * @version 2022-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductdeliveryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 出库单编号
     */
    @Query(tableColumn = "a.delivery_order_no", javaField = "deliveryOrderNo", type = QueryType.LIKE)
    private String deliveryOrderNo;
    /**
     * 销售订单
     */
    //(message = "销售订单不能为空")
    private SalesorderDTO salesorder;

    /*收货地址*/
    private ReceivingAddrDTO receivingAddr;

    private SalesorderDTO saleorder;

    @TableField(exist = false)
    private String saleorderCodes;

    @TableField(exist = false)
    private String customerNames;
    /**
     * 出库类型
     */
    //(message = "出库类型不能为空")
    @Query(tableColumn = "a.issue_type", javaField = "issueType", type = QueryType.EQ)
    private String issueType;
    /**
     * 出库单状态
     */
    @Query(tableColumn = "a.productdelivery_state", javaField = "productdeliveryState", type = QueryType.EQ)
    private String productdeliveryState;
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
    private List<ProductdeliverydetailsDTO> productdeliverydetailsDTOList = Lists.newArrayList();

    /**
     * 入库开始时间
     */
    @Query(tableColumn = "a.create_date", javaField = "materialIntoBegin", type = QueryType.GE)
    private Date materialIntoBegin;
    /**
     * 入库结束时间
     */
    @Query(tableColumn = "a.create_date", javaField = "materialIntoEnd", type = QueryType.LE)
    private Date materialIntoEnd;

    //客户名称
    @TableField(exist = false)
    @Query(tableColumn = "customer1.customer_name", javaField = "customerName", type = QueryType.LIKE)
    private String customerName;

    //出库人
    @TableField(exist = false)
    @Query(tableColumn = "sysuser.name", javaField = "name", type = QueryType.LIKE)
    private String name;

    //出发地
    private String destination;

    //目的地
    private String departure;
    /**
     * 中转地
     */
    private String transfer;
    /**
     * 总里程
     */
    private String mileage;
    /**
     * 所需时间
     */
    private String totalTime;

}
