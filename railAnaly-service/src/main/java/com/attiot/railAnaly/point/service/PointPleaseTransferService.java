package com.attiot.railAnaly.point.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.point.dao.APointPleaseTransferDao;
import com.attiot.railAnaly.point.entity.APointPleaseTransfer;
import com.attiot.railAnaly.point.param.APointPleaseTransferQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class PointPleaseTransferService {
    @Autowired
    private APointPleaseTransferDao aPointPleaseTransferDao;

    /**
     * 新增
     *
     * @param aPointPleaseTransfer 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    public void create(APointPleaseTransfer aPointPleaseTransfer){
        aPointPleaseTransferDao.insert(aPointPleaseTransfer);
    }

    /***
     * 批量新增
     * @param transferList
     */
    public void insertBatch(List<APointPleaseTransfer> transferList){
        aPointPleaseTransferDao.insertBatch(transferList);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    public void delete(String id){
        aPointPleaseTransferDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public APointPleaseTransfer getById(String id){
        return  aPointPleaseTransferDao.getById(id);
    }


    /**
     * 修改
     *
     * @param aPointPleaseTransfer 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    public void update(APointPleaseTransfer aPointPleaseTransfer){
        aPointPleaseTransferDao.update(aPointPleaseTransfer);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-06-14 10:58:12
     */
    public Page<APointPleaseTransfer> query(APointPleaseTransferQueryParam param){
        Page<APointPleaseTransfer> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPointPleaseTransferDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPointPleaseTransferDao.query(param));
        }
        return page;
    }

}
