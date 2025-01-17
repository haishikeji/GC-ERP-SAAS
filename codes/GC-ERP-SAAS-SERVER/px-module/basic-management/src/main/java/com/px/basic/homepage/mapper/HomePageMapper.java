/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.homepage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.basic.homepage.domain.HomePage;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

/**
 * 首页数据MAPPER接口
 * @author 品讯科技
 * @version 2022-06-20
 */
public interface HomePageMapper extends BaseMapper<HomePage> {

    /**
     * 根据出过来的编码名称生成编码
     * @param name
     * @return
     */
    ResponseEntity<String> getCodingRules(String name);

    /**
     * 首页头部四块--本年销售总额
     */
    ArrayList<Map<String, String>> getTopFourData_bnxsze();

    /**
     * 首页头部四块--本年采购总额
     */
    ArrayList<Map<String, String>> getTopFourData_bncgze();

    /**
     * 首页头部四块--库存总价值
     */
    String getTopFourData_kczjz();

    /**
     * 首页头部四块--本月入库价值
     */
    String getTopFourData_byrkjz();

    /**
     * 本年月销售
     */
    ArrayList<Map<String, String>> getBottomData_bnyxs();
    ArrayList<Map<String, String>> getBottomData_xsphb();

    /**
     * 本年月采购
     */
    ArrayList<Map<String, String>> getBottomData_bnycg();
    ArrayList<Map<String, String>> getBottomData_cgphb();

    /**
     * 库存统计
     */
    ArrayList<Map<String, String>> getBottomData_kcjzfx();
    ArrayList<Map<String, String>> getBottomData_chjzph();
    ArrayList<Map<String, String>> getBottomData_chyj();

    /**
     * 订单执行进度
     */
    ArrayList<Map<String, String>> getBottomData_ddzxjd(@Param(value="ddzt") String ddzt);
}
