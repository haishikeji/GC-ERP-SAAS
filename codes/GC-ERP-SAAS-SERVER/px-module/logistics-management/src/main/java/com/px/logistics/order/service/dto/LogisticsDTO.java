/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.logistics.order.service.dto;

import com.google.common.collect.Lists;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 三星物流Entity
 *
 * @author 品讯科技
 * @version 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogisticsDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 订单状态0 待发  1已发  2已完成
     */
    private String state;
    /**
     * 发货客户id
     */
    private String sendId;
    /**
     * 收货客户id
     */
    private String deliveryId;
    /**
     * 发货日期
     */
    private String sendTime;
    /**
     * 预期交货日期
     */
    private String expectDate;
    /**
     * 实际交货日期
     */
    private String realityDate;
    /**
     * 备注
     */
    private String notes;


    /**
     * 发货客户名称
     */
    private String sendIdNanme;
    /**
     * 收货客户名称
     */
    private String deliveryIdName;


    /**
     * 开始时间
     */
    private Date materialOutBegin;
    /**
     * 结束时间
     */
    private Date materialOutEnd;

    /**
     * 物流ID
     */
    private String wuliuId;


    /**
     * 物流ID
     */
    private String wuliuName;

    /**
     * 发货清单id
     */
    private String shippingId;


    /**
     * 子表列表
     */
    private List<ProductdeliverydetailsDTO> productdeliverydetailsDTOS = Lists.newArrayList();

    /**
     * 联系人
     */
    private String contactsName;
    /**
     * 联系人手机
     */
    private String contactsPhone;

    /**
     * 车牌
     */
    private String license;
    /**
     * 收款类型(1代收 2 运输 3其他)
     */
    private String logType;
    /**
     * 金额
     */
    private String logMoney;
    /**
     *
     */
    private String logNumber;

    private String destination;
    private String departure;
    private String transfer;
    private String mileage;
    private String totalTime;



}
