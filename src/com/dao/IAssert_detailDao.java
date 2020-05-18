package com.dao;

import com.pojo.Assert_detail;

import java.util.List;

public interface IAssert_detailDao {
    /** 查询资产明细表
     * @Author Luke
     * @param ad_code 资产编号
     * @return 返回资产明细表对象
     */
    public Assert_detail findAssetsByAdCode(String ad_code);


    /**
     * 以id为关键查询数据库
     * @author Luke
     * @param ad_id 资产明细id
     * @return
     */
    public Assert_detail findAssetsDetailById(Integer ad_id);

    /**
     * @Author Luke
     * 修改相应id的资产明细表记录
     * @param ad_code 资产编号
     * @param assert_detail 资产明细对象
     * @return true说明修改成功，false 说明修改失败
     */
    public boolean updateAssertsByAdCode(String ad_code, Assert_detail assert_detail);

    /**
     * 插入记录
     * @author Luke
     * @param assert_detail 资产明细对象
     * @return true说明插入成功，false 说明插入失败
     */
    public boolean insertAssets(Assert_detail assert_detail);

    /**
     * 根据凭证id去更新对应的凭证号，资产凭证表一致
     * @author Luke
     * @param ad_avid   凭证id
     * @param vou 凭证号
     * @return
     */

    public boolean updateAssertsVouByAdAvid(Integer ad_avid, String vou);

    /**
     * 根据采购单号去更新对应的凭证号id
     * @author Luke
     * @param ad_purcode    采购单号
     * @param assert_detail 资产明细表对象
     * @return
     */

    public boolean updateAssertsByPurCode(String ad_purcode, Assert_detail assert_detail);

    /**
     * 依据 采购单号更新凭证id
     * @author Luke
     * @param ad_purcode    采购单号
     * @param av_id     凭证id
     * @return
     */
    public boolean updateAssertsVouAdavidByPurcode(String ad_purcode, Integer av_id);



}
