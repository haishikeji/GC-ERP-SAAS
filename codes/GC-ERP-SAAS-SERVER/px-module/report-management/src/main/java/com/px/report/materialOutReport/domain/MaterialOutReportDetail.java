/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.materialOutReport.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 物料入库详情表Entity
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_produce_material_issue_detail")
public class MaterialOutReportDetail {

    private static final long serialVersionUID = 1L;
    /**
     * 物料出库编号
     */
    private String materialCode;
    /**
     * 出库仓库
     */
    private String materialOutHouse;
    /**
     * 出库类型
     */
    private String materialType;
    /**
     * 出库数量
     */
    private String materialOutNum;
    /**
     * 出库人
     */
    private String createBy;
    /**
     * 出库时间
     */
    private Date createDate;
}
