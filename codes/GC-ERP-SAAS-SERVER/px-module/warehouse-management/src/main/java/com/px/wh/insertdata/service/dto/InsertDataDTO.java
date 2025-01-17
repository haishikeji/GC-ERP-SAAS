/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.insertdata.service.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 插入数据DTO
 * @author 品讯科技
 * @version 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InsertDataDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 开始时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date stratTime;
	/**
     * 结束时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date jzendTime;
	/**
     * 销售年度最大总额
     */
	private Double productYearMax;
	/**
     * 销售年度最小总额
     */
	private Double productYearMin;
	/**
     * 采购年度最大总额
     */
	private Double purYearMax;
	/**
     * 采购年度最小总额
     */
	private Double purYearMin;
	/**
	 * 编码规则
	 */
	private String bianma;
	/**
     * 销售月最大总额
     */
	/*//(message="销售月最大总额不能为空")*/
	private Double productMax;
	/**
     * 销售月最小总额
     */
	/*//(message="销售月最小总额不能为空")*/
	private Double productMin;
	/**
     * 采购月最大总额
     */
	/*//(message="采购月最大总额不能为空")*/
	private Double purMax;
	/**
     * 采购月最小总额
     */
	/*//(message="采购月最小总额不能为空")*/
	private Double purMin;
	/**
     * 月销售单数
     */
	/*//(message="月销售单数不能为空")*/
	private Integer producMonthCount;
	/**
     * 月采购单数
     */
	/*//(message="月采购单数不能为空")*/
	private Integer purMonthCount;
	/**
     * 月销售详情产品数
     */
	/*//(message="月销售详情产品数不能为空")*/
	private Integer producMonthDelCount;
	/**
     * 月采购详情产品数
     */
	/*//(message="月采购详情产品数不能为空")*/
	private Integer purMonthDelCount;
	/**
	 * 库存总价值过大
	 */
	private Integer kcjzd;
	/**
	 * 库存总价值过小
	 */
	private Double kcjzdmin;
	/**
	 * 库存总价
	 */
	private BigDecimal kczj;

}
