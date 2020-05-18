package com.dao;

import com.pojo.Asserts_type;

public interface IAsserts_typeDao {
    /** @Author
     * 插入资产类别
     * @param asserts_type 资产类型
     * @return 插入成功返回true,否则返回false
     */
    public boolean insertAssetsType(Asserts_type asserts_type);

    public Asserts_type findAssetstypeByTypename(String asty_name);
}
