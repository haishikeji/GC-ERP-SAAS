/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.basic.customer.domain.Customer;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.basic.customer.service.dto.CustomerDTO;

import java.util.List;

/**
 * 客户管理MAPPER接口
 * @author 品讯科技
 * @version 2022-05-27
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    /**
     * 根据id获取客户管理
     * @param id
     * @return
     */
    CustomerDTO findById(String id);

    /**
     * 获取客户管理列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <CustomerDTO> findList(Page <CustomerDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);


    void updateDelById(Customer customer);

    void deleteByIds(String id);


    /**
     * 根据用户名称查询信息
     * @param id
     * @return
     */
    CustomerDTO findByName(String name);

    List<CustomerDTO> findAll();

}
