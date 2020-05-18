package com.dao;

import com.pojo.Assets;

public interface IAssetsDao {
    /**
     * 依据资产名称查找资产表
     * @author Luke
     * @param ass_name 资产名称
     * @return 资产对象
     */
    public Assets findAssetsByAss_name(String ass_name);

    /**
     * 依据资产表id查询数据资产表
     * @author Luke
     * @param ss_id 资产表id
     * @return
     */
    public Assets findAssetsById(Integer ss_id);

    /**
     * 插入资产信息
     * @author Luke
     * @param assets  资产对象
     * @return 插入成功返回true, 否则返回false
     */
    public boolean insertAsserts(Assets assets);

    /**
     * 更新资产信息
     * @author Luke
     * @param ass_name 资产名称
     * @param assets 资产对象
     * @return 更新成功返回true, 否则返回false
     */
    public boolean updateAssertsByAssName(String ass_name, Assets assets);

    /**
     * 资产异动
     * @author Luke
     * @param assets 要异动的资产表对象
     * @param price 单价
     * @param tag 标志， "领用", "归还", "报废", "返修", "销帐"
     * @return
     */
    public boolean updateAssetsStoreById(Assets assets, double price, String tag);

}
