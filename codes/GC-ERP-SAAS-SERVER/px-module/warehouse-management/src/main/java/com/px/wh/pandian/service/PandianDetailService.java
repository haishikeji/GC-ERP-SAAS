/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.pandian.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.basic.inventorytype.service.InventoryService;
import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.wh.pandian.mapper.PandainMapper;
import com.px.wh.pandian.service.dto.PandainDTO;
import com.px.wh.pandian.service.dto.PandianDetailDTO;
import com.px.wh.pandian.service.mapstruct.PandianDetailWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.wh.pandian.domain.PandianDetail;
import com.px.wh.pandian.mapper.PandianDetailMapper;

/**
 * 盘点单详情Service
 * @author 品讯科技
 * @version 2022-06-13
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class PandianDetailService extends ServiceImpl<PandianDetailMapper, PandianDetail> {
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private PandainMapper pandainMapper;

	/**
	* 查询列表
	* @param pandianId
	* @return
	*/
	public List <PandianDetailDTO> findList(String pandianId) {
		PandainDTO pandainDTO=pandainMapper.findById(pandianId);
		List <PandianDetailDTO> pandianDetailDTOList=super.lambdaQuery ().eq ( PandianDetail::getPandianId, pandianId ).list ().stream ().map (PandianDetailWrapper.INSTANCE::toDTO ).collect( Collectors.toList());
		pandianDetailDTOList.forEach(pandianDetailDTO -> {
			if(StringUtils.isNotBlank(pandianDetailDTO.getPandianStock())){
				pandianDetailDTO.setChayiStock(new BigDecimal(pandianDetailDTO.getPandianStock()).subtract(new BigDecimal(pandianDetailDTO.getInventoryInStock())).toString());
			}
			InventoryDTO inventory=pandianDetailDTO.getInventory();
			if(StringUtils.isNotBlank(inventory.getId())){
				inventory=inventoryService.findByWarehouse(inventory.getId(),pandainDTO.getWarehouse().getId());
				pandianDetailDTO.setInventory(inventory);
			}
		});
		return pandianDetailDTOList;
	}

}
