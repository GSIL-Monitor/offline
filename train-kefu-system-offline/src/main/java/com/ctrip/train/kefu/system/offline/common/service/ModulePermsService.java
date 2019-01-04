package com.ctrip.train.kefu.system.offline.common.service;

import com.ctrip.train.kefu.system.offline.common.constant.PropertyFile;
import com.ctrip.train.kefu.system.offline.common.domain.OfflineStaffInfo;
import common.qconfig.QConfigHelper;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ying_zhou 2017/10/21 16:33
 */
@Service
public class ModulePermsService {
     /**
     * Get the module's ID configured in classpath:module.properties by uri
     * @param uri
     * @return 0 if no module ID was found
     */
    public List<Integer> getModuleIdByUri(String uri){
        ArrayList<Integer> lst= new ArrayList<>();
        if(StringUtils.isNotEmpty(uri)){
            if (uri.startsWith("/")){
                uri=uri.substring(1);
            }
            if(uri.contains("/")){
                uri=uri.split("/")[0];
            }
            Map<String, String> map = QConfigHelper.getQConfigMap(PropertyFile.MODULE_PROPERTIES);
            String key=String.format(PropertyFile.MODULE_PROPERTIES_TEMPLATE,uri);
            if(!map.containsKey(key)){
                return lst;
            }
            String  module=map.get(key);
            if (module.contains(",")){
               String[] arr=  module.split(",");
                for (String item:arr){
                    lst.add(Integer.parseInt(item));
                }
            }
            else
                lst.add(Integer.parseInt(module));
            return lst;
        }
        return lst;
   }


    /**
     * 验证权限
     * @param code
     * @return
     */
   public  boolean hasModulePerms(String code,OfflineStaffInfo staffInfo){
       if (code==null||code.isEmpty()){
           return  false;
       }
       //写死数据，之后从数据库中查询
       List<String> permsCode= new ArrayList<>();


       if (null!=staffInfo&&staffInfo.getStaffPermission()!=null) {
           List<String> permCddes = staffInfo.getStaffPermission()
                   .stream()
                   .map(OfflinePermission::getPermCode)
                   .collect(Collectors.toList());
           permsCode.addAll(permCddes);
       }

       if (code.contains(",")){
          String[] modelCode= code.split(",");
          if (modelCode.length>0){
              return permsCode.retainAll(Arrays.asList(modelCode));
          }
       }
       return permsCode.contains(code);
   }
}
