/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.insertdata.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 插入数据Entity
 * @author 品讯科技
 * @version 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_bas_insert_data")
public class InsertData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 开始时间
     */
    @TableField("stratTime")
	private Date stratTime;
	/**
     * 结束时间
     */
    @TableField("endTime")
	private Date endTime;
	/**
     * 销售年度最大总额
     */
    @TableField("productYearMax")
	private Double productYearMax;
	/**
     * 销售年度最小总额
     */
    @TableField("productYearMin")
	private Double productYearMin;
	/**
     * 采购年度最大总额
     */
    @TableField("purYearMax")
	private Double purYearMax;
	/**
     * 采购年度最小总额
     */
    @TableField("purYearMin")
	private Double purYearMin;
	/**
     * 销售月最大总额
     */
    @TableField("productMax")
	private Double productMax;
	/**
     * 销售月最小总额
     */
    @TableField("productMin")
	private Double productMin;
	/**
     * 采购月最大总额
     */
    @TableField("purMax")
	private Double purMax;
	/**
     * 采购月最小总额
     */
    @TableField("purMin")
	private Double purMin;
	/**
     * 月销售单数
     */
    @TableField("producMonthCount")
	private Integer producMonthCount;
	/**
     * 月采购单数
     */
    @TableField("purMonthCount")
	private Integer purMonthCount;
	/**
     * 月销售详情产品数
     */
    @TableField("producMonthDelCount")
	private Integer producMonthDelCount;
	/**
     * 月采购详情产品数
     */
    @TableField("purMonthDelCount")
	private Integer purMonthDelCount;

	/**
	 * 库存总价值过大
	 */
	@TableField("kcjzd")
	private Integer kcjzd;
	/**
	 * 库存总价值过小
	 */
	@TableField("kcjzdmin")
	private Double kcjzdmin;
	/**
	 * 库存总价
	 */
	@TableField("kczj")
	private BigDecimal kczj;

}
