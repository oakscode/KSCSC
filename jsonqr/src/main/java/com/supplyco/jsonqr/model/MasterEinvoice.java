package com.supplyco.jsonqr.model;

import java.util.List;

public class MasterEinvoice {

    private String Version;

    private TranDtls TranDtls;

    private DocDtls DocDtls;

    private SellerDtls SellerDtls;

    private BuyerDtls BuyerDtls;

    private DispDtls DispDtls;

    private String ShipDtls;

    private ValDtls ValDtls;

    private EwbDtls EwbDtls;

    private List<ItemList> ItemList;

    public void setVersion(String Version){
        this.Version = Version;
    }
    public String getVersion(){
        return this.Version;
    }
    public void setTranDtls(TranDtls TranDtls){
        this.TranDtls = TranDtls;
    }
    public TranDtls getTranDtls(){
        return this.TranDtls;
    }
    public void setDocDtls(DocDtls DocDtls){
        this.DocDtls = DocDtls;
    }
    public DocDtls getDocDtls(){
        return this.DocDtls;
    }
    public void setSellerDtls(SellerDtls SellerDtls){
        this.SellerDtls = SellerDtls;
    }
    public SellerDtls getSellerDtls(){
        return this.SellerDtls;
    }
    public void setBuyerDtls(BuyerDtls BuyerDtls){
        this.BuyerDtls = BuyerDtls;
    }
    public BuyerDtls getBuyerDtls(){
        return this.BuyerDtls;
    }
    public void setDispDtls(DispDtls DispDtls){
        this.DispDtls = DispDtls;
    }
    public DispDtls getDispDtls(){
        return this.DispDtls;
    }
    public void setShipDtls(String ShipDtls){
        this.ShipDtls = ShipDtls;
    }
    public String getShipDtls(){
        return this.ShipDtls;
    }
    public void setValDtls(ValDtls ValDtls){
        this.ValDtls = ValDtls;
    }
    public ValDtls getValDtls(){
        return this.ValDtls;
    }
    public void setEwbDtls(EwbDtls EwbDtls){
        this.EwbDtls = EwbDtls;
    }
    public EwbDtls getEwbDtls(){
        return this.EwbDtls;
    }
    public void setItemList(List<ItemList> ItemList){
        this.ItemList = ItemList;
    }
    public List<ItemList> getItemList(){
        return this.ItemList;
    }

}
