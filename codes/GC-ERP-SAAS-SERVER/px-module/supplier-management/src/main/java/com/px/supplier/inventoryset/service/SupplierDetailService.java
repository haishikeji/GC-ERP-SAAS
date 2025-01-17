/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.supplier.inventoryset.service;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.px.basic.inventorytype.domain.Inventory;
import com.px.basic.inventorytype.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.supplier.inventoryset.service.dto.SupplierDetailDTO;
import com.px.supplier.inventoryset.domain.SupplierDetail;
import com.px.supplier.inventoryset.mapper.SupplierDetailMapper;

/**
 * 供应商存货管理Service
 * @author 品讯科技
 * @version 2022-06-09
 */
@Service
@Transactional
@DS("#session.sss")//从session获取
public class SupplierDetailService extends ServiceImpl<SupplierDetailMapper, SupplierDetail> {

	@Autowired
	private InventoryService inventoryService;
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public SupplierDetailDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param supplierManagementId
	 * @return
	 */
	public List <SupplierDetailDTO> findList(String supplierManagementId) {
		return  baseMapper.findList (supplierManagementId);
	}

	public List<SupplierDetail> listIsNotn() {
		return baseMapper.listIsNotn();
	}

	public void jiage() {
		List<SupplierDetail> listsupdel = baseMapper.listIsNotn();
		//Shippinglist one = shippinglistService.getOne(new LambdaQueryWrapper<Shippinglist>().eq(Shippinglist::getId, id));
		List<Inventory> list = inventoryService.list();
		if (list.size()>0){
			for (int i=0;i<listsupdel.size();i++) {
				Inventory one = inventoryService.getOne(new LambdaQueryWrapper<Inventory>().eq(Inventory::getId, listsupdel.get(i).getInventoryId()));
				if (one != null) {
					SupplierDetail supplierDetail = new SupplierDetail();
					if (Double.valueOf(one.getTaxincludedPrice()) > 1) {
//					supplierDetail.setHistoryPurchasePrice1(listsupdel.get(i).getNewPurchasePrice()*0.9);
//					supplierDetail.setHistoryPurchasePrice3(listsupdel.get(i).getNewPurchasePrice());
//					supplierDetail.setHistoryPurchasePrice5(listsupdel.get(i).getNewPurchasePrice()*0.8);
						supplierDetail.setNewPurchasePrice(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice1(Double.valueOf(one.getTaxincludedPrice()) * 0.9);
						supplierDetail.setHistoryPurchasePrice3(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice5(Double.valueOf(one.getTaxincludedPrice()) * 0.8);
						supplierDetail.setHistoryPurchasePrice2(Double.valueOf(one.getTaxincludedPrice()) * 1.1);
						supplierDetail.setHistoryPurchasePrice4(Double.valueOf(one.getTaxincludedPrice()) * 1.2);
						//lowerPurchasePrice 最低  highPurchasePrice 最高
						supplierDetail.setLowerPurchasePrice(Double.valueOf(one.getTaxincludedPrice()) * 0.8);
						supplierDetail.setHighPurchasePrice(Double.valueOf(one.getTaxincludedPrice()) * 1.2);
						supplierDetail.setInventoryId(listsupdel.get(i).getInventoryId());
						supplierDetail.setSupplierManagementId(listsupdel.get(i).getSupplierManagementId());
						supplierDetail.setAveragePurchasePrice(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setId(listsupdel.get(i).getId());
						baseMapper.updateById(supplierDetail);
					}else {
						supplierDetail.setNewPurchasePrice(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice1(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice3(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice5(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice2(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHistoryPurchasePrice4(Double.valueOf(one.getTaxincludedPrice()));
						//lowerPurchasePrice 最低  highPurchasePrice 最高
						supplierDetail.setLowerPurchasePrice(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setHighPurchasePrice(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setInventoryId(listsupdel.get(i).getInventoryId());
						supplierDetail.setSupplierManagementId(listsupdel.get(i).getSupplierManagementId());
						supplierDetail.setAveragePurchasePrice(Double.valueOf(one.getTaxincludedPrice()));
						supplierDetail.setId(listsupdel.get(i).getId());
						baseMapper.updateById(supplierDetail);
					}
				}

			}
		}
	}


}
