/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialreturn.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.basic.codingrules.service.CodingRulesService;
import com.px.basic.inventorywarehouse.service.InventoryWarehouseService;
import com.px.basic.inventorywarehouse.service.dto.InventoryWarehouseVO;
import com.px.sys.constant.CommonConstants;
import com.px.wh.materialinto.service.MaterialIntoService;
import com.px.wh.materialinto.service.dto.MaterialIntoDTO;
import com.px.wh.materialinto.service.dto.MaterialIntoDetailDTO;
import com.px.wh.productdelivery.service.ProductdeliveryService;
import com.px.wh.materialreturn.service.dto.MaterialReturnDTO;
import com.px.wh.materialreturn.service.dto.MaterialReturnDetailDTO;
import com.px.wh.materialreturn.service.mapstruct.MaterialReturnDetailWrapper;
import com.px.wh.materialreturn.service.mapstruct.MaterialReturnWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.wh.materialreturn.domain.MaterialReturn;
import com.px.wh.materialreturn.domain.MaterialReturnDetail;
import com.px.wh.materialreturn.mapper.MaterialReturnMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 退料管理Service
 * @author 品讯科技
 * @version 2022-06-17
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class MaterialReturnService extends ServiceImpl<MaterialReturnMapper, MaterialReturn> {
	/**
	* 子表service
	*/
	@Autowired
	private MaterialReturnDetailService materialReturnDetailService;
	@Autowired
	private MaterialIntoService materialIntoService;
	@Autowired
	private ProductdeliveryService productdeliveryService;
	@Autowired
	private CodingRulesService codingRulesService;
	@Autowired
	private InventoryWarehouseService inventoryWarehouseService;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MaterialReturnDTO findById(String id) {
		MaterialReturnDTO materialReturnDTO = baseMapper.findById ( id );
		materialReturnDTO.setMaterialReturnDetailDTOList(materialReturnDetailService.findList(id));
		return materialReturnDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <MaterialReturnDTO> findPage(Page <MaterialReturnDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  materialReturnDTO
	* @return
	*/
	public void saveOrUpdate(MaterialReturnDTO materialReturnDTO) throws Exception {
		MaterialReturn materialReturn =  MaterialReturnWrapper.INSTANCE.toEntity ( materialReturnDTO );
		super.saveOrUpdate (materialReturn);
		//记录入库//退货入库纪录
		MaterialIntoDTO materialIntoDTO=new MaterialIntoDTO();
		String inCode = codingRulesService.getCodingRules("物料入库编号").getBody();
		materialIntoDTO.setMaterialCode(inCode);
		materialIntoDTO.setMaterialState("2");
		materialIntoDTO.setMaterialType("2");

		List<MaterialIntoDetailDTO> materialIntoDetailDTOList=new ArrayList<>();
		List<InventoryWarehouseVO> stockList=new ArrayList<>();


		for (MaterialReturnDetailDTO materialReturnDetailDTO : materialReturnDTO.getMaterialReturnDetailDTOList ()){
			if ( CommonConstants.DELETED.equals ( materialReturnDetailDTO.getDelFlag()) ){
				materialReturnDetailService.removeById ( materialReturnDetailDTO.getId () );
			}else{
				MaterialReturnDetail materialReturnDetail = MaterialReturnDetailWrapper.INSTANCE.toEntity ( materialReturnDetailDTO );
				materialReturnDetail.setReturnId ( materialReturn.getId () );
				materialReturnDetailService.saveOrUpdate ( materialReturnDetail );
			}
			MaterialIntoDetailDTO materialIntoDetailDTO=new MaterialIntoDetailDTO();
			materialIntoDetailDTO.setInventory(materialReturnDetailDTO.getInventory());
			materialIntoDetailDTO.setMaterialIntoNum(materialReturnDetailDTO.getNum());
			materialIntoDetailDTO.setMaterialIntoHouse(materialReturnDTO.getReturnWarehouse());
			materialIntoDetailDTOList.add(materialIntoDetailDTO);

			//添加入库库存 判断是否已存在
			InventoryWarehouseVO inStock=new InventoryWarehouseVO();
			inStock.setInventoryId(materialReturnDetailDTO.getInventory().getId());
			inStock.setWarehouseId(materialReturnDTO.getReturnWarehouse().getId());
			inStock.setNum(materialReturnDetailDTO.getNum());
			inStock.setType(1);
			stockList.add(inStock);
		}
		inventoryWarehouseService.changeNum(stockList);
		materialIntoDTO.setMaterialIntoDetailDTOList(materialIntoDetailDTOList);
		materialIntoService.save(materialIntoDTO);
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
		materialReturnDetailService.lambdaUpdate ().eq ( MaterialReturnDetail::getReturnId, id ).remove ();
	}

	public List<MaterialReturnDTO> queryByDate(String date) {
		return baseMapper.queryByDate(date);
	}
}
