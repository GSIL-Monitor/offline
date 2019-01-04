package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.soa.train.javaxproductservice.v1.SearchProductByUidResponseType;
import com.ctrip.train.kefu.system.offline.order.vm.VmMemberCardRights;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Service
public interface MemberCardService {

    List<VmMemberCardRights> memberCardGetUserRightsByUid(String uid, String cardcode);

    boolean memberCardGetUserRights(String uid, String cardcode, List<VmMemberCardRights> listMemberCardRights,List<String> listOrderid,StringBuilder sbUseInfo);

    String cancelMemberCard(Integer productid, String uid, String orderid,String fromorderid);

    SearchProductByUidResponseType searchProductByUid(String uid);
}
