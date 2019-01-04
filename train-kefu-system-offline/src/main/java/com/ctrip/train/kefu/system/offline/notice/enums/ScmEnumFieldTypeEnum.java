package com.ctrip.train.kefu.system.offline.notice.enums;

public enum ScmEnumFieldTypeEnum {
    NoticeSource{
        @Override
        public String getFieldType() { return "notice_source"; }
    },
    ComplainSource{
        @Override
        public String getFieldType() { return "complain_source"; }
    },
    LeaderNoticeSource{
        @Override
        public String getFieldType() { return "notice_source"; }
    };
    public abstract String getFieldType();
}
