package com.example.islamicapp.Models;

import java.util.ArrayList;

public class EventModel {
    private  String Title,Description,Address,Date,Time,OID,Sect,City,EventId,InterestedPeoples;

    public EventModel() {
    }

    public EventModel(String title, String description, String address, String date, String time, String OID, String sect, String city, String eventId) {
        Title = title;
        Description = description;
        Address = address;
        Date = date;
        Time = time;
        this.OID = OID;
        Sect = sect;
        City = city;
        EventId = eventId;
    }

    public EventModel(String title, String description, String address, String date,
                      String time, String OID, String sect, String city, String eventId, String interestedPeoples) {
        Title = title;
        Description = description;
        Address = address;
        Date = date;
        Time = time;
        this.OID = OID;
        Sect = sect;
        City = city;
        EventId = eventId;
        InterestedPeoples = interestedPeoples;
    }

    public String getPeoplesList() {
        return InterestedPeoples;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getSect() {
        return Sect;
    }

    public void setSect(String sect) {
        Sect = sect;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
