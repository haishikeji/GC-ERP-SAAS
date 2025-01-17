/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.basicinformationbf.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 信息DTO
 * @author 品讯科技
 * @version 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasicInformationbfDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 公司名称
     */
	@Query(tableColumn = "a.corporate_name", javaField = "corporateName", type = QueryType.LIKE)

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
	@Query(tableColumn = "a.db_name", javaField = "dbName", type = QueryType.LIKE)
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
	@Query(tableColumn = "a.admin_account", javaField = "adminAccount", type = QueryType.LIKE)
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date runTime;
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date stratTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
