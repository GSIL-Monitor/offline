package com.ctrip.train.kefu.system.offline.order.vm;

import java.util.Dictionary;
import java.util.List;

public class VmInvoiceDetail {

    private String productType;
    private String address;
    private String bankNumber;
    private String mail;
    private String payer;
    private String tel;
    private String title;
    private String openBank;
    private List<VmInvoiceOrderInfo> orderList;
    //超级会员字段
    private String superOrderNumber;
    private String superProductId;
    private String superuid;
    private String superpartnerName;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public List<VmInvoiceOrderInfo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<VmInvoiceOrderInfo> orderList) {
        this.orderList = orderList;
    }

    public String getSuperOrderNumber() {
        return superOrderNumber;
    }

    public void setSuperOrderNumber(String superOrderNumber) {
        this.superOrderNumber = superOrderNumber;
    }

    public String getSuperProductId() {
        return superProductId;
    }

    public void setSuperProductId(String superProductId) {
        this.superProductId = superProductId;
    }

    public String getSuperuid() {
        return superuid;
    }

    public void setSuperuid(String superuid) {
        this.superuid = superuid;
    }

    public String getSuperpartnerName() {
        return superpartnerName;
    }

    public void setSuperpartnerName(String superpartnerName) {
        this.superpartnerName = superpartnerName;
    }
}

