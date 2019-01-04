package com.ctrip.train.kefu.system.offline.system.service.impl;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysMenuRoleInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysOfflineMenu;
import com.ctrip.train.kefu.system.offline.system.domain.MenusNodeResult;
import com.ctrip.train.kefu.system.offline.system.domain.MenusResult;
import com.ctrip.train.kefu.system.offline.system.service.SysMenuService;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenusResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostResponse;
import common.util.DateUtils;
import dao.ctrip.ctrainchat.entity.OfflineMenu;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private ExtSysMenuRoleInfo extSysMenuRoleInfo;

    @Autowired
    private ExtSysOfflineMenu extSysOfflineMenu;

    @Override
    public List<VmMenusResponse> searchStaffMenus(Map<String,String> map) {
        List<OfflineMenu> smr=extSysMenuRoleInfo.searchStaffMenu(map);
        if(smr!=null&&smr.size()>0){
            List<OfflineMenu> parentMenuList = smr.stream().filter(m->m.getParentId()==0||m.getParentId()==null)
                    .sorted(Comparator.comparing(OfflineMenu::getSort)).collect(Collectors.toList());
            if(parentMenuList!=null&&parentMenuList.size()>0){
                List<VmMenusResponse> menus=new ArrayList<>();
                for (OfflineMenu parentMenu:parentMenuList){
                    //一级菜单名
                    VmMenusResponse vmsr=new VmMenusResponse();
                    vmsr.setMenuName(parentMenu.getMenuName());
                    vmsr.setIcon(parentMenu.getIcon());
                    List<OfflineMenu> twoMenu=smr.stream().filter(m->m.getParentId().equals(parentMenu.getMenuId()))
                            .sorted(Comparator.comparing(OfflineMenu::getSort)).collect(Collectors.toList());
                    List<VmMenusResponse> nodes=new ArrayList<>();
                    for (OfflineMenu temp:twoMenu){
                        VmMenusResponse node=new VmMenusResponse();
                        //二级菜单
                        node.setMenuName(temp.getMenuName());
                        node.setUrl(temp.getUrl());
                        nodes.add(node);
                    }
                    vmsr.setNodes(nodes);
                    menus.add(vmsr);
                }
                return menus;
            }
        }
        return null;
    }

    @Override
    public PageInfo searchMenuForPage(VmMenuRequest request) {
        List<MenusNodeResult> reports= extSysOfflineMenu.searchMenu(request);
        PageInfo pg=new PageInfo();
        if (reports!=null&&reports.size()>0){
            ModelMapper modelMapper=new ModelMapper();
            List<VmMenuResponse> vmList= modelMapper.map(reports,new TypeToken<List<VmMenuResponse>>() {}.getType());
            int count=extSysOfflineMenu.searchMenuCount(request);
            pg.setData(vmList);
            pg.setRecordsFiltered(count);
            pg.setRecordsTotal(count);
        }
        return pg;
    }

    @Override
    public VmMenuResponse queryMenuBytId(Long tid) throws SQLException {
        OfflineMenu menu=extSysOfflineMenu.queryByPk(tid);
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(menu,new TypeToken<VmMenuResponse>() {}.getType());
    }

    @Override
    public int addMenu(VmMenuRequest request) throws SQLException {
        ModelMapper modelMapper=new ModelMapper();
        OfflineMenu menu= modelMapper.map(request,new TypeToken<OfflineMenu>() {}.getType());
        menu.setCreateTime(DateUtils.getCurFullTimestamp());
        menu.setAvailable(1);
        menu.setMenuId(extSysOfflineMenu.queryMaxId()+1);
        return extSysOfflineMenu.insert(menu);
    }

    @Override
    public int editMenu(VmMenuRequest request) throws SQLException {
        OfflineMenu menu=extSysOfflineMenu.queryByPk(request.getId());
        menu.setMenuName(request.getMenuName());
        menu.setUrl(request.getUrl());
        menu.setType(request.getType());
        menu.setAvailable(request.getAvailable());
        menu.setParentId(request.getParentId());
        menu.setIcon(request.getIcon());
        menu.setSort(request.getSort());
        return extSysOfflineMenu.update(menu);
    }

    @Override
    public int deleteMenu(long tid) throws SQLException {
        OfflineMenu menu=extSysOfflineMenu.queryByPk(tid);
        return extSysOfflineMenu.delete(menu);
    }

    @Override
    public List<VmMenusResponse> availableMenus() {
        List<MenusResult> menus=extSysOfflineMenu.searchAvailableMenu();
        if(menus!=null&&menus.size()>0){
            Map<Long, List<MenusResult>> map=menus.stream().collect(Collectors.groupingBy(MenusResult::getMenuId));
            List<VmMenusResponse> result=new ArrayList<>();
            for (Map.Entry<Long, List<MenusResult>> entry : map.entrySet()) {
                VmMenusResponse menuVm=new VmMenusResponse();
                menuVm.setMenuId(String.valueOf(entry.getKey()));
                menuVm.setMenuName(entry.getValue().get(0).getMenuName());
                List<VmMenusResponse> temp=new ArrayList<>();

                for (MenusResult menu:entry.getValue()){
                    if(menu.getNodeId()!=null){
                        VmMenusResponse node=new VmMenusResponse();
                        node.setMenuId(String.valueOf(menu.getNodeId()));
                        node.setMenuName(menu.getNodeName());
                        temp.add(node);
                    }
                }
                if (temp.size()>0)
                    menuVm.setNodes(temp);
                result.add(menuVm);
            }
            return result;
        }
        return null;
    }
}
