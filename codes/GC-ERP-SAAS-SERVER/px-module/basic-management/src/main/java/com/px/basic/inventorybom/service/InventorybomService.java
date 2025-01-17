/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.inventorybom.service;

import com.px.sys.constant.CommonConstants;
import com.px.basic.inventorybom.domain.Inventorybom;
import com.px.basic.inventorybom.domain.InventorybomDetail;
import com.px.basic.inventorybom.mapper.InventorybomMapper;
import com.px.basic.inventorybom.service.dto.InventorybomDTO;
import com.px.basic.inventorybom.service.dto.InventorybomDetailDTO;
import com.px.basic.inventorybom.service.mapstruct.InventorybomDetailWrapper;
import com.px.basic.inventorybom.service.mapstruct.InventorybomWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 物料清单Service
 * @author 品讯科技
 * @version 2022-05-29
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class InventorybomService extends ServiceImpl<InventorybomMapper, Inventorybom> {
	/**
	* 子表service
	*/
	@Autowired
	private InventorybomDetailService inventorybomDetailService;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public InventorybomDTO findById(String id) {
		InventorybomDTO inventorybomDTO = baseMapper.findById ( id );
		inventorybomDTO.setInventorybomDetailDTOList(inventorybomDetailService.findList(id));
		return inventorybomDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <InventorybomDTO> findPage(Page <InventorybomDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  inventorybomDTO
	* @return
	*/
	public void saveOrUpdate(InventorybomDTO inventorybomDTO) {
		Inventorybom inventorybom =  InventorybomWrapper.INSTANCE.toEntity ( inventorybomDTO );
		super.saveOrUpdate (inventorybom);
		for (InventorybomDetailDTO inventorybomDetailDTO : inventorybomDTO.getInventorybomDetailDTOList ()){
			if ( CommonConstants.DELETED.equals ( inventorybomDetailDTO.getDelFlag()) ){
				inventorybomDetailService.removeById ( inventorybomDetailDTO.getId () );
			}else{
				InventorybomDetail inventorybomDetail = InventorybomDetailWrapper.INSTANCE.toEntity ( inventorybomDetailDTO );
				inventorybomDetail.setInventorybomId ( inventorybom.getId () );
				inventorybomDetailService.saveOrUpdate ( inventorybomDetail );
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
		inventorybomDetailService.lambdaUpdate ().eq ( InventorybomDetail::getInventorybomId, id ).remove ();
	}

}
