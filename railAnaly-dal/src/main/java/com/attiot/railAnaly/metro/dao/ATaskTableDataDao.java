/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;


import com.attiot.railAnaly.metro.entity.ATaskTableData;
import com.attiot.railAnaly.metro.param.ATaskTableDataQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author attiot
 * 2018-05-18 13:08:37
 */
@Repository
public interface ATaskTableDataDao {
    /**
     * 新增
     *
     * @param aTaskTableData 参数
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    void insert(ATaskTableData aTaskTableData);

    /**
     * 修改
     *
     * @param aTaskTableData 参数
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    void update(ATaskTableData aTaskTableData);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    List<ATaskTableData> query(ATaskTableDataQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    ATaskTableData getByParam(ATaskTableDataQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    long queryCount(ATaskTableDataQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-18 13:08:37
     */
    ATaskTableData getById(String id);

    public List<ATaskTableData>  getTableDataByPPointId(String ppointId);

     public void deleteByPpointIdAndTableId(Map params);

     public void batchInsert(List list);
}
