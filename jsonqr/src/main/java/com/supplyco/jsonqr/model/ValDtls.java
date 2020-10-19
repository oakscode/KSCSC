package com.supplyco.jsonqr.model;

public class ValDtls
{
    private int AssVal;

    private int IgstVal;

    private double CgstVal;

    private double SgstVal;

    private double CesVal;

    private double StCesVal;

    private int Discount;

    private int OthChrg;

    private int RndOffAmt;

    private double TotInvVal;

    public void setAssVal(int AssVal){
        this.AssVal = AssVal;
    }
    public int getAssVal(){
        return this.AssVal;
    }
    public void setIgstVal(int IgstVal){
        this.IgstVal = IgstVal;
    }
    public int getIgstVal(){
        return this.IgstVal;
    }
    public void setCgstVal(double CgstVal){
        this.CgstVal = CgstVal;
    }
    public double getCgstVal(){
        return this.CgstVal;
    }
    public void setSgstVal(double SgstVal){
        this.SgstVal = SgstVal;
    }
    public double getSgstVal(){
        return this.SgstVal;
    }
    public void setCesVal(double CesVal){
        this.CesVal = CesVal;
    }
    public double getCesVal(){
        return this.CesVal;
    }
    public void setStCesVal(double StCesVal){
        this.StCesVal = StCesVal;
    }
    public double getStCesVal(){
        return this.StCesVal;
    }
    public void setDiscount(int Discount){
        this.Discount = Discount;
    }
    public int getDiscount(){
        return this.Discount;
    }
    public void setOthChrg(int OthChrg){
        this.OthChrg = OthChrg;
    }
    public int getOthChrg(){
        return this.OthChrg;
    }
    public void setRndOffAmt(int RndOffAmt){
        this.RndOffAmt = RndOffAmt;
    }
    public int getRndOffAmt(){
        return this.RndOffAmt;
    }
    public void setTotInvVal(double TotInvVal){
        this.TotInvVal = TotInvVal;
    }
    public double getTotInvVal(){
        return this.TotInvVal;
    }
}

