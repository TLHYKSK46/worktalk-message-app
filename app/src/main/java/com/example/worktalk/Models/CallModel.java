package com.example.worktalk.Models;

public class CallModel {
    private String userName,otherName,profilFoto,zaman, userTelefonNo,otherTelefonNo, aramaTipi,otherKey,userKey;
    //private  String userName, otherName, otherCallKey, userKey, userTelefonNo, otherTelefonNo, zaman, aramaTipi;

    public CallModel() {

    }

    public CallModel(String userName, String otherName, String profilFoto, String zaman, String userTelefonNo, String otherTelefonNo, String aramaTipi, String otherKey, String userKey) {
        this.userName = userName;
        this.otherName = otherName;
        this.profilFoto = profilFoto;
        this.zaman = zaman;
        this.userTelefonNo = userTelefonNo;
        this.otherTelefonNo = otherTelefonNo;
        this.aramaTipi = aramaTipi;
        this.otherKey = otherKey;
        this.userKey = userKey;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherTelefonNo() {
        return otherTelefonNo;
    }

    public void setOtherTelefonNo(String otherTelefonNo) {
        this.otherTelefonNo = otherTelefonNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilFoto() {
        return profilFoto;
    }

    public void setProfilFoto(String profilFoto) {
        this.profilFoto = profilFoto;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public String getUserTelefonNo() {
        return userTelefonNo;
    }

    public void setUserTelefonNo(String userTelefonNo) {
        this.userTelefonNo = userTelefonNo;
    }

    public String getAramaTipi() {
        return aramaTipi;
    }

    public void setAramaTipi(String aramaTipi) {
        this.aramaTipi = aramaTipi;
    }

    public String getOtherKey() {
        return otherKey;
    }

    public void setOtherKey(String otherKey) {
        this.otherKey = otherKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @Override
    public String toString() {
        return "CallModel{" +
                "userName='" + userName + '\'' +
                ", otherName='" + otherName + '\'' +
                ", profilFoto='" + profilFoto + '\'' +
                ", zaman='" + zaman + '\'' +
                ", userTelefonNo='" + userTelefonNo + '\'' +
                ", otherTelefonNo='" + otherTelefonNo + '\'' +
                ", aramaTipi='" + aramaTipi + '\'' +
                ", otherKey='" + otherKey + '\'' +
                ", userKey='" + userKey + '\'' +
                '}';
    }
}
