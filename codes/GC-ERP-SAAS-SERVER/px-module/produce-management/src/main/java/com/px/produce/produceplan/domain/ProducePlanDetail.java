/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.produceplan.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产计划单详情表Entity
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_produce_plan_detail")
public class ProducePlanDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 生产计划主表id
     */
    private String producePlanId;
    /**
     * 存货
     */
    @TableField("inventory_id")
    private String inventoryId;
    /**
     * 仓库id
     */
    private String defaultWarehouseId;
    /**
     * 计划生产数量
     */
    private String planProduceNum;
    /**
     * 订单属性
     */
    private String orderAttribute;
    /**
     * 订货日期
     */
    private Date orderDate;
    /**
     * 交货日期
     */
    private Date deliveryDate;
    /**
     * 计划完成日期
     */
    private Date planFinishDate;
    /**
     * 实际完成日期
     */
    private Date trueFinishDate;
    /**
     * 生产状态
     */
    private String type;

    /**
     * 批号
     */
    private String salesorderBatchnumber;
}
