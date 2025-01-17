/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.inventoryStock.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存货库存Entity
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_bas_inventory_warehouse")
public class InventoryStock {

    private static final long serialVersionUID = 1L;

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
     * 在库库存
     */
    private String inventoryInStock;
    /**
     * 可用库存
     */
    private String availableStock;
    /**
     * 锁定库存
     */
    private String lockStock;

}
