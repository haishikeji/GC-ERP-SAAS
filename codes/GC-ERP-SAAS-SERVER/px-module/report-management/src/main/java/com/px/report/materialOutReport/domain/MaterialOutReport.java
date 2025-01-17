/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.materialOutReport.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物料出库详情Entity
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_produce_material_issue_detail")
public class MaterialOutReport {

    private static final long serialVersionUID = 1L;

    /**
     * 存货ID
     */
    private String inventoryId;
    /**
     * 存货名称
     */
    private String inventoryName;
    /**
     * 存货类型
     */
    private String inventoryType;
    /**
     * 存货编码
     */
    private String inventoryCode;
    /**
     * 存货合计出库数量
     */
    private String inventoryOutNum;
//    /**
//     * 在库库存
//     */
//    private String inventoryInStock;
//    /**
//     * 可用库存
//     */
//    private String availableStock;
//    /**
//     * 锁定库存
//     */
//    private String lockStock;

}
