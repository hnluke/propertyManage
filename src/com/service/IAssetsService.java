package com.service;

import com.pojo.AllAssetsItem;

import java.util.List;

public interface IAssetsService {
    /**
     * 采购入库
     * @param purchaseId 采购数据表id
     * @return 成功入库返回true, 否则返回false
     */
    public boolean inStore(Integer purchaseId);

    /**
     * 查询资产表
     * @param from 字段名
     * @param to 值
     * @param flag 暂时保留
     * @param index 第几页
     * @return
     */
    public List<AllAssetsItem> findAssets(String from, String to, String flag, int index);

    /**
     * 财务确认业务，向资产明细表中填入凭证号，跟资产凭证表一致
     * @param av_vou 凭证号
     * @return
     */
    public String financeConfirm(String av_vou);

    /**
     * 资产领用
     * @param ad_id 资产明细id
     * @param ad_uid 用户表id
     * @param tag 异动操作
     * @return
     */
    public boolean assertsUse(Integer ad_id, Integer ad_uid, String tag);

    /**
     * 查询资产表
     * @param field 字段名
     * @param val 值
     * @param flag 暂时保留
     * @param index 第几页
     * @return
     */
    public int getAssetsCount(String field, String val, String flag, int index);

    /**
     * 将资产数据导出到Excel表中
     * @param list  资产数据列表
     * @param filePath 文件路径
     * @return
     */
    public String exportAssetsToExcel(List<AllAssetsItem> list, String filePath);
}
