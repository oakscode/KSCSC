package com.supplyco.jsonqr.model;

public class DispDtls
{
    private String Nm;

    private String Addr1;

    private String Addr2;

    private String Loc;

    private int Pin;

    private String Stcd;

    public void setNm(String Nm){
        this.Nm = Nm;
    }
    public String getNm(){
        return this.Nm;
    }
    public void setAddr1(String Addr1){
        this.Addr1 = Addr1;
    }
    public String getAddr1(){
        return this.Addr1;
    }
    public void setAddr2(String Addr2){
        this.Addr2 = Addr2;
    }
    public String getAddr2(){
        return this.Addr2;
    }
    public void setLoc(String Loc){
        this.Loc = Loc;
    }
    public String getLoc(){
        return this.Loc;
    }
    public void setPin(int Pin){
        this.Pin = Pin;
    }
    public int getPin(){
        return this.Pin;
    }
    public void setStcd(String Stcd){
        this.Stcd = Stcd;
    }
    public String getStcd(){
        return this.Stcd;
    }
}
