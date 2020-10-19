package com.supplyco.jsonqr.model;

public class TranDtls
{
    private String TaxSch;

    private String SupTyp;

    private String IgstOnIntra;

    private String RegRev;

    private String EcmGstin;

    public void setTaxSch(String TaxSch){
        this.TaxSch = TaxSch;
    }
    public String getTaxSch(){
        return this.TaxSch;
    }
    public void setSupTyp(String SupTyp){
        this.SupTyp = SupTyp;
    }
    public String getSupTyp(){
        return this.SupTyp;
    }
    public void setIgstOnIntra(String IgstOnIntra){
        this.IgstOnIntra = IgstOnIntra;
    }
    public String getIgstOnIntra(){
        return this.IgstOnIntra;
    }
    public void setRegRev(String RegRev){
        this.RegRev = RegRev;
    }
    public String getRegRev(){
        return this.RegRev;
    }
    public void setEcmGstin(String EcmGstin){
        this.EcmGstin = EcmGstin;
    }
    public String getEcmGstin(){
        return this.EcmGstin;
    }
}