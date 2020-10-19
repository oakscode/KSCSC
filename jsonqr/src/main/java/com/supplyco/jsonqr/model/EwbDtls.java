package com.supplyco.jsonqr.model;

public class EwbDtls
{
    private String TransId;

    private String TransName;

    private String TransMode;

    private int Distance;

    private String TransDocNo;

    private String TransDocDt;

    private String VehNo;

    private String VehType;

    public void setTransId(String TransId){
        this.TransId = TransId;
    }
    public String getTransId(){
        return this.TransId;
    }
    public void setTransName(String TransName){
        this.TransName = TransName;
    }
    public String getTransName(){
        return this.TransName;
    }
    public void setTransMode(String TransMode){
        this.TransMode = TransMode;
    }
    public String getTransMode(){
        return this.TransMode;
    }
    public void setDistance(int Distance){
        this.Distance = Distance;
    }
    public int getDistance(){
        return this.Distance;
    }
    public void setTransDocNo(String TransDocNo){
        this.TransDocNo = TransDocNo;
    }
    public String getTransDocNo(){
        return this.TransDocNo;
    }
    public void setTransDocDt(String TransDocDt){
        this.TransDocDt = TransDocDt;
    }
    public String getTransDocDt(){
        return this.TransDocDt;
    }
    public void setVehNo(String VehNo){
        this.VehNo = VehNo;
    }
    public String getVehNo(){
        return this.VehNo;
    }
    public void setVehType(String VehType){
        this.VehType = VehType;
    }
    public String getVehType(){
        return this.VehType;
    }
}
