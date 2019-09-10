package com.example.islamicapp.Models;

public class    WazifaModel {
    private String Purpose,Sect,Translation,Wazifa,Id,Num,Type;

    public WazifaModel() {
    }

    public WazifaModel(String purpose, String sect, String translation, String wazifa, String id,String num,String type) {
        Purpose = purpose;
        Sect = sect;
        Translation = translation;
        Wazifa = wazifa;
        Id = id;
        Num = num;
        Type = type;

    }

    public String getNum() {
        return Num;
    }

    public String getType() {
        return Type;
    }

    public String getId() {
        return Id;
    }

    public String getPurpose() {
        return Purpose;
    }

    public String getSect() {
        return Sect;
    }

    public String getTranslation() {
        return Translation;
    }

    public String getWazifa() {
        return Wazifa;
    }
}
