/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.stock.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.px.core.service.TreeService;
import com.px.basic.stock.domain.BasicsStock;
import com.px.basic.stock.mapper.BasicsStockMapper;

/**
 * 存货属性Service
 * @author 品讯科技
 * @version 2022-05-27
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class BasicsStockService extends TreeService<BasicsStockMapper, BasicsStock> {

	public boolean saveOrUpdate(BasicsStock basicsStock) {
		return super.saveOrUpdate (basicsStock);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
