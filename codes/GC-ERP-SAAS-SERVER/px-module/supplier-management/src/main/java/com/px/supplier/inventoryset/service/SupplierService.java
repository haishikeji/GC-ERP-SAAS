/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.supplier.inventoryset.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.sys.constant.CommonConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.supplier.inventoryset.service.dto.SupplierDTO;
import com.px.supplier.inventoryset.service.dto.SupplierDetailDTO;
import com.px.supplier.inventoryset.service.mapstruct.SupplierWrapper;
import com.px.supplier.inventoryset.service.mapstruct.SupplierDetailWrapper;
import com.px.supplier.inventoryset.domain.Supplier;
import com.px.supplier.inventoryset.domain.SupplierDetail;
import com.px.supplier.inventoryset.mapper.SupplierMapper;

/**
 * 供应商存货设置Service
 * @author 品讯科技
 * @version 2022-06-09
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class SupplierService extends ServiceImpl<SupplierMapper, Supplier> {
	/**
	* 子表service
	*/
	@Autowired
	private SupplierDetailService supplierDetailService;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public SupplierDTO findById(String id) {
		SupplierDTO supplierDTO = SupplierWrapper.INSTANCE.toDTO ( super.getById ( id ) );
		supplierDTO.setSupplierDetailDTOList(supplierDetailService.findList(id));
		return supplierDTO;
	}

	/**
	 * 保存或者更新
	 * @param  supplierDTO
	 * @return
	 */
	public void saveOrUpdate(SupplierDTO supplierDTO) {
		Supplier supplier =  SupplierWrapper.INSTANCE.toEntity ( supplierDTO );
		super.saveOrUpdate (supplier);
		for (SupplierDetailDTO supplierDetailDTO : supplierDTO.getSupplierDetailDTOList ()){
			if ( CommonConstants.DELETED.equals ( supplierDetailDTO.getDelFlag()) ){
				supplierDetailService.removeById ( supplierDetailDTO.getId () );
			}else{
				SupplierDetail supplierDetail = SupplierDetailWrapper.INSTANCE.toEntity ( supplierDetailDTO );
				supplierDetail.setSupplierManagementId ( supplier.getId () );
				supplierDetailService.saveOrUpdate ( supplierDetail );
			}
		}
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
		supplierDetailService.lambdaUpdate ().eq ( SupplierDetail::getSupplierManagementId, id ).remove ();
	}

}
