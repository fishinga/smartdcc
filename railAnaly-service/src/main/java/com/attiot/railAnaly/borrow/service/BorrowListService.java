package com.attiot.railAnaly.borrow.service;

import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.borrow.dao.ABorrowListDao;
import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.borrow.entity.ABorrowList;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.param.ABorrowListQueryParam;
import com.attiot.railAnaly.category.entity.ABorrowCategory;
import com.attiot.railAnaly.category.param.ABorrowCategoryQueryParam;
import com.attiot.railAnaly.category.service.BorrowCategoryService;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.common.HttpClientUtilsToServer;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.dao.ABorrowGoodsDao;
import com.attiot.railAnaly.goods.dao.PointListGoodsDao;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.service.BorrowGoodsService;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.attiot.railAnaly.metro.param.BorrowCategoryQueryParam;
import com.attiot.railAnaly.point.dao.APointPleaseDao;
import com.attiot.railAnaly.point.entity.APointPlease;
import com.attiot.railAnaly.point.service.PointPleaseService;
import com.attiot.railAnaly.util.Constant;
import com.attiot.railAnaly.workflow.dao.AWorkflowDataDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SSS on 2018/4/10.
 */
@Service
public class BorrowListService {
    @Autowired
    private ABorrowListDao aBorrowListDao;

    @Autowired
    private ABorrowListDetailDao aBorrowListDetailDao;
    @Autowired
    private ABorrowGoodsDao aBorrowGoodsDao;
    @Autowired
    private AWorkflowDataDao aWorkflowDataDao;
    @Autowired
    private APointPleaseDao aPointPleaseDao;
    @Autowired
    private TSBaseUserDao tSBaseUserDao;
    @Autowired
    private BorrowGoodsService goodsService;
    @Autowired
    private BorrowListDetailService  borrowDetailService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private TSBaseUserService userService;
    @Autowired
    private PointPleaseService pointService;
    @Autowired
    private BorrowCategoryService categoryService;
    @Autowired
    private JPushService jpushService;
    @Value("${server_path}")
    private String serverPath;
    
    @Autowired
	private PointListGoodsDao pointListGoodsDao;
    /**
     * 新增
     *
     * @param aBorrowList 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    public String create(ABorrowList aBorrowList){
        aBorrowListDao.insert(aBorrowList);
        return aBorrowList.getId();
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    public void delete(String id){
        aBorrowListDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public ABorrowList getById(String id){
        return  aBorrowListDao.getById(id);
    }

    /***
     * 根据请点ID查询
     * @param pointId
     * @return
     */
    public List<ABorrowList> getByPointId(String pointId){
        return  aBorrowListDao.getByPointId(pointId);
    }

    public List<ABorrowListDetail> getBorrowDetailByPointId(String ppointId) {
        return aBorrowListDetailDao.getBorrowByPointId(ppointId);
    }

    /**
     * 修改
     *
     * @param aBorrowList 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    public void update(ABorrowList aBorrowList){
        aBorrowListDao.update(aBorrowList);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    public Page<ABorrowList> query(ABorrowListQueryParam param){
        Page<ABorrowList> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aBorrowListDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aBorrowListDao.query(param));
        }
        return page;
    }




    /***
     * 保存相关的借用信息
     * @param ids
     * @param borrower
     * @param orgId
     * @param pointId
     * @param jobContent
     * @param remark
     */
    @Transactional
    public void saveWithTransaction(String[] ids,String borrower,String orgId,String pointId,String jobContent,String remark){

        save( ids, borrower, orgId, pointId, jobContent, remark);
        jpushService.sendMessage(ConstantValue.PUSH_MSG_GUIHUANG,borrower,"借用物品成功提交审批.");
    }

    public void save(String[] ids,String borrower,String orgId,String pointId,String jobContent,String remark){

        if(null == ids || ids.length<=0) return;
        Map borrowMap = new HashMap();
        List<ABorrowGoods> goodsList = aBorrowGoodsDao.getByIds(ids);
        ABorrowList borrowList = new ABorrowList();
        borrowList.setBorrower(borrower);
        borrowList.setBorrowNum(goodsList.size());
        borrowList.setBorrowState(3);//借用申请
        borrowList.setBorrowTime(new Date());
        borrowList.setCreator(borrower);
        borrowList.setCreatetime(new Date());
        borrowList.setCreatorOrg(orgId);
        borrowList.setRemark(remark);
        borrowList.setJobContent(jobContent);
        borrowList.setPpointId(pointId);
        borrowList.setType(0);
        String borrowId = this.create(borrowList);
        TSBaseUser u = tSBaseUserDao.getById(borrower);
        borrowList.setBorrowerName(u.getRealname());
        APointPlease point = aPointPleaseDao.getById(pointId);
        if(null != point) {
            borrowList.setPointType(point.getPointType());

        }else {
            borrowList.setPointType("0");//表示外来人员
        }
        borrowMap.put("borrow",borrowList);

        List lst = new ArrayList();
        for(int k=0;k<goodsList.size();k++){
            ABorrowGoods goods = goodsList.get(k);
            ABorrowListDetail borrowListDetail = new ABorrowListDetail();
            borrowListDetail.setBorrowListId(borrowId);
            borrowListDetail.setBorrowGoodsCategory(goods.getCategory());
            ABorrowCategoryQueryParam cq = new ABorrowCategoryQueryParam();
            cq.setId(goods.getCategory());
            Page<ABorrowCategory> page = categoryService.query(cq);
            if(page.getResults().size()>0){
                ABorrowCategory category = page.getResults().get(0);
                borrowListDetail.setCategoryName(category.getName());
            }
            borrowListDetail.setBorrowGoodsId(goods.getId());
            borrowListDetail.setBorrowGoodsName(goods.getName());
            borrowListDetail.setCode(goods.getCode());
            borrowListDetail.setUnit(goods.getUnit());
            borrowListDetail.setReturnType("0");
            aBorrowListDetailDao.insert(borrowListDetail);
            goods.setState(2);//占用物品
            aBorrowGoodsDao.editState(goods);
            lst.add(borrowListDetail);
        }
        borrowMap.put("borrowDetail",lst);
        //审批流程开始
        List<AWorkflowData> list = workflowDataService.needSaveNode(Constant.PROCESS_CODE_2010,borrowId,borrower,new Gson().toJson(borrowMap));
        aWorkflowDataDao.batchInsert(list);
        //消息提醒
        if(list.size()>0) {
            for(AWorkflowData runWorkflow:list) {
                if(runWorkflow.getRunState()==1) {
                    workflowDataService.pushMsgToAppAndWeb(runWorkflow);
                }
            }
        }
    }

    /***
     * 查询我的借用（借用历史）
     * @param param
     * @return
     */
    public List getBorrowHis(HashMap<String,Object> param){
        return aBorrowListDao.getBorrowHis(param);
    }

    /***
     * 需要归还的借用物品
     * @param param
     * @return
     */
    public List getBorrowReturn(HashMap<String,Object> param){
        return borrowDetailService.getBorrowReturn(param);
    }


    /***
     * 保存归还的物品信息
     * @param goodId
     * @param accessToken
     */
    @Transactional
    public void saveGiveBack(String[] goodId,String accessToken,String type,String userId,String pointId){
        List<ABorrowListDetail> detailList = goodId!=null && goodId.length>0?aBorrowListDetailDao.getByGoodId(goodId):new ArrayList();
        if(null!=detailList && detailList.size()>0){
        String borrowId = "";
        if("1".equals(type)||"2".equals(type)){//转借或交接给个人
            List<ABorrowGoods> goodsList = goodsService.getByIds(goodId);
            ABorrowList borrowList = new ABorrowList();
            borrowList.setBorrower(userId);
            borrowList.setBorrowNum(goodsList.size());
            borrowList.setBorrowState(4);
            borrowList.setBorrowTime(new Date());
            borrowList.setCreator(userId);
            borrowList.setCreatetime(new Date());
            borrowList.setPpointId(pointId);
            borrowList.setType(0);
            TSBaseUser u = tSBaseUserDao.getById(accessToken);

            borrowList.setCreatorOrg(u.getDepartid());
            borrowList.setRemark("1".equals(type)?"转借单":"交接单");
            borrowList.setJobContent("1".equals(type)?"转借单":"交接单");
            borrowId = this.create(borrowList);
            String goodsStr = "";
            StringBuilder goodsStrB = new StringBuilder(goodsStr);
            for(ABorrowGoods bg:goodsList) {
                if(goodsStrB.length()>0) {
                    goodsStrB.append(",");
                }
                goodsStrB.append(bg.getName()).append("[").append(bg.getCode()).append("]");
            }
            goodsStr = "1".equals(type)?"新的转借单":"新的交接单"+goodsStrB.toString();
            jpushService.sendMessage(ConstantValue.PUSH_MSG_GUIHUANG,userId,goodsStr);
            HttpClientUtilsToServer.getInstance().doGet(serverPath,userId,ConstantValue.PUSH_MSG_DAIBANG, goodsStr);
        }
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime=new Date();
        SimpleDateFormat time2=new SimpleDateFormat("yyMMddHHmmss");
        String no = Constant.PROCESS_CODE_2011+time2.format(nowTime);
        Map borrowMap = new HashMap();
        List _newLst = new ArrayList();
        for(int i=0;i<detailList.size();i++){
        	String creator = "";
        	
            ABorrowListDetail detail = detailList.get(i);
//            detail.setReturnNum(no);//不用去走流程所以returnNum值不要去变化
            detail.setReturnTime(time.format(nowTime));
            detail.setRestituer(accessToken);
            if("0".equals(type)){//归还
                detail.setReturnType("2");
                detail.setReturnNum(no);//归还要走流程所以returnNum需要有新值
            }else if("1".equals(type)||"2".equals(type)){//转借或交接给个人
            	creator = userId;
                detail.setReturnType("1".equals(type)?"4":"5");//状态为转借或交接
                ABorrowListDetail borrowListDetail = new ABorrowListDetail();
                borrowListDetail.setBorrowListId(borrowId);
                borrowListDetail.setBorrowGoodsCategory(detail.getBorrowGoodsCategory());
                borrowListDetail.setCategoryName(detail.getCategoryName());
                borrowListDetail.setBorrowGoodsId(detail.getBorrowGoodsId());
                borrowListDetail.setBorrowGoodsName(detail.getBorrowGoodsName());
                borrowListDetail.setCode(detail.getCode());
                borrowListDetail.setUnit(detail.getUnit());
                borrowListDetail.setReturnType("1");
                borrowListDetail.setAttachId(detail.getId());
                aBorrowListDetailDao.insert(borrowListDetail);
                //修改原有借用单据
                ABorrowList borrowList = aBorrowListDao.getById(detail.getBorrowListId());
                if(null == borrowList.getReturnNum()){
                    borrowList.setReturnNum(0);
                }
                borrowList.setReturnNum(borrowList.getReturnNum() + 1);
                if(borrowList.getBorrowNum() == borrowList.getReturnNum()){
                    borrowList.setBorrowState(6);//已还状态
                }else{
                    borrowList.setBorrowState(4);//待还状态
                }
                
                borrowList.setModifytime(new Date());
                borrowList.setModifor(accessToken);
                aBorrowListDao.update(borrowList);
                if(borrowList.getBorrowState()==6) {//已还，更新数据
                    jpushService.sendMessage(ConstantValue.PUSH_MSG_GUIHUANG,accessToken,detail.getBorrowGoodsName()+"["+detail.getCode()+"]"+"已交接给调度.");
                }
            }else if("3".equals(type)){//交接给调度
            	
            	/**---------------获取所有的调度start--------------------------*/
            	List<TSBaseUser> users = tSBaseUserDao.getTSBaseUserListByRoleCode("1030");
            	StringBuilder tempString = new StringBuilder(creator);
            	for(TSBaseUser u:users) {
                    tempString.append(u.getId()).append(",");
            	}
            	creator = tempString.toString();
            	if(creator.length()>1) {
            		creator=creator.substring(0,creator.length()-1);
    			}
            	/**---------------获取所有的调度end--------------------------*/
            	
                detail.setReturnType("6");//状态改为待转派
                detail.setRestituer(creator);
                //修改原有借用单据
                ABorrowList borrowList = aBorrowListDao.getById(detail.getBorrowListId());
                if(null == borrowList.getReturnNum()){
                    borrowList.setReturnNum(0);
                }
                borrowList.setReturnNum(borrowList.getReturnNum() + 1);
                if(borrowList.getBorrowNum() == borrowList.getReturnNum()){
                    borrowList.setBorrowState(6);//已还状态
                }else{
                    borrowList.setBorrowState(4);//待还状态
                }
               
                borrowList.setModifytime(new Date());
                borrowList.setModifor(accessToken);
                aBorrowListDao.update(borrowList);
                if(borrowList.getBorrowState()==6) {//已还，更新数据
                    jpushService.sendMessage(ConstantValue.PUSH_MSG_GUIHUANG,accessToken,detail.getBorrowGoodsName()+"["+detail.getCode()+"]"+"已交接给调度.");
                }
            }
            
            /**-----------将挂牌负责人改为被交接人start------------*/
            String goodsId = detail.getBorrowGoodsId();
			List<PointListGoods> pointListGoodsList = pointListGoodsDao.getByGoodsIds(goodsId);
			if(pointListGoodsList.size() > 0 ){
				PointListGoods goods = pointListGoodsList.get(0);
				goods.setCreator(creator);
				pointListGoodsDao.update(goods);
			}
			/**-----------将挂牌负责人改为被交接人end------------*/
            aBorrowListDetailDao.update(detail);
            _newLst.add(detail);
        }
        if("0".equals(type)){//归还需走审批流
            borrowMap.put("borrowDetail",_newLst);
            //审批流程开始
            List<AWorkflowData> list = workflowDataService.needSaveNode(Constant.PROCESS_CODE_2011,no,accessToken,new Gson().toJson(borrowMap));
            aWorkflowDataDao.batchInsert(list);
            //消息提醒
            if(list.size()>0) {
                for(AWorkflowData runWorkflow:list) {
                    if(runWorkflow.getRunState()==1) {
                        workflowDataService.pushMsgToAppAndWeb(runWorkflow);
                    }
                }
            }
        }
        }
    }
}
