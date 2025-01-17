/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.productdelivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.wh.productdelivery.domain.Productdelivery;
import com.px.wh.productdelivery.service.dto.ProductdeliveryDTO;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 产品出库单MAPPER接口
 * @author 品讯科技
 * @version 2022-06-02
 */
public interface ProductdeliveryMapper extends BaseMapper<Productdelivery> {

    /**
     * 根据id获取产品出库单
     * @param id
     * @return
     */
    ProductdeliveryDTO findById(String id);

    /**
     * 获取产品出库单列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <ProductdeliveryDTO> findList(Page <ProductdeliveryDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
