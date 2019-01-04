package com.ctrip.train.kefu.system.offline.system.service.impl;

import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.EventTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.system.dao.ExtPriorityStaff;
import com.ctrip.train.kefu.system.offline.system.dao.ExtScmSmallEnumExDao;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysChatStaffInfo;
import com.ctrip.train.kefu.system.offline.system.domain.SmallEnumsResult;
import com.ctrip.train.kefu.system.offline.system.domain.StaffPriorityResult;
import com.ctrip.train.kefu.system.offline.system.service.SysPriorityService;
import com.ctrip.train.kefu.system.offline.system.service.SysStaffService;
import com.ctrip.train.kefu.system.offline.system.vm.*;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.OfflinePosition;
import dao.ctrip.ctrainchat.entity.OfflineStaffPriorityNotice;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysPriorityServiceImpl implements SysPriorityService {
    @Autowired
    private ExtPriorityStaff extPriorityStaff;

    @Autowired
    private ExtScmSmallEnumExDao extScmSmallEnumExDao;

    @Autowired
    private ExtSysChatStaffInfo extSysChatStaffInfo;

    @Override
    public PageInfo searchPriorityForPage(VmPriorityRequest request) throws SQLException {
        List<StaffPriorityResult> reports=extPriorityStaff.searchPriority(request);
        PageInfo pg=new PageInfo();
        List<VmPriorityResponse> vmList=new ArrayList<>();
        if (reports!=null&&reports.size()>0){
            ModelMapper modelMapper=new ModelMapper();
            vmList= modelMapper.map(reports,new TypeToken<List<VmPriorityResponse>>() {}.getType());
            Map<Integer,Map<Long,String>> noticeTypes=queryAllNoticeType();
            vmList.forEach(it->{
                if(!StringUtils.isBlank(it.getNoticeTypes())){
                    String [] typesArr=it.getNoticeTypes().split(",");
                    List<String> rs=new ArrayList<>();
                    for (String noticeType:typesArr){
                        if(StringUtils.isNumeric(noticeType)&&it.getNoticeProductLine()!=null&&noticeTypes.containsKey(Integer.valueOf(it.getNoticeProductLine()))){
                            String noticeTypeName=noticeTypes.get(Integer.valueOf(it.getNoticeProductLine())).get(Long.valueOf(noticeType));
                            if(StringUtils.isNotEmpty(noticeTypeName)){
                                rs.add(noticeTypeName);
                            }
                        }
                    }
                    it.setNoticeTypes(StringUtils.join(rs.toArray(), "、"));
                }else {
                    it.setNoticeTypes("空");
                }
            });
        }
        int count=extPriorityStaff.searchPriorityCount(request);
        pg.setRecordsFiltered(count);
        pg.setRecordsTotal(count);
        pg.setData(vmList);
        return pg;
    }

    @Override
    public List<Map<String, Object>> queryPriorityBytId(Long tid) throws SQLException {
        OfflineStaffPriorityNotice op= extPriorityStaff.queryByPk(tid);
        String[] types= op.getNoticeTypes().split(",");
        List<ScmSmallEnum> tempList=searchSmallEnums(op.getNoticeProductLine(),op.getEnvenType());
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (ScmSmallEnum s:tempList){
            Map<String, Object> map = null;
            map = new HashMap<String, Object>();
            map.put("id", s.getTid());
            map.put("pId", 0);
            if(Arrays.asList(types).contains(String.valueOf(s.getTid()))){
                map.put("checked", "true");
            }else {
                map.put("checked", "");
            }
            map.put("name", s.getFieldName());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public JsonResult addPriority(VmPriorityRequest request) throws SQLException {

        if(extSysChatStaffInfo.queryStaffByStaffNum(request.getStaffNum(),1)==null){
            return JsonResult.fail("该员工编号不存在！！");
        }
        //已经存在的通知类型不准添加
        List<OfflineStaffPriorityNotice> priorityList=new ArrayList<>();
        if(!StringUtils.isEmpty(request.getNoticeTypes())) {
            OfflineStaffPriorityNotice ospn=new OfflineStaffPriorityNotice();
            ospn.setStaffNum(request.getStaffNum());
            ospn.setNoticeProductLine(Integer.valueOf(request.getNoticeProductLine()));
            ospn.setNoticeTypes(request.getNoticeTypes());
            ospn.setEnvenType(EventTypeEnum.Notice.getValue());

            List<OfflineStaffPriorityNotice> tempList=extPriorityStaff.searchPrioritys(ospn);
            if(tempList!=null&&tempList.size()>0){
                return JsonResult.fail("该客服已经设置了"+ProductLineEnum.convertByCode(ospn.getNoticeProductLine()).getProductLineName()+"  "
                        +EventTypeEnum.convertEventType(ospn.getEnvenType()).getName()
                        +"的通知类型"
                );
            }

            ospn.setAvailable(1);//可用
            ospn.setCreateTime(DateUtils.getCurFullTimestamp());
            priorityList.add(ospn);
        }
        if(!StringUtils.isEmpty(request.getComplainTypes())) {
            OfflineStaffPriorityNotice ospn=new OfflineStaffPriorityNotice();
            ospn.setStaffNum(request.getStaffNum());
            ospn.setNoticeProductLine(Integer.valueOf(request.getNoticeProductLine()));
            ospn.setAvailable(1);//可用
            ospn.setCreateTime(DateUtils.getCurFullTimestamp());
            ospn.setNoticeTypes(request.getComplainTypes());
            ospn.setEnvenType(EventTypeEnum.Complain.getValue());

            List<OfflineStaffPriorityNotice> tempList=extPriorityStaff.searchPrioritys(ospn);
            if(tempList!=null&&tempList.size()>0){
                return JsonResult.fail("该客服已经设置了"+ProductLineEnum.convertByCode(ospn.getNoticeProductLine()).getProductLineName()+"  "
                        +EventTypeEnum.convertEventType(ospn.getEnvenType()).getName()
                        +"的通知类型");
            }
            priorityList.add(ospn);
        }
        if(!StringUtils.isEmpty(request.getLeaderTypes())) {
            OfflineStaffPriorityNotice ospn=new OfflineStaffPriorityNotice();
            ospn.setStaffNum(request.getStaffNum());
            ospn.setNoticeProductLine(Integer.valueOf(request.getNoticeProductLine()));
            ospn.setAvailable(1);//可用
            ospn.setCreateTime(DateUtils.getCurFullTimestamp());
            ospn.setNoticeTypes(request.getLeaderTypes());
            ospn.setEnvenType(EventTypeEnum.LeaderNotice.getValue());
            List<OfflineStaffPriorityNotice> tempList=extPriorityStaff.searchPrioritys(ospn);
            if(tempList!=null&&tempList.size()>0){
                return JsonResult.fail("该客服已经设置了"+ProductLineEnum.convertByCode(ospn.getNoticeProductLine()).getProductLineName()+"  "
                        +EventTypeEnum.convertEventType(ospn.getEnvenType()).getName()
                        +"的通知类型！"
                );
            }
            priorityList.add(ospn);
        }
        if (priorityList.size()>0){
            extPriorityStaff.insert(priorityList);
            return JsonResult.ok("添加成功！");
        }
        return JsonResult.fail("添加失败！");
    }

    @Override
    public int editPriority(VmPriorityRequest request) throws SQLException {

        OfflineStaffPriorityNotice ospn=extPriorityStaff.queryByPk(request.getId());
        ospn.setNoticeTypes(request.getNoticeTypes());
        return extPriorityStaff.update(ospn);

    }

    @Override
    public JsonResult updatePriorityStatus(long tid) throws SQLException {
        OfflineStaffPriorityNotice ospn=extPriorityStaff.queryByPk(tid);
        if(ospn.getAvailable()==1){
            ospn.setAvailable(0);
        }else {
            ospn.setAvailable(1);
        }
        if(extPriorityStaff.update(ospn)==1){
            return JsonResult.ok("更改成功！");
        }else {
            return JsonResult.fail("更改失败！");
        }
    }

    @Override
    public SmallEnumResponse searchSmallEnumsAll(int productLine) {

        SmallEnumResponse response = new SmallEnumResponse();

        response.setNoticeEnums(getDateMap(searchSmallEnums(productLine,EventTypeEnum.Notice.getValue()),EventTypeEnum.Notice.getValue()));

        response.setComplainEnums(getDateMap(searchSmallEnums(productLine,EventTypeEnum.Complain.getValue()),EventTypeEnum.Complain.getValue()));

        response.setLeaderEnums(getDateMap(searchSmallEnums(productLine,EventTypeEnum.LeaderNotice.getValue()),EventTypeEnum.LeaderNotice.getValue()));

        return response;
    }

    /**
     * 获取所有产品线的一级问题分类(id 与名字)
     * @return
     */
    public Map<Integer,Map<Long,String>> queryAllNoticeType(){
        Map<Integer,Map<Long,String>> rs=new HashMap<>();
        Arrays.stream(ProductLineEnum.values()).forEach(productLineEnum->{
            Map<Long,String> idNameMap=new HashMap<>();
            idNameMap.putAll(searchSmallEnums(productLineEnum.getProductLineCode(),EventTypeEnum.Notice.getValue()).stream()
                        .collect(Collectors.toMap(ScmSmallEnum::getTid,ScmSmallEnum::getFieldName)));
            idNameMap.putAll(searchSmallEnums(productLineEnum.getProductLineCode(),EventTypeEnum.Complain.getValue()).stream()
                    .collect(Collectors.toMap(ScmSmallEnum::getTid,ScmSmallEnum::getFieldName)));
            idNameMap.putAll(searchSmallEnums(productLineEnum.getProductLineCode(),EventTypeEnum.LeaderNotice.getValue()).stream()
                    .collect(Collectors.toMap(ScmSmallEnum::getTid,ScmSmallEnum::getFieldName)));
            rs.put(productLineEnum.getProductLineCode(),idNameMap);
        });
        return rs;
    }

    public List<ScmSmallEnum> searchSmallEnums(int productLine,int eventType)  {
        ScmSmallEnum sample=new ScmSmallEnum();
        sample.setFkUpperTid((long) 0);
        sample.setIsDeleted("0");
        sample.setFieldType(getConfigType(productLine, eventType));
        try {
            return extScmSmallEnumExDao.queryLike(sample);
        } catch (SQLException e) {
            CLogger.error("dao:searchSmallEnums", e);
            return null;
        }
    }

    private String getConfigType(int productLine, int eventType) {
        ProductLineEnum productLineEnum=ProductLineEnum.convertByCode(productLine);
        String configType="";
        if(productLineEnum!=null){
            EventTypeEnum eventTypeEnum=EventTypeEnum.convertEventType(eventType);
            switch (eventTypeEnum){
                case Complain:
                    configType=productLineEnum.getComplainType();
                    break;
                case LeaderNotice:
                    configType=productLineEnum.getLeaderNoticeType();
                    break;
                case Notice:
                    configType=productLineEnum.getNoticeType();
                    break;
                default:
            }
        }
        return configType;
    }

    private List<Map<String, Object>> getDateMap( List<ScmSmallEnum> list, int eventType){
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            String pareteId,pareteName;
            if (eventType==1){
                 pareteId="notice";
                 pareteName="通知";
            }else if(eventType==2){
                 pareteId="complain";
                 pareteName="投诉";
            }else{
                 pareteId="leader";
                 pareteName="领班";
            }
            Map<String, Object> map = null;
            map = new HashMap<String, Object>();
            map.put("id", pareteId);
            map.put("pId", 0);
            map.put("checked", "");
            map.put("name", pareteName);
            mapList.add(map);
            if (list!=null&&list.size()>0){
                for (ScmSmallEnum scmEnum : list) {
                    map = new HashMap<String, Object>();
                    map.put("id", scmEnum.getTid());
                    map.put("pId", pareteId);
                    map.put("checked", "");
                    map.put("name", scmEnum.getFieldName());
                    mapList.add(map);
                }
            }
        return mapList;
    }
}
