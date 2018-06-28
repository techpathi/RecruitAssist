package com.techpathi.rbassist;

public class Companies {

    private String mcName;
    private String mcHq;
    private String mcAboutUs;
    private String mcYearFounded;
    private String mcSize;
    private String mcSpecialities;
    private String mcRegisteredDate;
    private String mcType;
    private String mcLogoUrl;
    private String mcwebsite;


    public Companies(){ }

    public Companies(String cName,String cHq,String cAboutUs,String cYearFounded,String cSize,String cSpecialities,
                String cRegisteredDate,String cType,String cLogoUrl,String cWebsite) {

       mcName=cName;
       mcHq=cHq;
       mcAboutUs=cAboutUs;
       mcYearFounded=cYearFounded;
       mcSize=cSize;
       mcRegisteredDate=cRegisteredDate;
       mcSpecialities=cSpecialities;
       mcType=cType;
       mcLogoUrl=cLogoUrl;
       mcwebsite=cWebsite;



    }

    //Getters

    public String getMcHq() {
        return mcHq;
    }

    public String getMcAboutUs() {
        return mcAboutUs;
    }

    public String getMcName() {
        return mcName;
    }

    public String getMcYearFounded() {
        return mcYearFounded;
    }

    public String getMcSize() {
        return mcSize;
    }

    public String getMcSpecialities() {
        return mcSpecialities;
    }

    public String getMcRegisteredDate() {
        return mcRegisteredDate;
    }

    public String getMcType() {
        return mcType;
    }

    public  String getMcLogoUrl()
    {
        return mcLogoUrl;
    }

    public String getMcwebsite()
    {
        return mcwebsite;
    }



    //Setters


    public void setMcHq(String mcHq) {
        this.mcHq = mcHq;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public void setMcAboutUs(String mcAboutUs) {
        this.mcAboutUs = mcAboutUs;
    }

    public void setMcYearFounded(String mcYearFounded) {
        this.mcYearFounded = mcYearFounded;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public void setMcSize(String mcSize) {
        this.mcSize = mcSize;
    }

    public void setMcSpecialities(String mcSpecialities) {
        this.mcSpecialities = mcSpecialities;
    }

    public void setMcRegisteredDate(String mcRegisteredDate) {
        this.mcRegisteredDate = mcRegisteredDate;
    }

    public void setMcLogoUrl(String mcLogoUrl)
    {
        this.mcLogoUrl=mcLogoUrl;
    }

    public void setMcwebsite(String mcwebsite)
    {
        this.mcwebsite=mcwebsite;
    }
}
