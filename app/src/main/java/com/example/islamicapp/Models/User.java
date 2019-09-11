package com.example.islamicapp.Models;

public class User {

private String Name;
private String ImageUri;
private String Sect;

    public User(String name, String sect) {
        Name = name;
        Sect = sect;
    }

    public User(String name, String imageUri, String sect) {
        Name = name;
        ImageUri = imageUri;
        Sect = sect;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getSect() {
        return Sect;
    }

    public void setSect(String sect) {
        Sect = sect;
    }

    public User(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    User(){

    }
}
