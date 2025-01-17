/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.processadmin.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.produce.processadmin.domain.ProcessManagement;
import com.px.produce.processadmin.mapper.ProcessManagementMapper;
import com.px.produce.processadmin.service.dto.ProcessManagementDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工序管理Service
 * @author 品讯科技
 * @version 2022-06-13
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class ProcessManagementService extends ServiceImpl<ProcessManagementMapper, ProcessManagement> {

    public IPage<ProcessManagementDTO> findPage(Page<ProcessManagementDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除*/
        return  baseMapper.findList (page, queryWrapper);
    }

    public List<ProcessManagement> list1() {
        return baseMapper.list1();
    }
}
