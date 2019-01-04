package com.ctrip.train.kefu.system.offline.notice.vm.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUrge {
    private int status;
    private String msg;

    public ResponseUrge(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static ResponseUrge success(String message){
        return  new ResponseUrge(0,message);
    }

    public static ResponseUrge fail(String message){
        return new ResponseUrge(1,message);
    }

}
