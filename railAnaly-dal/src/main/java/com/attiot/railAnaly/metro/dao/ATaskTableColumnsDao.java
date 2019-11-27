/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;


import com.attiot.railAnaly.metro.entity.ATaskTableColumns;
import com.attiot.railAnaly.metro.param.ATaskTableColumnsQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 *
 * @author attiot
 * 2018-05-26 09:12:40
 */
@Repository
public interface ATaskTableColumnsDao {
    /**
     * 新增
     *
     * @param aTaskTableColumns 参数
     *
     * @author attiot
     * 2018-05-26 09:12:40
     */
    void insert(ATaskTableColumns aTaskTableColumns);

    /**
     * 修改
     *
     * @param aTaskTableColumns 参数
     *
     * @author attiot
     * 2018-05-26 09:12:40
     */
    void update(ATaskTableColumns aTaskTableColumns);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-26 09:12:40
     */
    void delete(String id);

    public void deleteByTaskTableId(String taskTableId);




    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-26 09:12:40
     */
    ATaskTableColumns getByParam(ATaskTableColumnsQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-26 09:12:40
     */
    long queryCount(ATaskTableColumnsQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-26 09:12:40
     */
    ATaskTableColumns getById(String id);

    public List<ATaskTableColumns> getColumnsByTaskTableId(ATaskTableColumnsQueryParam param);

    public void batchInsert(List<ATaskTableColumns> list);

    public List<ATaskTableColumns> getColumnByTableCode(String tablecode);

    public List<ATaskTableColumns> getTableColumnsByTableCode(String tablecode);
}
