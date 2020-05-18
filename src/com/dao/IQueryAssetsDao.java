package com.dao;

import com.pojo.AllAssetsItem;
import com.pojo.Purchase;

import java.util.List;

public interface IQueryAssetsDao {
    /**
     *  查询资产
     * @author Luke
     * @param field 字段名称
     * @param value  值
     * @param flag 暂时保留，一般传""
     * @param index 第几页
     * @return
     */
    public List<AllAssetsItem> findAssetsBy(String field, String value, String flag, int index);

    /**
     * 统计数据表的记录数
     * @param field 字段名称
     * @param value 值
     * @param flag 暂时保留，一般传""
     * @param index 第几页
     * @return
     */
    public int queryAssetsCount(String field, String value, String flag, int index);

    /**
     * 导出数据到Excel表
     * @param list 资产列表集体List
     * @param path 文件保存路径
     * @return
     */
    public boolean exportToExcel(List<AllAssetsItem> list, String path);




}
