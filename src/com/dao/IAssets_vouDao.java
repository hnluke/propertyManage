package com.dao;

import com.pojo.Assets_vou;

public interface IAssets_vouDao {
    /**
     * 依据凭证号来查找资产
     * @author Luke
     * @param av_no 凭证号
     * @return 返回资产凭证对象
     */
    public Assets_vou findAssetsVouByAvno(String av_no);

    /**
     * 依据资产凭证表id查询资产凭证表
     * @author Luke
     * @param av_id 凭证id
     * @return
     */
    public Assets_vou findAssetsVouById(Integer av_id);

    /**
     * 向资产凭证表插入数据
     * @author Luke
     * @param assets_vou    资产凭证对象
     * @return 插入成功返回true, 否则返回false
     */
    public boolean insertAssetsVou(Assets_vou assets_vou);

    /**
     * 更新资产凭证表
     * @author Luke
     * @param av_no 资产凭证号
     * @param assets_vou  资产凭证对象
     * @return 更新成功返回true, 否则返回false
     */
    public boolean updateAssetsVouByAvno(String av_no, Assets_vou assets_vou);
}
