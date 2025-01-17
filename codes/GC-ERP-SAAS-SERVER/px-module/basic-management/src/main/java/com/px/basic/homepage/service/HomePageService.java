/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.homepage.service;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.basic.homepage.domain.HomePage;
import com.px.basic.homepage.mapper.HomePageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.*;

/**
 * 首页数据Service
 * @author 品讯科技
 * @version 2022-06-20
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class HomePageService extends ServiceImpl<HomePageMapper, HomePage> {

    /**
     * 首页头部四块
     */
    public Map<String, Object> getTopFourData() {
        Map<String, Object> tfd_map = new HashMap<>();

        //本年销售总额
        double[] bnxsze = {0.0, 0.0, 0.0, 0.0, 0.0};
        ArrayList<Map<String, String>> bnxsze_list = baseMapper.getTopFourData_bnxsze();
        this.amountCalculation_tb_hb(bnxsze_list, bnxsze);
        tfd_map.put("bnxsze", bnxsze);

        //本年采购总额
        double[] bncgze = {0.0, 0.0, 0.0, 0.0, 0.0};
        ArrayList<Map<String, String>> bncgze_list = baseMapper.getTopFourData_bncgze();
        this.amountCalculation_tb_hb(bncgze_list, bncgze);
        tfd_map.put("bncgze", bncgze);

        //库存总价值
        double[] kczjz = {0.0, 0.0};
        String kczjz_g = baseMapper.getTopFourData_kczjz();
        kczjz_g = kczjz_g == null ? "0" : kczjz_g;
        kczjz[0] = NumberUtil.round(NumberUtil.div(kczjz_g, "10000"), 2).doubleValue();
        //本月入库价值
        String byrkjz_g = baseMapper.getTopFourData_byrkjz();
        byrkjz_g = byrkjz_g == null ? "0" : byrkjz_g;
        kczjz[1] =  NumberUtil.round(NumberUtil.div(byrkjz_g, "10000"), 2).doubleValue();
        tfd_map.put("kczjz", kczjz);

        return tfd_map;
    }

    //月同比+月环比金额计算
    public void amountCalculation_tb_hb (ArrayList<Map<String, String>> bnxsze_list, double[] processArray) {
        if (bnxsze_list != null && bnxsze_list.size() > 0) {
            HashMap<String, String> key_map = new HashMap<>();
            //list转map
            for (Map<String, String> map : bnxsze_list) {
                key_map.put(map.get("hjlb"), map.get("hj"));
            }
            processArray[0] = NumberUtil.round(NumberUtil.div(key_map.get("bnze"), "10000"), 2).doubleValue();
            processArray[3] = NumberUtil.round(NumberUtil.div(key_map.get("byze"), "10000"), 2).doubleValue();

            //同比
            if (!key_map.get("qnbyze").equals("0")) {
                BigDecimal xj = NumberUtil.sub(key_map.get("byze"), key_map.get("qnbyze"));
                BigDecimal xcc = NumberUtil.mul(NumberUtil.div(xj.toString(), key_map.get("qnbyze"),4), 100);
                processArray[1] = NumberUtil.round(xcc, 2).doubleValue();
            } else {
                processArray[1] = 100;
            }
            //环比
            if (!key_map.get("syze").equals("0")) {
                BigDecimal xj = NumberUtil.sub(key_map.get("byze"), key_map.get("syze"));
                BigDecimal xcc = NumberUtil.mul(NumberUtil.div(xj.toString(), key_map.get("syze"),4), 100);
                processArray[2] = NumberUtil.round(xcc, 2).doubleValue();
            } else {
                processArray[2] = 100;
            }
            //本年月均销售额 万
            if (!key_map.get("bnys").equals("0")) {
                BigDecimal pj_xc = NumberUtil.div(NumberUtil.div(key_map.get("bnze"), key_map.get("bnys")).toString(), "10000");
                processArray[4] = NumberUtil.round(pj_xc, 2).doubleValue();
            }
        }
    }

    /**
     * 销售额
     */
    public Map<String, Object> getXseData() {
        Map<String, Object> xd_map = new HashMap<>();
        //本年月销售趋势图
        ArrayList<Map<String, String>> xse_list = new ArrayList<>();

        String[] months_array = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayList<Map<String, String>> get_xse_list = baseMapper.getBottomData_bnyxs();

        if (get_xse_list != null && get_xse_list.size() > 0) {
            //key map
            HashMap<String, String> key_map = new HashMap<>();
            for (Map<String, String> map : get_xse_list) {
                key_map.put(map.get("yf"), map.get("yhj"));
            }

            for (int i = 0; i < months_array.length; i++) {
                HashMap<String, String> m_map = new HashMap<>();
                m_map.put("日期", NumberUtil.roundStr(months_array[i], 0).toString() + "月");
                if (key_map.get(months_array[i]) != null) {
                    m_map.put("销售额", NumberUtil.round(NumberUtil.div(key_map.get(months_array[i]), "10000"), 2).toString());
                } else {
                    m_map.put("销售额", "0");
                }
                xse_list.add(m_map);
            }
        }
        xd_map.put("xse", xse_list);

        //客户销售排行榜
        xd_map.put("xsphb", baseMapper.getBottomData_xsphb());

        return xd_map;
    }

    /**
     * 采购额
     */
    public Map<String, Object> getCgeData() {
        Map<String, Object> cd_map = new HashMap<>();
        //本年月采购趋势图
        ArrayList<Map<String, String>> cge_list = new ArrayList<>();

        String[] months_array = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayList<Map<String, String>> get_cge_list = baseMapper.getBottomData_bnycg();

        if (get_cge_list != null && get_cge_list.size() > 0) {
            //key map
            HashMap<String, String> key_map = new HashMap<>();
            for (Map<String, String> map : get_cge_list) {
                key_map.put(map.get("yf"), map.get("yhj"));
            }

            for (int i = 0; i < months_array.length; i++) {
                HashMap<String, String> m_map = new HashMap<>();
                m_map.put("日期", NumberUtil.roundStr(months_array[i], 0).toString() + "月");
                if (key_map.get(months_array[i]) != null) {
                    m_map.put("采购额", NumberUtil.round(NumberUtil.div(key_map.get(months_array[i]), "10000"), 2).toString());
                } else {
                    m_map.put("采购额", "0");
                }
                cge_list.add(m_map);
            }
        } else {
            for (int i = 0; i < months_array.length; i++) {
                HashMap<String, String> m_map = new HashMap<>();
                m_map.put("日期", NumberUtil.roundStr(months_array[i], 0).toString() + "月");
                m_map.put("采购额", "0");
                cge_list.add(m_map);
            }
        }
        cd_map.put("cge", cge_list);

        //供应商采购排行榜
        cd_map.put("cgphb", baseMapper.getBottomData_cgphb());

        return cd_map;
    }

    /**
     * 库存统计
     */
    public Map<String, Object> getKctjData() {
        Map<String, Object> cd_map = new HashMap<>();
        //仓库产品库存价值分析
        String[] jzfx = {"0", "0"};
        ArrayList<Map<String, String>> jzfx_list = baseMapper.getBottomData_kcjzfx();
        if (jzfx_list != null && jzfx_list.size() > 0) {
            for (Map<String, String> map : jzfx_list) {
                if (("cp").equals(map.get("chlb"))) {
                    jzfx[0] = map.get("chjz");
                } else if (("wl").equals(map.get("chlb"))) {
                    jzfx[1] = map.get("chjz");
                }
            }
        }
        cd_map.put("kcjzfx", jzfx);

        //仓库产品存货排名TOP10
        cd_map.put("chjzph", baseMapper.getBottomData_chjzph());
        //存货预警
        cd_map.put("chyj", baseMapper.getBottomData_chyj());
        return cd_map;
    }

    /**
     * 订单执行进度
     */
    public Map<String, Object> getDdzxjdData(String ddzt) {
        Map<String, Object> cd_map = new HashMap<>();
        //订单执行进度情况分析
        cd_map.put("ddzxjd", baseMapper.getBottomData_ddzxjd(ddzt));
        return cd_map;
    }

}
