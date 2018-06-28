package com.techpathi.rbassist;

public class Jobs {

    private String mjID;
    private String mjobTitle;
    private String mcName;
    private String mlocation;
    private String mdescription;
    private String mresponsibilities;
    private String mqualifications;
    private String memploymentType;
    private String mpostedAt;
    private String mapplyByDate;
    private String mcLogo;


    public Jobs(){ }

    public Jobs(String jID,String jobTitle,String cName,String location,String description,String responsibilities,
                String qualifications,String employmentType,String postedAt,String applyDate,String cLogo) {

        mjID=jID;
        mjobTitle=jobTitle;
        mcName=cName;
        mlocation=location;
        mdescription=description;
        mresponsibilities=responsibilities;
        mqualifications=qualifications;
        memploymentType=employmentType;
        mpostedAt=postedAt;
        mapplyByDate=applyDate;
        mcLogo=cLogo;


    }

    //Getters

    public String getMjID() {
        return mjID;
    }

    public String getMjobTitle() {
        return mjobTitle;
    }

    public String getMcName() {
        return mcName;
    }

    public String getMdescription() {
        return mdescription;
    }

    public String getMlocation() {
        return mlocation;
    }

    public String getMresponsibilities() {
        return mresponsibilities;
    }

    public String getMqualifications() {
        return mqualifications;
    }

    public String getMemploymentType() {
        return memploymentType;
    }

    public String getMpostedAt() {
        return mpostedAt;
    }

    public String getMapplyDate() {
        return mapplyByDate;
    }

    public String getMcLogo(){return mcLogo;}

    //Setters


    public void setMjID(String mjID) {
        this.mjID = mjID;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public void setMjobTitle(String mjobTitle) {
        this.mjobTitle = mjobTitle;
    }

    public void setMemploymentType(String memploymentType) {
        this.memploymentType = memploymentType;
    }

    public void setMlocation(String mlocation) {
        this.mlocation = mlocation;
    }

    public void setMresponsibilities(String mresponsibilities) {
        this.mresponsibilities = mresponsibilities;
    }

    public void setMpostedAt(String mpostedAt) {
        this.mpostedAt = mpostedAt;
    }

    public void setMqualifications(String mqualifications) {
        this.mqualifications = mqualifications;
    }

    public void setMapplyDate(String mapplyByDate) {
        this.mapplyByDate = mapplyByDate;
    }

    public void setMcLogo(String mcLogo){

        this.mcLogo=mcLogo;
    }




}
