package com.attiot.railAnaly.point.dao;

import com.attiot.railAnaly.point.entity.APointPleaseTransfer;
import com.attiot.railAnaly.point.param.APointPleaseTransferQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface APointPleaseTransferDao {
    /**
     * 新增
     *
     * @param aPointPleaseTransfer 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    void insert(APointPleaseTransfer aPointPleaseTransfer);

    /***
     * 批量新增
     * @param transferList
     */
    void insertBatch(List<APointPleaseTransfer> transferList);

    /**
     * 修改
     *
     * @param aPointPleaseTransfer 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    void update(APointPleaseTransfer aPointPleaseTransfer);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    List<APointPleaseTransfer> query(APointPleaseTransferQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    APointPleaseTransfer getByParam(APointPleaseTransferQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    long queryCount(APointPleaseTransferQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    APointPleaseTransfer getById(String id);
}
