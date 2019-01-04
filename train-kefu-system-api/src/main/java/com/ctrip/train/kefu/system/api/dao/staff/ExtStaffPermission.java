package com.ctrip.train.kefu.system.api.dao.staff;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.StaffPermissionDao;
import dao.ctrip.ctrainpps.entity.StaffPermission;
import dao.ctrip.ctrainpps.entity.StaffPermissionRelation;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtStaffPermission extends StaffPermissionDao {
    protected DalQueryDao baseDao;
    public ExtStaffPermission() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取用户权限list
     **/
    public List<StaffPermission> getStaffPermissionList(Long permissionTid, String permissionName, Integer isDelete, String permissionCode) throws SQLException {
        DalHints hints=new DalHints();
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("SELECT Tid,PermissionName,IsDelete,StaffNumber,PermissionType,PermissionDesc,PermissionCode,RoleForPermission FROM staff_permission where 1=1 ");
        builder.combine(permissionTid>0,"and tid=?", Types.BIGINT,permissionTid);
        builder.combine(StringUtils.isNotBlank(permissionName),"and permissionname like ?", Types.VARCHAR,permissionName);
        builder.combine(isDelete!=-1,"and isdelete =? ",Types.SMALLINT,isDelete);
        builder.combine(StringUtils.isNotBlank(permissionCode),"and permissioncode=?",Types.VARCHAR,permissionCode);
        hints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(),builder.getParameters(),hints,StaffPermission.class);
    }

    /**
     * 获取用户权限list
     * getPermissionsRelation
     **/
    public List<StaffPermissionRelation> getPermissionsRelation(String staffNumber, String permissionCode, int permissionTid) throws SQLException {
        DalHints hints=new DalHints();
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("SELECT r.StaffNumber,r.EffectiveTime,r.PermissionTid,c.Staff_Name StaffName,p.PermissionCode,p.PermissionName,r.IsDelete " +
                "FROM staff_permission_relation r " +
                "left join staff_permission p  on r.PermissionTid=p.Tid " +
                "left join chat_staff_info c on r.StaffNumber=c.Staff_Number AND c.Is_Delete=1");
        builder.combine("where  r.IsDelete=1 ");
        builder.combine(StringUtils.isNotBlank(staffNumber)," and  r.staffnumber=? ", Types.VARCHAR,staffNumber);
        builder.combine(StringUtils.isNotBlank(permissionCode)," and p.permissioncode=? ", Types.VARCHAR,permissionCode);
        builder.combine(permissionTid>0," and p.tid=? ", Types.INTEGER,permissionTid);
        hints.set(DalHintEnum.allowPartial);
        return  baseDao.query(builder.getSql(),builder.getParameters(),hints,StaffPermissionRelation.class);
    }
}
