package com.ctrip.train.kefu.system.offline.notice.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
@Getter
@Setter
public class OnceSolveNotice implements DalPojo {
    @Column(name="id")
    @Type(value= Types.BIGINT)
    private Long id;

    @Column(name="NoticeType")
    @Type(value= Types.INTEGER)
    private int NoticeType;

    @Column(name = "orderid")
    @Type(value = Types.VARCHAR)
    private String orderid;

    @Column(name = "Product_Line")
    @Type(value = Types.VARCHAR)
    private String productLine;
}
