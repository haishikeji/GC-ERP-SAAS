/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.salesorder.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.sales.salesorder.service.dto.SalesgoodsDTO;
import com.px.sales.salesorder.service.dto.SalesorderdetailsDTO1;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.sales.salesorder.service.dto.SalesorderdetailsDTO;
import com.px.sales.salesorder.domain.Salesorderdetails;
import com.px.sales.salesorder.mapper.SalesorderdetailsMapper;

/**
 * 销售订单详情Service
 *
 * @author 品讯科技
 * @version 2022-05-30
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class SalesorderdetailsService extends ServiceImpl<SalesorderdetailsMapper, Salesorderdetails> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public SalesorderdetailsDTO findById(String id) {
        return baseMapper.findById(id);
    }

    /**
     * 查询列表
     *
     * @param salesorderId
     * @return
     */
    public List<SalesorderdetailsDTO> findList(String salesorderId) {
        return baseMapper.findList(salesorderId);
    }

    public List<SalesorderdetailsDTO1> findList1(HashMap hashMap) {
        return baseMapper.findList1(hashMap);
    }

    /**
     * 自定义分页检索
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<SalesorderdetailsDTO> findPage(Page<SalesorderdetailsDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq("a.del_flag", 0); // 排除已经删除
        return baseMapper.findPage(page, queryWrapper);
    }

    //数量大于100
    public void updatesalaslzede(String id) {
        baseMapper.updatesalaslzede(id);
    }

    //数量大于10
    public void updatesalaslzede1(String id) {
        baseMapper.updatesalaslzede1(id);
    }

    //价格大于100
    public void updatesalaslzede2(String id) {
        baseMapper.updatesalaslzede2(id);
    }

    //价格大于10
    public void updatesalaslzede3(String id) {
        baseMapper.updatesalaslzede3(id);
    }

    public IPage<SalesgoodsDTO> findListAll(Page<SalesgoodsDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrappe, Date materialOutBegin, Date materialOutEnd,  List<String> inventoryName, List<String> customerName) {
        String Begin = null;
        String end = null;
        ArrayList<String> inventoryNameAll = null;
        if (materialOutBegin != null) {
            Begin = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(materialOutBegin)+" 00:00:00";
        }
        if (materialOutEnd != null) {
            end = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(materialOutEnd)+" 23:59:59";
        }
        return baseMapper.findByAll(page, queryWrappe, Begin, end, inventoryName, customerName);
    }

}
