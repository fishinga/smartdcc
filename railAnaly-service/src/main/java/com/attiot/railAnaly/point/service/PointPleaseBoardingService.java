package com.attiot.railAnaly.point.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.point.dao.APointPleaseBoardingDao;
import com.attiot.railAnaly.point.entity.APointPleaseBoarding;
import com.attiot.railAnaly.point.param.APointPleaseBoardingQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class PointPleaseBoardingService {
    @Autowired
    private APointPleaseBoardingDao aPointPleaseBoardingDao;

    /**
     * 新增
     *
     * @param aPointPleaseBoarding 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    public void create(APointPleaseBoarding aPointPleaseBoarding){
        aPointPleaseBoardingDao.insert(aPointPleaseBoarding);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    public void delete(String id){
        aPointPleaseBoardingDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public APointPleaseBoarding getById(String id){
        return  aPointPleaseBoardingDao.getById(id);
    }

    /***
     * 根据请点ID查询
     * @param pointId
     * @return
     */
    public APointPleaseBoarding getByPointId(String pointId){
        return  aPointPleaseBoardingDao.getByPointId(pointId);
    }


    /**
     * 修改
     *
     * @param aPointPleaseBoarding 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    public void update(APointPleaseBoarding aPointPleaseBoarding){
        aPointPleaseBoardingDao.update(aPointPleaseBoarding);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    public Page<APointPleaseBoarding> query(APointPleaseBoardingQueryParam param){
        Page<APointPleaseBoarding> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPointPleaseBoardingDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPointPleaseBoardingDao.query(param));
        }
        return page;
    }
}
