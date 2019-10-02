package com.example.islamicapp;

public class RecordModel {
    private String purpose,translation,wazifa,type,uid;

    public RecordModel(String purpose, String translation, String wazifa, String type, String uid) {
        this.purpose = purpose;
        this.translation = translation;
        this.wazifa = wazifa;
        this.type = type;
        this.uid = uid;
    }

    public RecordModel() {
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWazifa() {
        return wazifa;
    }

    public void setWazifa(String wazifa) {
        this.wazifa = wazifa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
