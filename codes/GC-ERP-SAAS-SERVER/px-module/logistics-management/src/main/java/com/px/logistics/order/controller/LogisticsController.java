/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.logistics.order.controller;

import javax.validation.Valid;

import com.px.basic.codingrules.service.CodingRulesService;
import com.px.core.query.QueryWrapperGenerator;
import com.px.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.logistics.order.service.dto.LogisticsDTO;
import com.px.logistics.order.service.LogisticsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 三星物流Controller
 *
 * @author 品讯科技
 * @version 2023-04-17
 */
@Api(tags = "三星物流")
@RestController
@RequestMapping(value = "/logistics/order/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private CodingRulesService codingRulesService;

    /**
     * 保存成功列表数据
     */
    @ApiLog("查询保存成功列表数据")
    @ApiOperation(value = "查询保存成功列表数据")
    @PreAuthorize("hasAuthority('logistics:order:logistics:list')")
    @GetMapping("list")
    public ResponseEntity<IPage<LogisticsDTO>> list(LogisticsDTO logisticsDTO, Page<LogisticsDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(logisticsDTO, LogisticsDTO.class);
        if (logisticsDTO.getSendId() != null && !logisticsDTO.getSendId().equals("")) {
            queryWrapper.eq("a.send_id", logisticsDTO.getSendId());
        }
        if (logisticsDTO.getDeliveryId() != null && !logisticsDTO.getDeliveryId().equals("")) {
            queryWrapper.eq("a.delivery_id", logisticsDTO.getDeliveryId());
        }
        if (logisticsDTO.getMaterialOutBegin() != null && !logisticsDTO.getMaterialOutBegin().equals("")) {
            queryWrapper.ge("a.reality_date", logisticsDTO.getMaterialOutBegin());
            queryWrapper.le("a.reality_date", logisticsDTO.getMaterialOutEnd());
        }
        queryWrapper.eq("a.del_flag", "0");
        IPage<LogisticsDTO> page1 = logisticsService.findPage(page, queryWrapper);
        return ResponseEntity.ok(page1);
    }


    @ApiLog("结算管理列表数据")
    @ApiOperation(value = "查询保存成功列表数据")
    @PreAuthorize("hasAuthority('logistics:orderMoney:logistics:list')")
    @GetMapping("Moneylist")
    public ResponseEntity<IPage<LogisticsDTO>> Moneylist(LogisticsDTO logisticsDTO, Page<LogisticsDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(logisticsDTO, LogisticsDTO.class);
        if (logisticsDTO.getId() != null && !logisticsDTO.getId().equals("")) {
            queryWrapper.eq("a.id", logisticsDTO.getId());
        }
        queryWrapper.eq("a.del_flag", "0");
        queryWrapper.isNotNull("a.log_type");
        IPage<LogisticsDTO> page1 = logisticsService.findPage(page, queryWrapper);
        return ResponseEntity.ok(page1);
    }




    /**
     * 根据Id获取保存成功数据
     */
    @ApiLog("根据Id获取保存成功数据")
    @ApiOperation(value = "根据Id获取保存成功数据")
    @PreAuthorize("hasAnyAuthority('logistics:order:logistics:view','logistics:order:logistics:add','logistics:order:logistics:edit')")
    @GetMapping("queryById")
    public ResponseEntity<LogisticsDTO> queryById(String id) {
        return ResponseEntity.ok(logisticsService.findById(id));
    }

    /**
     * 保存保存成功
     */
    @ApiLog("保存成功")
    @ApiOperation(value = "保存成功")
    @PreAuthorize("hasAnyAuthority('logistics:order:logistics:add','logistics:order:logistics:edit')")
    @PostMapping("save")
    public ResponseEntity<String> save(@Valid @RequestBody LogisticsDTO logisticsDTO) throws Exception {
        String contractCode = codingRulesService.getCodingRules("物流订单号").getBody();
        logisticsDTO.setLogNumber(contractCode);
        //新增或编辑表单保存
        logisticsService.saveOrUpdate(logisticsDTO);
        return ResponseEntity.ok("保存成功");
    }



    @ApiLog("更新")
    @ApiOperation(value = "更新")
    @PreAuthorize("hasAnyAuthority('logistics:orderMoney:logistics:add','logistics:orderMoney:logistics:edit')")
    @PostMapping("saveMoney")
    public ResponseEntity<String> saveMoney(@Valid @RequestBody LogisticsDTO logisticsDTO) {
        //新增或编辑表单保存
        logisticsService.saveOrUpdateMoney(logisticsDTO);
        return ResponseEntity.ok("保存成功");
    }



    /**
     * 保存保存成功
     */
    @ApiLog("发货按钮")
    @ApiOperation(value = "发货按钮")
    @PreAuthorize("hasAuthority('logistics:order:logistics:logfa')")
    @GetMapping("logfa")
    public ResponseEntity<String> logfa(String ids) {
        LogisticsDTO byId = logisticsService.findById(ids);
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = df.format(date);
        byId.setSendTime(str);
        byId.setState("1");
        //新增或编辑表单保存
        logisticsService.saveOrUpdate(byId);
        return ResponseEntity.ok("发货成功");
    }


    /**
     * 保存保存成功
     */
    @ApiLog("已完成")
    @ApiOperation(value = "已完成")
    @PreAuthorize("hasAuthority('logistics:order:logistics:logYi')")
    @GetMapping("logYi")
    public ResponseEntity<String> logYi(String ids) {
        LogisticsDTO byId = logisticsService.findById(ids);
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = df.format(date);
        byId.setExpectDate(str);
        byId.setState("2");
        //新增或编辑表单保存
        logisticsService.saveOrUpdate(byId);
        return ResponseEntity.ok("已完成");
    }

    /**
     * 删除保存成功
     */
    @ApiLog("删除保存成功")
    @ApiOperation(value = "删除保存成功")
    @PreAuthorize("hasAuthority('logistics:order:logistics:del')")
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(String ids) {
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            logisticsService.removeById(id);
        }
        return ResponseEntity.ok("删除保存成功成功");
    }


    /**
     * 物流统计图
     */
    @ApiLog("物流统计图")
    @ApiOperation(value = "物流统计图")
    @GetMapping("wuliutu")
    public ResponseEntity<Map<String, Object>> wuliutu() throws Exception {
        return ResponseEntity.ok (logisticsService.getKctjData());
    }

    /**
     * 客服管理统计
     */
    @ApiLog("客服管理统计")
    @ApiOperation(value = "客服管理统计")
    @GetMapping("kefu")
    public ResponseEntity<Map<String, Object>> kefu() throws Exception {
        return ResponseEntity.ok (logisticsService.getCusData());
    }



}
