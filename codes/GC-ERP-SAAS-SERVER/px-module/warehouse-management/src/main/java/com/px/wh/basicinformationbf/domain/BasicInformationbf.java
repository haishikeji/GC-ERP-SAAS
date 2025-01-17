/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.basicinformationbf.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 信息Entity
 * @author 品讯科技
 * @version 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hs_basic_information")
public class BasicInformationbf extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 公司名称
     */
	private String corporateName;
	/**
     * 社会信用代码
     */
	private String socialCreditCode;
	/**
     * 所属行业
     */
	private String industry;
	/**
     * 联系人
     */
	private String contacts;
	/**
     * 联系方式
     */
	private String contactsTel;
	/**
     * 营业执照
     */
	private String businessLicense;
	/**
     * logo
     */
	private String logo;
	/**
     * 平台名称
     */
	private String platformName;
	/**
     * 服务器
     */
	private String selectServe;
	/**
     * 数据库名
     */
	private String dbName;
	/**
     * 数据库帐号
     */
	private String databaseAccount;
	/**
     * 数据库密码
     */
	private String databasePwd;
	/**
     * redis库号
     */
	private Long redisNum;
	/**
     * 管理员账号
     */
	private String adminAccount;
	/**
     * 管理员密码
     */
	private String adminPas;
	/**
     * 月销售额
     */
	private String monthlySales;
	/**
     * 月销售单数
     */
	private String monthlyNum;
	/**
     * 是否自动跑数据
     */
	private String runData;
	/**
     * 跑数据时间
     */
	private Date runTime;
	/**
	 * 开始时间
	 */
	private Date stratTime;
	/**
     * 结束时间
     */
	private Date endTime;
	/**
     * 截止时间
     */
	private Date deadline;
	/**
     * 编码规则
     */
	private String codingRules;

}
