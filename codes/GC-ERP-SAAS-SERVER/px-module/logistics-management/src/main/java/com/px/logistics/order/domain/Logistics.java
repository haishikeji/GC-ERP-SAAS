/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.logistics.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 三星物流Entity
 *
 * @author 品讯科技
 * @version 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_logistics_order")
public class Logistics extends BaseEntity {

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
     * 物流ID
     */
    private String wuliuId;
    /**
     * 发货清单id
     */
    private String shippingId;

    /**
     * 收款类型(1代收 2 运输 3其他)
     */
    private String logType;
    /**
     * 金额
     */
    private String logMoney;

    /**
     * 编号
     */
    private String logNumber;

}
