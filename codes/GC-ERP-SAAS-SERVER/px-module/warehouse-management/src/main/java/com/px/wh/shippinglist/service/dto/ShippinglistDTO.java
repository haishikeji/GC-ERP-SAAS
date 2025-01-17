/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.wh.shippinglist.service.dto;

import com.px.sys.service.dto.OfficeDTO;
import com.px.wh.productdelivery.service.dto.ProductdeliveryDTO;
import javax.validation.constraints.NotNull;
import com.px.basic.customer.service.dto.CustomerDTO;
import com.px.sys.service.dto.UserDTO;
import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 发货清单Entity
 * @author 品讯科技
 * @version 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShippinglistDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 发货编号
     */
    @Query(tableColumn = "a.shipping_list_no", javaField = "shippingListNo", type = QueryType.LIKE)
	private String shippingListNo;
	/**
     * 产品出库单
     */
	private ProductdeliveryDTO productDelivery;

	/**
     * 销售订单
     */
	/*//(message="销售订单不能为空")*/
	private String salesorderId;
	/**
     * 客户ID
     */
	//(message="客户ID不能为空")
	private String customerId;
	/**
     * 客户名称
     */
    /*@Query(tableColumn = "a.customer_id", javaField = "customer.id", type = QueryType.LIKE)*/
	private CustomerDTO customer;
	/**
     * 联系人
     */
    @Query(tableColumn = "a.contacts", javaField = "contacts", type = QueryType.LIKE)
	private String contacts;

	/**
	 * 联系人电话
	 */
	private String customerName;
	/**
     * 联系人电话
     */
	private String contactsPhone;
	/**
     * 联系人地址
     */
	private String contactsAddr;
	/**
     * 承运单位名称
     */
	private String nameOfCarrier;
	/**
     * 物流号
     */
	private String logisticsNo;
	/**
     * 运费
     */
	private String freight;
	/**
     * 流程实例
     */
	/*//(message="流程实例不能为空")*/
	private String procInsId;
	/**
     * 审核结果
     */
	/*//(message="审核结果不能为空")*/
	private String result;
	/**
     * 发货人
     */
	private UserDTO preparer;
	/**
     * 复核人
     */
	private UserDTO reviewed;
	/**
     * 发货日期
     */
	/*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/

	private Date deliveryTime;
	/**
     * 发货状态
     */
	private String shipmentStatus;
    /**
     *子表列表
     */
	private List<ShippinglistdetailsDTO> shippinglistdetailsDTOList = Lists.newArrayList();

	/**
	 * 车牌
	 */
	private String licensePlate;
	/**
	 * 司机名字
	 */
	private String driverName;
	/**
	 * 司机电话
	 */
	private String driverTel;
	/**
	 * 归属公司
	 */
/*	@Query(type = QueryType.EQ, tableColumn = "c.id", javaField = "companyDTO.id")
	@ApiModelProperty(hidden = true)*/
	private OfficeDTO companyDTO;

	private String tableName;

	private String companyDTOname;
	/**
	 * 复核人
	 */
	private String reviewedname;
	/**
	 * 产品出库单
	 */
	private String deliveryOrderNo;
	/**
	 * 发货人
	 */
	private String preparername;


}
