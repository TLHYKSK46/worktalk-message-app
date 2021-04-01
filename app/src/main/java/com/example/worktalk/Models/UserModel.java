package com.example.worktalk.Models;

public class UserModel {
private String telefonNo,adSoyad,profilFoto,email,departman,statü, sirketKey;

    public UserModel() {

    }

    public UserModel(String telefonNo, String sirketKey, String adSoyad, String profilFoto, String email, String departman, String statü) {
        this.telefonNo = telefonNo;
        this.adSoyad = adSoyad;
        this.profilFoto = profilFoto;
        this.email = email;
        this.departman = departman;
        this.statü = statü;
        this.sirketKey = sirketKey;
    }

    public String getTelefonNo() {
        return telefonNo;
    }

    public void setTelefonNo(String telefonNo) {
        this.telefonNo = telefonNo;
    }

    public String getSirketKey() {
        return sirketKey;
    }

    public void setSirketKey(String sirketKey) {
        this.sirketKey = sirketKey;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getProfilFoto() {
        return profilFoto;
    }

    public void setProfilFoto(String profilFoto) {
        this.profilFoto = profilFoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getStatü() {
        return statü;
    }

    public void setStatü(String statü) {
        this.statü = statü;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "telefonNo='" + telefonNo + '\'' +
                ", adSoyad='" + adSoyad + '\'' +
                ", profilFoto='" + profilFoto + '\'' +
                ", email='" + email + '\'' +
                ", departman='" + departman + '\'' +
                ", statü='" + statü + '\'' +
                ", sirketKey='" + sirketKey + '\'' +
                '}';
    }
}
