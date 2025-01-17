/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.prints.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.basic.prints.domain.Prints;
import com.px.basic.prints.mapper.PrintsMapper;

/**
 * 打印配置Service
 * @author 品讯科技
 * @version 2022-07-05
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class PrintsService extends ServiceImpl<PrintsMapper, Prints> {

}
