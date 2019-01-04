package com.ctrip.train.kefu.system.client.pojo.train;

import java.util.List;
import java.util.Map;

public class TwoContractPojo {
    private String partnerOrderId;
    private List<Map<String,String>> ticketInfo;

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public List<Map<String, String>> getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(List<Map<String, String>> ticketInfo) {
        this.ticketInfo = ticketInfo;
    }
}
