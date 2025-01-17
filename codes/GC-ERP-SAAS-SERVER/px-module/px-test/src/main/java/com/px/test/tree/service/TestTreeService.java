/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.tree.service;

import com.px.test.tree.service.dto.TestTreeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.px.core.service.TreeDTOService;
import com.px.test.tree.domain.TestTree;
import com.px.test.tree.mapper.TestTreeMapper;

/**
 * 组织机构Service
 * @author 品讯科技
 * @version 2022-03-18
 */
@Service
@Transactional
public class TestTreeService extends TreeDTOService<TestTreeMapper, TestTree, TestTreeDTO> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestTreeDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	public List <TestTreeDTO> listDTO() {
		return  baseMapper.findList ();
	}

	public boolean saveOrUpdate(TestTree testTree) {
		return super.saveOrUpdate (testTree);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
