package com.example.worktalk.Models;

public class DurumModel {
    private String userName,zaman,konuBaslik,konuIcerik,profilFotoUrl,userKey;

    public DurumModel(String userName, String zaman, String konuBaslik, String konuIcerik, String profilFotoUrl, String userKey) {
        this.userName = userName;
        this.zaman = zaman;
        this.konuBaslik = konuBaslik;
        this.konuIcerik = konuIcerik;
        this.profilFotoUrl = profilFotoUrl;
        this.userKey = userKey;
    }

    public DurumModel() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public String getKonuBaslik() {
        return konuBaslik;
    }

    public void setKonuBaslik(String konuBaslik) {
        this.konuBaslik = konuBaslik;
    }

    public String getKonuIcerik() {
        return konuIcerik;
    }

    public void setKonuIcerik(String konuIcerik) {
        this.konuIcerik = konuIcerik;
    }

    public String getProfilFotoUrl() {
        return profilFotoUrl;
    }

    public void setProfilFotoUrl(String profilFotoUrl) {
        this.profilFotoUrl = profilFotoUrl;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @Override
    public String toString() {
        return "DurumModel{" +
                "userName='" + userName + '\'' +
                ", zaman='" + zaman + '\'' +
                ", konuBaslik='" + konuBaslik + '\'' +
                ", konuIcerik='" + konuIcerik + '\'' +
                ", profilFotoUrl='" + profilFotoUrl + '\'' +
                ", userKey='" + userKey + '\'' +
                '}';
    }
}
