package com.ctrip.train.kefu.system.api.service.vendor.user;

import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.KeyHolder;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.train.kefu.system.api.contract.*;
import com.ctrip.train.kefu.system.api.dao.user.ExtUserBasicInfo;
import com.ctrip.train.kefu.system.api.dao.user.ExtUserVendorInfo;
import com.ctrip.train.kefu.system.api.domain.user.DmUserInfo;
import com.ctrip.train.kefu.system.api.infrastructure.constants.Result;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.MD5Utils;
import dao.ctrip.ctrainchat.entity.UserBasicInfo;
import dao.ctrip.ctrainchat.entity.UserVendorInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private ExtUserBasicInfo userBasicInfo;

    @Autowired
    private ExtUserVendorInfo userVendorInfo;


    //dal client
    private DalClient dalClient = DalClientFactory.getClient(UserBasicInfo.class.getAnnotation(Database.class).name());

    /**
     * 获取供应商用户信息
     * @return
     */
    public GetVendorUserInfoRespose getUserInfo(GetVendorUserInfoRequest request )  {
        GetVendorUserInfoRespose respose = new GetVendorUserInfoRespose();
        if (request == null || request.getUserName() == null || request.getPassword() == null) {
            return respose;
        }
        DmUserInfo dmUserInfo = userVendorInfo.getUserInfo(request.getUserName(), request.getPassword());
        if (dmUserInfo != null) {
            ModelMapper modelMapper = new ModelMapper();
            VendorUserInfo userInfo = modelMapper.map(dmUserInfo ,VendorUserInfo.class);
            respose.setUserInfo(userInfo);
        }
        return respose;
    }

    /**
     * 添加供应商用户信息
     * @return
     */
    public AddVendorUserInfoRespose addUserInfo(AddVendorUserInfoRequest request )  {
        AddVendorUserInfoRespose respose = new AddVendorUserInfoRespose();
        if (request == null || request.getUserInfo() == null ) {
            return respose;
        }

        if (request.getUserInfo().getRealName()==null
            ||request.getUserInfo().getRealName().isEmpty()) {
            respose.setResult(false);
            respose.setMsg(Result.ParamIsEmpty("姓名"));
            return respose;
        }

        if (request.getUserInfo().getEmail()==null
                ||request.getUserInfo().getEmail().isEmpty()) {
            respose.setResult(false);
            respose.setMsg(Result.ParamIsEmpty("邮箱"));
            return respose;
        }

        if (request.getUserInfo().getVendorCode()==null
                ||request.getUserInfo().getVendorCode().isEmpty()) {
            respose.setResult(false);
            respose.setMsg(Result.ParamIsEmpty("供应商code"));
            return respose;
        }

        if (request.getUserInfo().getVendorCode()==null
                ||request.getUserInfo().getVendorCode().isEmpty()) {
            respose.setResult(false);
            respose.setMsg(Result.ParamIsEmpty("供应商名称"));
            return respose;
        }

        //用户基础信息
        UserBasicInfo  user=new  UserBasicInfo();
        user.setUserName(request.getUserInfo().getUserName());

        DmUserInfo info= userBasicInfo.getUserInfo(request.getUserInfo().getUserName().trim());
        if (info!=null){
            respose.setResult(false);
            respose.setMsg("该用户已存在");
            return respose;
        }

        user.setDatachangeLasttime(DateUtils.getCurFullTimestamp());
        user.setEmail(request.getUserInfo().getEmail());
        user.setIsDelete(0);
        user.setPassword(MD5Utils.getMD5(request.getUserInfo().getPassword()));
        user.setTelephone(request.getUserInfo().getTelephone());
        user.setUserType(2);
        user.setUserNum(request.getUserInfo().getUserNum());
        user.setRealName(request.getUserInfo().getRealName());

        //供应商信息
        UserVendorInfo vendor=new  UserVendorInfo();
        vendor.setDealLimit(5);
        vendor.setState(0);
        vendor.setVendorCode(request.getUserInfo().getVendorCode());
        vendor.setVendorName(request.getUserInfo().getVendorName());

        try {
            dalClient.execute(client -> {
                KeyHolder keyHolder = new KeyHolder();
                int result = userBasicInfo.insertWithKeyHolder(keyHolder,user);
                if (result > 0) {
                    long userId = keyHolder.getKey().longValue();
                    vendor.setUserId(userId);
                    userVendorInfo.insert(vendor);
                }
                return true;
            }, new DalHints());
            respose.setResult(true);
        } catch (Exception ex) {
            CLogger.error("sendVendorNotice", ex);
            respose.setResult(false);
        }

        return respose;
    }

}
