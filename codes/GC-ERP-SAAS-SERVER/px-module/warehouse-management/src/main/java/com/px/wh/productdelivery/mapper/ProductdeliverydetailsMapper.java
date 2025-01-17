/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.productdelivery.mapper;

;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
;
import com.px.wh.productdelivery.service.dto.ProductdeliverydetailsDTO;
import com.px.wh.productdelivery.domain.Productdeliverydetails;

/**
 * 产品出库单详情MAPPER接口
 * @author 品讯科技
 * @version 2022-06-02
 */
public interface ProductdeliverydetailsMapper extends BaseMapper<Productdeliverydetails> {

    /**
     * 根据id获取产品出库单详情
     * @param id
     * @return
     */
    ProductdeliverydetailsDTO findById(String id);

    /**
     * 获取产品出库单详情列表
     *
     * @param ProductdeliveryId
     * @return
     */
    List <ProductdeliverydetailsDTO> findList(String ProductdeliveryId);

}
